# ver04 PRD 기반 – 게시글 등록·조회 API Summary

## 1. 게시글 등록

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

---

## 2. 게시글 목록 조회

### 목록 조회 Endpoint

`GET /api/posts`

### 목록 조회 Response JSON (200 OK)

```json
[
  {
    "id": 1,
    "title": "첫 번째 글",
    "content": "본문 내용",
    "createdAt": "2025-03-10T12:00:00",
    "updatedAt": "2025-03-10T12:00:00"
  },
  {
    "id": 2,
    "title": "두 번째 글",
    "content": "본문 내용",
    "createdAt": "2025-03-10T13:00:00",
    "updatedAt": "2025-03-10T13:00:00"
  }
]
```

---

## 3. 게시글 상세 조회

### 상세 조회 Endpoint

`GET /api/posts/{id}`

### 상세 조회 Response JSON (200 OK)

```json
{
  "id": 1,
  "title": "첫 번째 글",
  "content": "본문 내용",
  "createdAt": "2025-03-10T12:00:00",
  "updatedAt": "2025-03-10T12:00:00"
}
```

---

## 4. 게시글 수정

### API Endpoint

`PUT /api/posts/{id}`

### Request JSON

```json
{
  "title": "수정된 제목",
  "content": "수정된 본문 내용"
}
```

### Response JSON (200 OK)

```json
{
  "id": 1,
  "title": "수정된 제목",
  "content": "수정된 본문 내용",
  "createdAt": "2025-03-10T12:00:00",
  "updatedAt": "2025-03-10T13:00:00"
}
```

### Error Response JSON 예시

#### 1) Validation 실패 (400 Bad Request)

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

#### 2) 대상 게시글이 없는 경우 (404 Not Found)

```json
{
  "message": "게시글을 찾을 수 없습니다. id=9999"
}
```

