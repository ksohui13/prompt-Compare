(function () {
    const params = new URLSearchParams(window.location.search);
    const id = params.get('id');

    const detailEl = document.getElementById('postDetail');
    const titleEl = document.getElementById('detailTitle');
    const contentEl = document.getElementById('detailContent');
    const loadingEl = document.getElementById('detailLoading');
    const messageEl = document.getElementById('detailMessage');

    function showMessage(text, isError) {
        messageEl.textContent = text;
        messageEl.className = 'message ' + (isError ? 'error' : 'success');
    }

    function hideLoading() {
        loadingEl.style.display = 'none';
    }

    if (!id) {
        hideLoading();
        showMessage('잘못된 접근입니다.', true);
        return;
    }

    fetch('/api/posts/' + encodeURIComponent(id))
        .then(function (res) {
            if (res.status === 404) {
                throw new Error('글이 없습니다.');
            }
            if (!res.ok) throw new Error('글을 불러올 수 없습니다.');
            return res.json();
        })
        .then(function (data) {
            hideLoading();
            titleEl.textContent = data.title || '(제목 없음)';
            contentEl.textContent = data.content || '';
            var editLink = document.getElementById('editLink');
            if (editLink) editLink.href = '/post-edit.html?id=' + encodeURIComponent(data.id);
            detailEl.hidden = false;

            var deleteBtn = document.getElementById('deleteBtn');
            if (deleteBtn) {
                deleteBtn.onclick = function () {
                    if (!window.confirm('정말 삭제하시겠습니까?')) return;
                    deleteBtn.disabled = true;
                    fetch('/api/posts/' + encodeURIComponent(id), { method: 'DELETE' })
                        .then(function (res) {
                            if (res.status === 404) throw new Error('글이 없습니다.');
                            if (!res.ok) throw new Error('삭제에 실패했습니다.');
                            window.location.href = '/post-list.html';
                        })
                        .catch(function (err) {
                            deleteBtn.disabled = false;
                            showMessage(err.message || '삭제 중 오류가 발생했습니다.', true);
                        });
                };
            }
        })
        .catch(function (err) {
            hideLoading();
            showMessage(err.message || '오류가 발생했습니다.', true);
        });
})();
