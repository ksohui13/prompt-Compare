(function () {
    const form = document.getElementById('createForm');
    const messageArea = document.getElementById('messageArea');
    const titleInput = document.getElementById('title');
    const contentInput = document.getElementById('content');
    const titleError = document.getElementById('titleError');
    const contentError = document.getElementById('contentError');
    const submitBtn = document.getElementById('submitBtn');

    function showMessage(text, type) {
        messageArea.textContent = text;
        messageArea.className = 'message-area ' + (type === 'success' ? 'success' : 'error');
        messageArea.removeAttribute('hidden');
    }

    function clearMessage() {
        messageArea.textContent = '';
        messageArea.className = 'message-area';
        messageArea.setAttribute('hidden', '');
    }

    function showFieldErrors(errors) {
        titleError.textContent = '';
        contentError.textContent = '';
        titleInput.classList.remove('invalid');
        contentInput.classList.remove('invalid');

        if (!errors || !Array.isArray(errors)) return;

        errors.forEach(function (err) {
            if (err.field === 'title') {
                titleError.textContent = err.message;
                titleInput.classList.add('invalid');
            } else if (err.field === 'content') {
                contentError.textContent = err.message;
                contentInput.classList.add('invalid');
            }
        });
    }

    function clearFieldErrors() {
        titleError.textContent = '';
        contentError.textContent = '';
        titleInput.classList.remove('invalid');
        contentInput.classList.remove('invalid');
    }

    form.addEventListener('submit', function (e) {
        e.preventDefault();
        clearMessage();
        clearFieldErrors();

        var title = titleInput.value.trim();
        var content = contentInput.value.trim();

        if (!title) {
            showMessage('제목을 입력해 주세요.', 'error');
            titleInput.classList.add('invalid');
            titleError.textContent = '제목은 필수입니다';
            return;
        }
        if (!content) {
            showMessage('내용을 입력해 주세요.', 'error');
            contentInput.classList.add('invalid');
            contentError.textContent = '내용은 필수입니다';
            return;
        }

        submitBtn.disabled = true;

        fetch('/api/posts', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ title: title, content: content })
        })
            .then(function (res) {
                return res.text().then(function (text) {
                    var data = {};
                    try {
                        data = text ? JSON.parse(text) : {};
                    } catch (err) {
                        data = { message: '서버 응답을 처리할 수 없습니다.' };
                    }
                    return { status: res.status, data: data };
                });
            })
            .then(function (result) {
                if (result.status === 201) {
                    showMessage('게시글이 등록되었습니다.', 'success');
                    form.reset();
                    clearFieldErrors();
                } else if (result.status === 400 && result.data.errors) {
                    showMessage(result.data.message || '입력값을 확인해 주세요.', 'error');
                    showFieldErrors(result.data.errors);
                } else {
                    showMessage(result.data.message || '등록에 실패했습니다.', 'error');
                }
            })
            .catch(function () {
                showMessage('네트워크 오류가 발생했습니다. 다시 시도해 주세요.', 'error');
            })
            .finally(function () {
                submitBtn.disabled = false;
            });
    });
})();
