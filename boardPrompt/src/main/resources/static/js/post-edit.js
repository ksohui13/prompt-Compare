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

    var TITLE_MAX = 500;
    var titleInput = document.getElementById('title');
    var contentInput = document.getElementById('content');
    var titleError = document.getElementById('titleError');
    var contentError = document.getElementById('contentError');

    function clearFieldErrors() {
        if (titleError) titleError.textContent = '';
        if (contentError) contentError.textContent = '';
        if (titleInput) titleInput.setAttribute('aria-invalid', 'false');
        if (contentInput) contentInput.setAttribute('aria-invalid', 'false');
    }

    function setFieldError(field, message) {
        if (field === 'title' && titleError && titleInput) {
            titleError.textContent = message;
            titleInput.setAttribute('aria-invalid', 'true');
        }
        if (field === 'content' && contentError && contentInput) {
            contentError.textContent = message;
            contentInput.setAttribute('aria-invalid', 'true');
        }
    }

    function validate() {
        clearFieldErrors();
        var title = titleInput.value.trim();
        var content = contentInput.value.trim();
        if (title === '') {
            setFieldError('title', '제목을 입력하세요');
            return false;
        }
        if (title.length > TITLE_MAX) {
            setFieldError('title', '제목은 500자 이하여야 합니다');
            return false;
        }
        if (content === '') {
            setFieldError('content', '내용을 입력하세요');
            return false;
        }
        return true;
    }

    form.addEventListener('submit', async function (e) {
        e.preventDefault();
        clearMessage();
        clearFieldErrors();

        if (!validate()) {
            showMessage('입력값을 확인하세요.', true);
            return;
        }

        const title = titleInput.value.trim();
        const content = contentInput.value.trim();
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
