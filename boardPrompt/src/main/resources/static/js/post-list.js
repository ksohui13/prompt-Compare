(function () {
    const listEl = document.getElementById('postList');
    const loadingEl = document.getElementById('listLoading');
    const messageEl = document.getElementById('listMessage');

    function showMessage(text, isError) {
        messageEl.textContent = text;
        messageEl.className = 'message ' + (isError ? 'error' : 'success');
    }

    function hideLoading() {
        loadingEl.style.display = 'none';
    }

    function renderList(posts) {
        listEl.innerHTML = '';
        if (!posts || posts.length === 0) {
            listEl.innerHTML = '<li class="post-list-empty">등록된 글이 없습니다.</li>';
            return;
        }
        posts.forEach(function (post) {
            const li = document.createElement('li');
            const a = document.createElement('a');
            a.href = '/post-detail.html?id=' + encodeURIComponent(post.id);
            a.className = 'post-list-item';
            a.textContent = post.title || '(제목 없음)';
            li.appendChild(a);
            listEl.appendChild(li);
        });
    }

    fetch('/api/posts')
        .then(function (res) {
            if (!res.ok) throw new Error('목록을 불러올 수 없습니다.');
            return res.json();
        })
        .then(function (data) {
            hideLoading();
            renderList(data);
        })
        .catch(function () {
            hideLoading();
            showMessage('목록을 불러오는 중 오류가 발생했습니다.', true);
        });
})();
