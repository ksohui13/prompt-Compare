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
            detailEl.hidden = false;
        })
        .catch(function (err) {
            hideLoading();
            showMessage(err.message || '오류가 발생했습니다.', true);
        });
})();
