(function () {
    const form = document.getElementById('postForm');
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

    form.addEventListener('submit', async function (e) {
        e.preventDefault();
        clearMessage();

        const title = document.getElementById('title').value.trim();
        const content = document.getElementById('content').value.trim();

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
