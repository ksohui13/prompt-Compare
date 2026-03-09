# 게시글 CRUD 서비스 PRD

## 문서 목적

본 문서는 회원가입, 로그인, 게시글 CRUD 기능을 제공하는  
게시판 서비스의 기능 요구사항을 정의한다.

이 PRD는 **AI 기반 개발 실험을 위한 요구사항 문서**이며  
AI는 이 문서를 기반으로 서비스를 구현해야 한다.

---

# 기술 스택

Backend

Java 17  
Spring Boot 3.x  
Gradle  
Spring Data JPA  
Spring Security  
H2 Database  

Frontend

HTML  
CSS  
JavaScript

---

# 인증 방식

인증 방식은 **JWT 기반 인증**을 사용한다.

로그인 성공 시 **Access Token**을 발급한다.

인증이 필요한 API 호출 시

```
Authorization: Bearer {access_token}
```

헤더를 사용한다.

---

# 기능 요구사항

## 1. 회원가입

사용자는 이메일과 비밀번호를 이용해 회원가입할 수 있어야 한다.

### Endpoint

POST /api/auth/signup

### Request Fields

email  
password  
nickname

### 요구사항

- 이메일은 중복될 수 없다
- 비밀번호는 BCrypt로 암호화하여 저장한다

---

## 2. 로그인

사용자는 이메일과 비밀번호로 로그인할 수 있어야 한다.

### Endpoint

POST /api/auth/login

### Request Fields

email  
password

### 요구사항

- 로그인 성공 시 JWT Access Token을 발급한다
- 토큰은 이후 인증이 필요한 API 호출에 사용된다

---

## 3. 게시글 등록

로그인한 사용자는 게시글을 작성할 수 있다.

### Endpoint

POST /api/posts

### Request Fields

title  
content

### 요구사항

- 작성자는 로그인 사용자로 설정된다
- 인증이 필요하다

---

## 4. 게시글 목록 조회

사용자는 게시글 목록을 조회할 수 있다.

### Endpoint

GET /api/posts

### 요구사항

- 인증 없이 조회 가능

---

## 5. 게시글 상세 조회

사용자는 게시글 상세 내용을 조회할 수 있다.

### Endpoint

GET /api/posts/{id}

### 요구사항

- 인증 없이 조회 가능

---

## 6. 게시글 수정

게시글 작성자는 자신의 게시글을 수정할 수 있다.

### Endpoint

PUT /api/posts/{id}

### Request Fields

title  
content

### 요구사항

- 작성자만 수정 가능
- 인증 필요

---

## 7. 게시글 삭제

게시글 작성자는 자신의 게시글을 삭제할 수 있다.

### Endpoint

DELETE /api/posts/{id}

### 요구사항

- 작성자만 삭제 가능
- 인증 필요

---

# 권한 정책

| 기능 | 인증 필요 |
|-----|-----|
| 회원가입 | ❌ |
| 로그인 | ❌ |
| 게시글 목록 조회 | ❌ |
| 게시글 상세 조회 | ❌ |
| 게시글 등록 | ✅ |
| 게시글 수정 | ✅ |
| 게시글 삭제 | ✅ |

---

# 프론트 요구사항

다음 화면을 제공해야 한다.

회원가입 화면  
로그인 화면  
게시글 목록 화면  
게시글 상세 화면  
게시글 작성 화면  
게시글 수정 화면

프론트는 **HTML / CSS / JavaScript** 기반으로 구현한다.

---

# 검증 요구사항

다음 입력값 검증을 적용해야 한다.

회원가입

- email 형식 검증
- password 최소 길이

게시글

- title 필수
- content 필수

---

# 테스트 요구사항

핵심 기능에 대한 테스트가 필요하다.

대상

회원가입  
로그인  
게시글 등록  
게시글 수정 권한