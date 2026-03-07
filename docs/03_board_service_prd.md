# 게시글 CRUD 서비스 PRD

## 1. 문서 목적
이 문서는 **회원가입/로그인 + 게시글 CRUD** 기능을 가진 간단한 게시글 서비스를 구현하기 위한 제품 요구사항 문서다.  
PRD 기반 실험과 PRD + TDD 실험에서 공통으로 사용한다.

---

## 2. 제품 개요
인증된 사용자는 게시글을 작성, 수정, 삭제할 수 있고, 누구나 게시글 목록과 상세를 조회할 수 있는 백엔드 API 서비스를 만든다.

---

## 3. 목표
- 회원가입과 로그인 기능을 제공한다.
- 로그인 성공 시 JWT 기반 인증을 사용한다.
- 인증된 사용자는 게시글을 작성할 수 있다.
- 게시글 수정/삭제는 작성자 본인만 가능해야 한다.
- 누구나 게시글 목록과 상세를 조회할 수 있어야 한다.
- 유지보수 가능한 계층 구조와 예외 처리 체계를 갖춘다.

---

## 4. 대상 사용자와 권한

### 4.1 비회원(게스트)
- 게시글 목록 조회 가능
- 게시글 상세 조회 가능

### 4.2 회원(인증 사용자)
- 회원가입 가능
- 로그인 가능
- 게시글 등록 가능
- 본인이 작성한 게시글 수정/삭제 가능

---

## 5. 범위(In Scope)
- 회원가입
- 로그인
- JWT 발급
- 게시글 등록
- 게시글 목록 조회
- 게시글 상세 조회
- 게시글 수정
- 게시글 삭제
- 작성자 권한 체크
- 요청값 검증
- 공통 예외 처리
- 핵심 기능 테스트

---

## 6. 범위 밖(Out of Scope)
- 댓글 기능
- 파일 업로드
- 관리자 기능
- Refresh Token
- 소셜 로그인
- 페이징, 검색, 정렬 옵션의 고도화
- Swagger/OpenAPI 문서 자동화
- 프론트엔드 UI

---

## 7. 기술 가정
- Language: Java 21
- Framework: Spring Boot 3.x
- Build: Gradle
- Persistence: Spring Data JPA
- DB: MySQL (런타임)
- Test: JUnit 5
- Security: Spring Security + JWT
- Package root: `com.example.board`

---

## 8. 선호 아키텍처
다음과 같은 계층형 구조를 선호한다.

- controller
- service
- repository
- domain(entity)
- dto
- config
- exception

과도한 추상화는 피하되, 책임 분리는 유지한다.

---

## 9. 기능 요구사항

### FR-01. 회원가입
- 엔드포인트: `POST /api/auth/signup`
- 입력: email, password, nickname
- 이메일은 중복될 수 없다.
- 비밀번호는 BCrypt로 암호화해 저장한다.
- 성공 시 회원 id, email, nickname 을 반환하거나 성공 메시지를 반환한다.

### FR-02. 로그인
- 엔드포인트: `POST /api/auth/login`
- 입력: email, password
- 이메일과 비밀번호가 맞으면 JWT access token 을 발급한다.
- 실패 시 인증 오류를 반환한다.

### FR-03. 게시글 등록
- 엔드포인트: `POST /api/posts`
- 인증 필요
- 입력: title, content
- 작성자는 로그인 사용자 기준으로 저장한다.
- 성공 시 게시글 정보(id, title, content, author, createdAt)를 반환한다.

### FR-04. 게시글 목록 조회
- 엔드포인트: `GET /api/posts`
- 인증 불필요
- 최신 글이 먼저 내려오도록 정렬한다.
- 목록 응답에는 본문 전체를 포함하지 않는다.
- 각 항목은 id, title, author, createdAt 을 포함한다.

### FR-05. 게시글 상세 조회
- 엔드포인트: `GET /api/posts/{id}`
- 인증 불필요
- 응답에는 id, title, content, author, createdAt, updatedAt 을 포함한다.
- 존재하지 않으면 리소스 없음 오류를 반환한다.

### FR-06. 게시글 수정
- 엔드포인트: `PUT /api/posts/{id}`
- 인증 필요
- 입력: title, content
- 작성자 본인만 수정 가능하다.
- 성공 시 수정된 게시글 정보를 반환한다.

### FR-07. 게시글 삭제
- 엔드포인트: `DELETE /api/posts/{id}`
- 인증 필요
- 작성자 본인만 삭제 가능하다.
- 성공 시 일관된 성공 응답을 반환한다.

---

## 10. 도메인 모델

### User
- id
- email
- password
- nickname
- createdAt

제약
- email unique
- password 암호화 저장

### Post
- id
- title
- content
- author(User)
- createdAt
- updatedAt

제약
- title: 1~100자
- content: 1~5000자

---

## 11. 인증 및 권한 규칙
- JWT Bearer Token 기반 인증 사용
- 로그인 시 access token 발급
- 인증 없이 허용
  - `GET /api/posts`
  - `GET /api/posts/{id}`
  - `POST /api/auth/signup`
  - `POST /api/auth/login`
- 인증 필요
  - `POST /api/posts`
  - `PUT /api/posts/{id}`
  - `DELETE /api/posts/{id}`
- 수정/삭제는 작성자 본인만 허용

---

## 12. Validation 규칙

### 회원가입
- email: 이메일 형식
- password: 최소 8자
- nickname: 최소 2자

### 로그인
- email: 필수
- password: 필수

### 게시글
- title: 1~100자
- content: 1~5000자

---

## 13. 예외 처리 요구사항
다음 경우를 구분해 JSON 형태로 응답한다.

- Validation 실패
- 인증 실패
- 인가 실패(권한 없음)
- 리소스 없음
- 비즈니스 예외(예: 중복 이메일)

권장 응답 예시

```json
{
  "code": "POST_NOT_FOUND",
  "message": "게시글을 찾을 수 없습니다."
}
```

---

## 14. 비기능 요구사항
- 코드 구조가 이해하기 쉬워야 한다.
- DTO와 Entity 역할을 구분한다.
- 예외 응답 형식을 통일한다.
- 게시글 목록과 상세는 인증 없이 정상 동작해야 한다.
- 핵심 기능에 대한 테스트가 있어야 한다.

---

## 15. 완료 기준(Definition of Done)
아래가 모두 만족되면 완료로 본다.

1. 회원가입 API 동작
2. 로그인 API 동작 및 JWT 발급
3. 게시글 등록/목록/상세/수정/삭제 API 동작
4. 작성자 본인만 수정/삭제 가능
5. Validation 적용
6. 공통 예외 처리 적용
7. 핵심 기능 테스트 통과
8. 빌드 성공