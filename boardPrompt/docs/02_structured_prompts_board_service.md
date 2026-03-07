# 게시글 CRUD 서비스 - 구조화 프롬프트 실험 세트

## 목적

기능 단위 + 명확한 제약 조건 + 산출물 구조를 가진 프롬프트가
코드 품질에 어떤 영향을 주는지 비교하기 위한 실험이다.

---

## 공통 실험 조건

초기 프로젝트 상태

- Java 21
- Spring Boot 3.x
- Gradle
- package: com.example.board
- DB: H2

---

## 1 회원가입

너는 시니어 Spring Boot 백엔드 개발자다.

회원가입 API를 구현해라.

요구사항

- endpoint: POST /api/auth/signup
- 필드: email, password, nickname
- 이메일 중복 검사
- 비밀번호 BCrypt 암호화
- DTO 사용
- 계층 구조 유지

---

## 2 로그인

로그인 API를 구현해라.

요구사항

- endpoint: POST /api/auth/login
- JWT 발급
- email/password 검증

---

## 3 JWT 보안 구성

JWT 인증이 동작하도록 Spring Security 설정을 구성해라.

요구사항

- 게시글 조회는 인증 없이 가능
- 등록/수정/삭제는 인증 필요

---

## 4 게시글 등록

게시글 등록 기능 구현

endpoint

POST /api/posts

필드

title  
content

작성자는 로그인 사용자

---

## 5 게시글 목록 조회

endpoint

GET /api/posts

최신순 정렬

---

## 6 게시글 상세 조회

endpoint

GET /api/posts/{id}

---

## 7 게시글 수정

endpoint

PUT /api/posts/{id}

---

## 8 게시글 삭제

endpoint

DELETE /api/posts/{id}

---

## 9 작성자 권한

작성자만 수정/삭제 가능하도록 구현

---

## 10 Validation

title  
content  
검증 적용

---

## 11 테스트

JUnit 기반 테스트 작성