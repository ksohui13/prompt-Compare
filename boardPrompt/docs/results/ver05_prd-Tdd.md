# ver05 PRD 기반 – 게시글 등록 API Summary

## 게시글 등록

### API Endpoint

`POST /api/posts`

### HTTP Method

POST

### Request JSON

```json
{
  "title": "게시글 제목",
  "content": "게시글 본문 내용"
}
```

| 필드    | 타입   | 필수 | 설명   |
|---------|--------|------|--------|
| title   | string | O    | 제목   |
| content | string | O    | 내용   |

### Response JSON (201 Created)

```json
{
  "id": 1,
  "title": "게시글 제목",
  "content": "게시글 본문 내용",
  "createdAt": "2025-03-10T12:00:00",
  "updatedAt": "2025-03-10T12:00:00"
}
```

### Error Response JSON

#### 1) Validation 실패 (400 Bad Request)

- `title` 또는 `content`가 비어 있거나 누락된 경우

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

#### 2) 요청 본문 오류 (400 Bad Request)

- JSON 형식 오류 등으로 body 파싱 실패 시 Spring 기본 에러 응답 등이 반환될 수 있음.
