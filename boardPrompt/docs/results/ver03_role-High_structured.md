# API Summary

## 게시글 등록

**Endpoint**  
`POST /api/posts`

**HTTP Method**  
POST

**Request JSON**
```json
{
  "title": "string",
  "content": "string"
}
```

**Response JSON** (201 Created)
```json
{
  "id": 1,
  "title": "string",
  "content": "string"
}
```

**Error Response JSON** (400 Bad Request - validation 등)
```json
{
  "timestamp": "string",
  "status": 400,
  "error": "Bad Request",
  "message": "string",
  "path": "/api/posts"
}
```

---

## 게시글 목록 조회

**Endpoint**  
`GET /api/posts`

**HTTP Method**  
GET

**Request**  
없음 (Query parameter 없음)

**Response JSON** (200 OK)
```json
[
  {
    "id": 1,
    "title": "string",
    "content": "string"
  }
]
```

**Error Response JSON**  
일반적으로 200 + 빈 배열 `[]`

---

## 게시글 상세 조회

**Endpoint**  
`GET /api/posts/{id}`

**HTTP Method**  
GET

**Request**  
Path variable: `id` (Long)

**Response JSON** (200 OK)
```json
{
  "id": 1,
  "title": "string",
  "content": "string"
}
```

**Error Response JSON** (404 Not Found)
```json
// Body 없음, HTTP 404
```
