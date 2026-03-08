# 구조화 프롬프트 실험

## 목적

구조화된 프롬프트가 코드 품질에 어떤 영향을 주는지 비교한다.

---

## 공통 규칙

Before writing code explain reasoning step by step.

출력 형식

1 Plan  
2 Reasoning  
3 Implementation  
4 Files created  
5 Verification  

---

## 1 회원가입

회원가입 API 구현

endpoint  
POST /api/auth/signup

fields

email  
password  
nickname

조건

- 이메일 중복 검사
- BCrypt 암호화

Scope 제한

회원가입 기능만 구현  
다른 기능 수정 금지

---

## 2 로그인

로그인 API 구현

endpoint  
POST /api/auth/login

조건

JWT access token 발급

Scope 제한

로그인 기능만 구현

---

## 3 JWT 인증

Spring Security + JWT 인증 구성

조건

- 게시글 조회 → 인증 필요 없음
- 등록 수정 삭제 → 인증 필요

Scope 제한

보안 설정만 수정

---

## 4 게시글 등록

endpoint

POST /api/posts

fields

title  
content

작성자는 로그인 사용자

Scope 제한

등록 기능만 구현

---

## 5 게시글 목록 조회

endpoint

GET /api/posts

최신순 정렬

Scope 제한

조회 기능만 구현

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

작성자만 수정 삭제 가능

---

## 10 Validation

title / content validation 적용

---

## 11 테스트

JUnit 테스트 작성