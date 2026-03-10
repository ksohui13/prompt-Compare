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
