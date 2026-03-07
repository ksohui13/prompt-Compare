# 게시글 CRUD 서비스 PRD

## 1. 문서 목적

회원가입 + 로그인 + 게시글 CRUD 기능을 가진 간단한 서비스 구현을 위한 요구사항 문서

---

## 2. 제품 개요

사용자는 로그인 후 게시글을 작성할 수 있다.  
모든 사용자는 게시글을 조회할 수 있다.

---

## 3. 목표

- 회원가입
- 로그인
- JWT 인증
- 게시글 CRUD

---

## 4. 사용자

게스트

- 게시글 조회 가능

회원

- 게시글 작성 가능
- 게시글 수정 가능
- 게시글 삭제 가능

---

## 5. 기술 스택

- Java 21
- Spring Boot
- Gradle
- Spring Data JPA
- Spring Security
- H2 Database
- HTML / CSS / JS

---

## 6. 기능 요구사항

### 회원가입

POST /api/auth/signup

필드

email  
password  
nickname

---

### 로그인

POST /api/auth/login

JWT 토큰 발급

---

### 게시글 등록

POST /api/posts

필드

title  
content

---

### 게시글 목록 조회

GET /api/posts

---

### 게시글 상세 조회

GET /api/posts/{id}

---

### 게시글 수정

PUT /api/posts/{id}

---

### 게시글 삭제

DELETE /api/posts/{id}

작성자만 가능