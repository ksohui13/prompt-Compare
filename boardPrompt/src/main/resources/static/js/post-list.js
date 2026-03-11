(function () {
    var messageArea = document.getElementById('messageArea');
    var tbody = document.getElementById('postListBody');

    function showMessage(text, type) {
        if (!messageArea) return;
        messageArea.textContent = text || '';
        messageArea.className = 'message-area ' + (type === 'error' ? 'error' : 'success');
        messageArea.removeAttribute('hidden');
    }

    function clearMessage() {
        messageArea.textContent = '';
        messageArea.className = 'message-area';
    }

    function formatDate(isoString) {
        if (!isoString) {
            return '';
        }
        try {
            var d = new Date(isoString);
            if (isNaN(d.getTime())) {
                return isoString;
            }
            return d.getFullYear() + '-' +
                String(d.getMonth() + 1).padStart(2, '0') + '-' +
                String(d.getDate()).padStart(2, '0') + ' ' +
                String(d.getHours()).padStart(2, '0') + ':' +
                String(d.getMinutes()).padStart(2, '0');
        } catch (e) {
            return isoString;
        }
    }

    function renderList(posts) {
        tbody.innerHTML = '';

        if (!posts || posts.length === 0) {
            var emptyRow = document.createElement('tr');
            var td = document.createElement('td');
            td.colSpan = 2;
            td.textContent = '등록된 게시글이 없습니다.';
            emptyRow.appendChild(td);
            tbody.appendChild(emptyRow);
            return;
        }

        posts.forEach(function (post) {
            var tr = document.createElement('tr');

            var titleTd = document.createElement('td');
            var link = document.createElement('a');
            link.href = '/post-detail.html?id=' + encodeURIComponent(post.id);
            link.textContent = post.title;
            titleTd.appendChild(link);

            var dateTd = document.createElement('td');
            dateTd.textContent = formatDate(post.createdAt);

            tr.appendChild(titleTd);
            tr.appendChild(dateTd);

            tbody.appendChild(tr);
        });
    }

    function loadPosts() {
        clearMessage();
        fetch('/api/posts')
            .then(function (res) {
                return res.text().then(function (text) {
                    var data = [];
                    try {
                        data = text ? JSON.parse(text) : [];
                    } catch (err) {
                        showMessage('서버 응답을 처리할 수 없습니다.', 'error');
                        return [];
                    }
                    if (!res.ok) {
                        var msg = (data && typeof data === 'object' && data.message) ? data.message : '게시글 목록을 불러오지 못했습니다.';
                        showMessage(msg, 'error');
                        return [];
                    }
                    return Array.isArray(data) ? data : [];
                });
            })
            .then(function (posts) {
                if (Array.isArray(posts)) {
                    renderList(posts);
                }
            })
            .catch(function () {
                showMessage('네트워크 오류가 발생했습니다. 다시 시도해 주세요.', 'error');
            });
    }

    document.addEventListener('DOMContentLoaded', loadPosts);
})();

