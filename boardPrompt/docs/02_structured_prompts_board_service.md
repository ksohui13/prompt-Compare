# 게시글 CRUD 서비스 - 구조화 프롬프트 실험

## 목적

구조화된 프롬프트가 코드 품질에 어떤 영향을 주는지 비교한다.

이 실험에서는 기능 요구사항, endpoint, scope 제한 등을 **구조화된 형태로 제공한다.**

Bad Prompt 실험과 비교하여 다음을 확인한다.

- 코드 구조의 안정성
- API 설계 일관성
- 인증 처리 방식
- 프론트 연동 가능성

이 문서는 **공통 프롬프트를 먼저 입력한 뒤 사용한다.**

---

# 공통 규칙

Before writing code explain reasoning step by step.

출력 형식

1 Plan  
2 Reasoning  
3 Implementation  
4 Files created  
5 Verification  

작업이 끝나면 반드시 **API endpoint와 request/response JSON 예시를 제공한다.**

프론트는 **HTML / CSS / JavaScript**로 구현한다.

---

# 실행 방식

각 기능은 다음 순서로 진행한다.

1 기능 요구사항 확인  
2 백엔드 프롬프트 입력  
3 프론트 프롬프트 입력  
4 결과 비교

---

# 1 회원가입 구현

## 1-1 기능 요구사항

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

## 1-2 백엔드 프롬프트

```text
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

작업이 끝나면 API endpoint와 request / response JSON 예시를 알려줘.
```

---

## 1-3 프론트 프롬프트

```text
회원가입 화면 구현

HTML, CSS, JavaScript 사용

화면 요구사항
- email
- password
- nickname 입력 폼

회원가입 API 호출 연결
POST /api/auth/signup

회원가입 성공 / 실패 메시지 표시
```

---

## 1-4 결과 비교 포인트

- 회원 엔티티 구조
- 이메일 중복 검사 위치
- 암호화 처리 방식
- API 응답 구조
- 프론트 폼과 API 연결

---

# 2 로그인 구현

## 2-1 기능 요구사항

로그인 API 구현

endpoint  
POST /api/auth/login

조건

JWT access token 발급

Scope 제한

로그인 기능만 구현

---

## 2-2 백엔드 프롬프트

```text
로그인 API 구현

endpoint
POST /api/auth/login

조건
JWT access token 발급

Scope 제한
로그인 기능만 구현

작업이 끝나면 request / response JSON 예시를 알려줘.
```

---

## 2-3 프론트 프롬프트

```text
로그인 화면 구현

HTML, CSS, JavaScript 사용

요구사항
- email
- password 입력

로그인 API 호출
POST /api/auth/login

성공 시 JWT 저장
```

---

## 2-4 결과 비교 포인트

- 인증 흐름 구조
- JWT 발급 방식
- 로그인 실패 처리
- 프론트 토큰 저장 방식

---

# 3 JWT 인증

## 3-1 기능 요구사항

Spring Security + JWT 인증 구성

조건

- 게시글 조회 → 인증 필요 없음
- 등록 수정 삭제 → 인증 필요

Scope 제한

보안 설정만 수정

---

## 3-2 백엔드 프롬프트

```text
Spring Security + JWT 인증 구성

조건
- 게시글 조회는 인증 필요 없음
- 게시글 등록 수정 삭제는 인증 필요

Scope 제한
보안 설정만 수정

작업이 끝나면 인증이 필요한 API와 인증 방식 설명.
```

---

## 3-3 프론트 프롬프트

```text
JWT 토큰을 요청에 포함하도록 수정

HTML, CSS, JavaScript 사용

요구사항
- 로그인 시 토큰 저장
- 인증 필요한 API 호출 시 Authorization header 추가
```

---

## 3-4 결과 비교 포인트

- Security 설정 구조
- JWT 필터 위치
- 인증 흐름
- 프론트 토큰 전달 방식

---

# 4 게시글 등록

## 4-1 기능 요구사항

endpoint

POST /api/posts

fields

title  
content

작성자는 로그인 사용자

Scope 제한

등록 기능만 구현

---

## 4-2 백엔드 프롬프트

```text
게시글 등록 API 구현

endpoint
POST /api/posts

fields
title
content

작성자는 로그인 사용자

Scope 제한
등록 기능만 구현

작업이 끝나면 request / response JSON 예시를 알려줘.
```

