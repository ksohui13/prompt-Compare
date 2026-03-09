# 게시글 CRUD 서비스 PRD

## 문서 목적

본 문서는 게시글 CRUD 기능을 제공하는  
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
H2 Database  

Frontend

HTML  
CSS  
JavaScript

---

# 기능 요구사항

## 1. 게시글 등록

사용자는 게시글을 작성할 수 있다.

### Endpoint

POST /api/posts

### Request Fields

title  
content

---

## 2. 게시글 목록 조회

사용자는 게시글 목록을 조회할 수 있다.

### Endpoint

GET /api/posts

---

## 3. 게시글 상세 조회

사용자는 게시글 상세 내용을 조회할 수 있다.

### Endpoint

GET /api/posts/{id}

---

## 4. 게시글 수정

사용자는 게시글을 수정할 수 있다.

### Endpoint

PUT /api/posts/{id}

### Request Fields

title  
content

---

## 5. 게시글 삭제

사용자는 게시글을 삭제할 수 있다.

### Endpoint

DELETE /api/posts/{id}

---

# 프론트 요구사항

다음 화면을 제공해야 한다.

게시글 목록 화면  
게시글 상세 화면  
게시글 작성 화면  
게시글 수정 화면

프론트는 **HTML / CSS / JavaScript** 기반으로 구현한다.

---

# 검증 요구사항

다음 입력값 검증을 적용해야 한다.

게시글

- title 필수
- content 필수

---

# 테스트 요구사항

핵심 기능에 대한 테스트가 필요하다.

대상

게시글 등록  
게시글 수정  
게시글 삭제