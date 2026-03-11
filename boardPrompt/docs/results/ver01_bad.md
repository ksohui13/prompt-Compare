# Validation 적용 요약 (PRD 기준)

## 적용 대상

- **게시글 등록** (POST /api/posts)
- **게시글 수정** (PUT /api/posts/{id})

---

## 필드별 검증

| API | 필드 | 적용 검증 | 설명 |
|-----|------|-----------|------|
| 게시글 등록 | title | `@NotBlank` | 제목 필수, null·빈 문자열·공백만 있는 값 불가 |
| 게시글 등록 | content | `@NotBlank` | 내용 필수, null·빈 문자열·공백만 있는 값 불가 |
| 게시글 수정 | title | `@NotBlank` | 제목 필수, null·빈 문자열·공백만 있는 값 불가 |
| 게시글 수정 | content | `@NotBlank` | 내용 필수, null·빈 문자열·공백만 있는 값 불가 |

- **도구**: Jakarta Bean Validation (`jakarta.validation.constraints.NotBlank`)
- **메시지**: title → `"제목은 필수입니다"`, content → `"내용은 필수입니다"`
- **진입점**: Controller에서 `@Valid`로 `CreatePostRequest` / `UpdatePostRequest` 검증 후 실패 시 `MethodArgumentNotValidException` 발생 → `GlobalExceptionHandler`에서 400 응답 처리

---

## Validation 실패 응답 예시

### HTTP 상태

`400 Bad Request`

### Response JSON

**제목·내용 모두 비어 있는 경우**

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

**제목만 비어 있는 경우**

```json
{
  "message": "입력값 검증에 실패했습니다",
  "errors": [
    {
      "field": "title",
      "message": "제목은 필수입니다"
    }
  ]
}
```

**내용만 비어 있는 경우**

```json
{
  "message": "입력값 검증에 실패했습니다",
  "errors": [
    {
      "field": "content",
      "message": "내용은 필수입니다"
    }
  ]
}
```
