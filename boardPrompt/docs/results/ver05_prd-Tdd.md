# ver05 PRD·TDD 기반 – 게시글 삭제 API Summary

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
