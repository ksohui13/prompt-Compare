(function () {
    const TITLE_MAX = 500;
    const form = document.getElementById('postForm');
    const messageEl = document.getElementById('message');
    const titleInput = document.getElementById('title');
    const contentInput = document.getElementById('content');
    const titleError = document.getElementById('titleError');
    const contentError = document.getElementById('contentError');

    function showMessage(text, isError) {
        messageEl.textContent = text;
        messageEl.className = 'message ' + (isError ? 'error' : 'success');
        messageEl.style.display = 'block';
    }

    function clearMessage() {
        messageEl.textContent = '';
        messageEl.className = 'message';
    }

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
            const response = await fetch('/api/posts', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ title: title, content: content }),
            });

            const data = await response.json().catch(function () {
                return null;
            });

            if (response.ok) {
                showMessage('등록되었습니다. (id: ' + data.id + ')', false);
                form.reset();
            } else {
                const msg = (data && data.message) ? data.message : '등록에 실패했습니다.';
                showMessage(msg, true);
            }
        } catch (err) {
            showMessage('네트워크 오류가 발생했습니다.', true);
        } finally {
            submitBtn.disabled = false;
        }
    });
})();
