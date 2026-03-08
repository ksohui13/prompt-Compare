# 게시글 CRUD 서비스 - 구조화 프롬프트 실험

## 목적

기능 단위 + 명확한 제약 조건 + 산출물 구조를 가진 프롬프트가
코드 품질에 어떤 영향을 주는지 비교한다.

---

## 시스템 환경

- Java 21
- Spring Boot 3.x
- Gradle
- H2 Database
- package: com.example.board

---

## 공통 규칙

너는 **시니어 Spring Boot 백엔드 개발자**다.

반드시 다음을 지켜라.

- controller / service / repository 구조 유지
- DTO 사용
- validation 적용
- JWT 인증 적용

출력 형식

1. 변경 계획
2. 코드 변경
3. 생성 파일 목록

---

## 1 회원가입

회원가입 API를 구현해라.

요구사항

endpoint  
POST /api/auth/signup

필드

email  
password  
nickname

조건

- 이메일 중복 검사
- BCrypt 암호화
- DTO 사용

---

## 2 로그인

로그인 API를 구현해라.

endpoint

POST /api/auth/login

요구사항

- email/password 검증
- JWT 토큰 발급

---

## 3 JWT 보안

Spring Security + JWT 인증 적용

요구사항

- 게시글 조회는 인증 없이 가능
- 등록/수정/삭제는 인증 필요

---

## 4 게시글 등록

POST /api/posts

필드

title  
content

---

## 5 게시글 목록 조회

GET /api/posts

최신순 정렬

---

## 6 게시글 상세 조회

GET /api/posts/{id}

---

## 7 게시글 수정

PUT /api/posts/{id}

---

## 8 게시글 삭제

DELETE /api/posts/{id}

---

## 9 작성자 권한

작성자만 수정/삭제 가능하도록 구현

---

## 10 Validation

title/content validation 적용

---

## 11 테스트

JUnit 기반 테스트 작성