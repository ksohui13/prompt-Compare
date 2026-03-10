(function () {
    const params = new URLSearchParams(window.location.search);
    const id = params.get('id');

    const form = document.getElementById('postEditForm');
    const loadingEl = document.getElementById('editLoading');
    const messageEl = document.getElementById('message');

    function showMessage(text, isError) {
        messageEl.textContent = text;
        messageEl.className = 'message ' + (isError ? 'error' : 'success');
        messageEl.style.display = 'block';
    }

    function clearMessage() {
        messageEl.textContent = '';
        messageEl.className = 'message';
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
            if (res.status === 404) throw new Error('글이 없습니다.');
            if (!res.ok) throw new Error('글을 불러올 수 없습니다.');
            return res.json();
        })
        .then(function (data) {
            hideLoading();
            document.getElementById('postId').value = data.id;
            document.getElementById('title').value = data.title || '';
            document.getElementById('content').value = data.content || '';
            form.hidden = false;
        })
        .catch(function (err) {
            hideLoading();
            showMessage(err.message || '오류가 발생했습니다.', true);
        });

    form.addEventListener('submit', async function (e) {
        e.preventDefault();
        clearMessage();

        const title = document.getElementById('title').value.trim();
        const content = document.getElementById('content').value.trim();
        const submitBtn = form.querySelector('button[type="submit"]');
        submitBtn.disabled = true;

        try {
            const response = await fetch('/api/posts/' + encodeURIComponent(id), {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ title: title, content: content }),
            });

            const data = await response.json().catch(function () { return null; });

            if (response.ok) {
                showMessage('수정되었습니다.', false);
                window.setTimeout(function () {
                    window.location.href = '/post-detail.html?id=' + encodeURIComponent(id);
                }, 800);
            } else {
                showMessage((data && data.message) ? data.message : '수정에 실패했습니다.', true);
                submitBtn.disabled = false;
            }
        } catch (err) {
            showMessage('네트워크 오류가 발생했습니다.', true);
            submitBtn.disabled = false;
        }
    });
})();
