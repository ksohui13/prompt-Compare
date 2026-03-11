(function () {
    var messageArea = document.getElementById('messageArea');
    var titleEl = document.getElementById('postTitle');
    var contentEl = document.getElementById('postContent');
    var createdAtEl = document.getElementById('postCreatedAt');
    var editLink = document.getElementById('editLink');
    var deleteBtn = document.getElementById('deleteBtn');

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

    function getPostIdFromQuery() {
        var params = new URLSearchParams(window.location.search);
        return params.get('id');
    }

    function renderPost(post) {
        titleEl.textContent = post.title || '';
        createdAtEl.textContent = formatDate(post.createdAt);
        contentEl.textContent = post.content || '';
        if (editLink && post.id) {
            editLink.href = '/post-edit.html?id=' + encodeURIComponent(post.id);
        }
    }

    function deletePost() {
        var id = getPostIdFromQuery();
        if (!id) {
            showMessage('잘못된 접근입니다. 게시글 ID가 없습니다.', 'error');
            return;
        }
        if (!window.confirm('이 게시글을 삭제하시겠습니까?')) {
            return;
        }
        clearMessage();
        deleteBtn.disabled = true;
        fetch('/api/posts/' + encodeURIComponent(id), { method: 'DELETE' })
            .then(function (res) {
                if (res.status === 204) {
                    showMessage('게시글이 삭제되었습니다.', 'success');
                    setTimeout(function () {
                        window.location.href = '/index.html';
                    }, 800);
                    return;
                }
                return res.text().then(function (text) {
                    var data = {};
                    try {
                        data = text ? JSON.parse(text) : {};
                    } catch (err) {
                        data = { message: '서버 응답을 처리할 수 없습니다.' };
                    }
                    showMessage(data.message || '삭제에 실패했습니다.', 'error');
                });
            })
            .catch(function () {
                showMessage('네트워크 오류가 발생했습니다. 다시 시도해 주세요.', 'error');
            })
            .finally(function () {
                deleteBtn.disabled = false;
            });
    }

    function loadPost() {
        clearMessage();
        var id = getPostIdFromQuery();
        if (!id) {
            showMessage('잘못된 접근입니다. 게시글 ID가 없습니다.', 'error');
            return;
        }

        fetch('/api/posts/' + encodeURIComponent(id))
            .then(function (res) {
                return res.text().then(function (text) {
                    var data = {};
                    try {
                        data = text ? JSON.parse(text) : {};
                    } catch (err) {
                        showMessage('서버 응답을 처리할 수 없습니다.', 'error');
                        return null;
                    }
                    if (!res.ok) {
                        showMessage(data.message || '게시글을 불러오지 못했습니다.', 'error');
                        return null;
                    }
                    return data;
                });
            })
            .then(function (post) {
                if (post) {
                    renderPost(post);
                }
            })
            .catch(function () {
                showMessage('네트워크 오류가 발생했습니다. 다시 시도해 주세요.', 'error');
            });
    }

    if (deleteBtn) {
        deleteBtn.addEventListener('click', deletePost);
    }

    document.addEventListener('DOMContentLoaded', loadPost);
})();

