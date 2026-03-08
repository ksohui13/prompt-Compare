# 게시글 CRUD 서비스 PRD

## 문서 목적

회원가입 + 로그인 + 게시글 CRUD 기능을 가진 서비스 요구사항 정의

---

## 기술 스택

Java 17  
Spring Boot 3.x  
Gradle  
Spring Data JPA  
Spring Security  
H2 Database  
HTML CSS JS

---

## 기능 요구사항

### 회원가입

POST /api/auth/signup

fields

email  
password  
nickname

조건

- 이메일 중복 불가
- BCrypt 암호화

---

### 로그인

POST /api/auth/login

JWT access token 발급

---

### 게시글 등록

POST /api/posts

fields

title  
content

작성자는 로그인 사용자

---

### 게시글 목록 조회

GET /api/posts

---

### 게시글 상세 조회

GET /api/posts/{id}

---

### 게시글 수정

PUT /api/posts/{id}

작성자만 수정 가능

---

### 게시글 삭제

DELETE /api/posts/{id}

작성자만 삭제 가능