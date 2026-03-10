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

