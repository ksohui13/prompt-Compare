(function () {
    var form = document.getElementById('editForm');
    var messageArea = document.getElementById('messageArea');
    var titleInput = document.getElementById('title');
    var contentInput = document.getElementById('content');
    var titleError = document.getElementById('titleError');
    var contentError = document.getElementById('contentError');
    var submitBtn = document.getElementById('submitBtn');

    function showMessage(text, type) {
        messageArea.textContent = text;
        messageArea.className = 'message-area ' + (type === 'success' ? 'success' : 'error');
    }

    function clearMessage() {
        messageArea.textContent = '';
        messageArea.className = 'message-area';
    }

    function clearFieldErrors() {
        titleError.textContent = '';
        contentError.textContent = '';
        titleInput.classList.remove('invalid');
        contentInput.classList.remove('invalid');
    }

    function showFieldErrors(errors) {
        clearFieldErrors();
        if (!errors || !Array.isArray(errors)) {
            return;
        }
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

    function getPostIdFromQuery() {
        var params = new URLSearchParams(window.location.search);
        return params.get('id');
    }

    function loadPost() {
        clearMessage();
        clearFieldErrors();
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
                if (!post) {
                    return;
                }
                titleInput.value = post.title || '';
                contentInput.value = post.content || '';
            })
            .catch(function () {
                showMessage('네트워크 오류가 발생했습니다. 다시 시도해 주세요.', 'error');
            });
    }

    function validateForm() {
        var title = titleInput.value.trim();
        var content = contentInput.value.trim();
        var hasError = false;
        clearFieldErrors();

        if (!title) {
            titleError.textContent = '제목은 필수입니다';
            titleInput.classList.add('invalid');
            hasError = true;
        }
        if (!content) {
            contentError.textContent = '내용은 필수입니다';
            contentInput.classList.add('invalid');
            hasError = true;
        }
        if (hasError) {
            showMessage('입력값을 확인해 주세요. 제목과 내용은 필수입니다.', 'error');
        }
        return !hasError;
    }

    titleInput.addEventListener('input', function () {
        if (titleError.textContent) {
            titleError.textContent = '';
            titleInput.classList.remove('invalid');
        }
    });
    contentInput.addEventListener('input', function () {
        if (contentError.textContent) {
            contentError.textContent = '';
            contentInput.classList.remove('invalid');
        }
    });

    form.addEventListener('submit', function (e) {
        e.preventDefault();
        clearMessage();
        clearFieldErrors();

        var id = getPostIdFromQuery();
        if (!id) {
            showMessage('잘못된 접근입니다. 게시글 ID가 없습니다.', 'error');
            return;
        }

        if (!validateForm()) {
            return;
        }

        var title = titleInput.value.trim();
        var content = contentInput.value.trim();
        submitBtn.disabled = true;

        fetch('/api/posts/' + encodeURIComponent(id), {
            method: 'PUT',
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
                if (result.status === 200) {
                    showMessage('게시글이 수정되었습니다.', 'success');
                } else if (result.status === 400 && result.data.errors) {
                    showMessage(result.data.message || '입력값을 확인해 주세요.', 'error');
                    showFieldErrors(result.data.errors);
                } else if (result.status === 404) {
                    showMessage(result.data.message || '게시글을 찾을 수 없습니다.', 'error');
                } else {
                    showMessage(result.data.message || '수정에 실패했습니다.', 'error');
                }
            })
            .catch(function () {
                showMessage('네트워크 오류가 발생했습니다. 다시 시도해 주세요.', 'error');
            })
            .finally(function () {
                submitBtn.disabled = false;
            });
    });

    document.addEventListener('DOMContentLoaded', loadPost);
})();