---

## 4-3 프론트 프롬프트

```text
게시글 등록 화면 구현

HTML, CSS, JavaScript 사용

필드
title
content

게시글 등록 API 호출
POST /api/posts
```

---

## 4-4 결과 비교 포인트

- 작성자 처리 방식
- 인증 사용자 연결
- 등록 API 구조
- 프론트 입력 폼

---

# 5 게시글 목록 조회

## 5-1 기능 요구사항

endpoint

GET /api/posts

최신순 정렬

Scope 제한

조회 기능만 구현

---

## 5-2 백엔드 프롬프트

```text
게시글 목록 조회 API 구현

endpoint
GET /api/posts

조건
최신순 정렬

Scope 제한
조회 기능만 구현

response JSON 예시 제공
```

---

## 5-3 프론트 프롬프트

```text
게시글 목록 화면 구현

HTML, CSS, JavaScript 사용

목록 조회 API 호출
GET /api/posts

게시글 리스트 표시
```

---

## 5-4 결과 비교 포인트

- 정렬 처리
- 응답 구조
- 프론트 리스트 렌더링

---

# 6 게시글 상세 조회

## 6-1 기능 요구사항

endpoint

GET /api/posts/{id}

---

## 6-2 백엔드 프롬프트

```text
게시글 상세 조회 API 구현

endpoint
GET /api/posts/{id}

response JSON 예시 제공
```

---

## 6-3 프론트 프롬프트

```text
게시글 상세 화면 구현

HTML, CSS, JavaScript 사용

상세 조회 API 호출
GET /api/posts/{id}
```

---

## 6-4 결과 비교 포인트

- ID 조회 구조
- 응답 데이터 구성
- 프론트 데이터 표시

---

# 7 게시글 수정

## 7-1 기능 요구사항

endpoint

PUT /api/posts/{id}

---

## 7-2 백엔드 프롬프트

```text
게시글 수정 API 구현

endpoint
PUT /api/posts/{id}

request JSON 예시 제공
```

---

## 7-3 프론트 프롬프트

```text
게시글 수정 화면 구현

HTML, CSS, JavaScript 사용

수정 API 호출
PUT /api/posts/{id}
```

---

## 7-4 결과 비교 포인트

- 수정 로직 구조
- 인증 처리
- 프론트 수정 폼

---

# 8 게시글 삭제

## 8-1 기능 요구사항

endpoint

DELETE /api/posts/{id}

---

## 8-2 백엔드 프롬프트

```text
게시글 삭제 API 구현

endpoint
DELETE /api/posts/{id}

response JSON 예시 제공
```

---

## 8-3 프론트 프롬프트

```text
게시글 삭제 기능 구현

HTML, CSS, JavaScript 사용

삭제 API 호출
DELETE /api/posts/{id}
```

---

## 8-4 결과 비교 포인트

- 삭제 API 구조
- 인증 처리
- 프론트 삭제 흐름

---

# 9 작성자 권한

## 9-1 기능 요구사항

작성자만 수정 삭제 가능

---

## 9-2 백엔드 프롬프트

```text
작성자만 수정 삭제 가능하게 구현
```

---

## 9-3 프론트 프롬프트

```text
작성자가 아닐 경우 수정 삭제 버튼 숨김 처리
```

---

## 9-4 결과 비교 포인트

- 서버 권한 검증
- 프론트 UI 처리

---

# 10 Validation

## 10-1 기능 요구사항

title / content validation 적용

---

## 10-2 백엔드 프롬프트

```text
title / content validation 적용
```

---

## 10-3 프론트 프롬프트

```text
입력값 validation 추가
```

---

## 10-4 결과 비교 포인트

- DTO validation
- 에러 응답 구조
- 프론트 입력 검증

---

# 11 테스트

## 11-1 기능 요구사항

JUnit 테스트 작성

---

## 11-2 백엔드 프롬프트

```text
JUnit 테스트 작성
```

---

## 11-3 프론트 프롬프트

```text
화면 동작 테스트 방법 정리
```

---

## 11-4 결과 비교 포인트

- 테스트 범위
- 실행 가능성
- 핵심 기능 커버 여부