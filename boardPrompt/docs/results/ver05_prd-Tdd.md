# ver05 PRD·TDD 기반 – API Summary

---

## 공통 예외 처리

### 공통 에러 응답 구조

모든 API 오류는 아래 형식의 JSON으로 통일한다.

| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| message | string | O | 사용자/클라이언트용 안내 메시지 |
| errors | array | X | 필드별 검증 오류 목록 (Validation 실패 시만 존재) |

**errors 배열 요소 (필드 오류)**

| 필드 | 타입 | 설명 |
|------|------|------|
| field | string | 검증 실패한 필드명 |
| message | string | 해당 필드에 대한 오류 메시지 |

- `errors`가 없을 때는 JSON에서 생략하거나 `null`이다.

---

### 예외별 응답 예시

#### 1) Validation 실패 (400 Bad Request)

- **발생 상황**: 게시글 등록/수정 요청 시 `title` 또는 `content`가 비어 있거나 공백만 있는 경우.
- **HTTP 상태**: `400 Bad Request`

```json
{
  "message": "입력값 검증에 실패했습니다",
  "errors": [
    {
      "field": "title",
      "message": "제목은 필수입니다"
    },
    {
      "field": "content",
      "message": "내용은 필수입니다"
    }
  ]
}
```

#### 2) 게시글 없음 (404 Not Found)

- **발생 상황**: 조회/수정/삭제 시 해당 `id`의 게시글이 존재하지 않는 경우.
- **HTTP 상태**: `404 Not Found`

```json
{
  "message": "게시글을 찾을 수 없습니다. id=9999"
}
```

#### 3) 기타 서버 오류 (500 Internal Server Error)

- **발생 상황**: 처리되지 않은 예외 발생 시.
- **HTTP 상태**: `500 Internal Server Error`

```json
{
  "message": "서버 오류가 발생했습니다."
}
```

---

## 게시글 삭제

### API Endpoint

`DELETE /api/posts/{id}`

### HTTP Method

DELETE

### Response (204 No Content)

성공 시 응답 본문 없음. HTTP 상태 코드 `204 No Content`만 반환한다.

### Error Response JSON 예시

#### 1) 대상 게시글이 없는 경우 (404 Not Found)

```json
{
  "message": "게시글을 찾을 수 없습니다. id=9999"
}
```

#### 2) 기타 서버 오류 (500 Internal Server Error)

서버 내부 오류 시 공통 에러 응답 구조 예시:

```json
{
  "message": "서버 오류가 발생했습니다."
}
```
