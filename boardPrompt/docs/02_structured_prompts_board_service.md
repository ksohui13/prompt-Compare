# 게시글 CRUD 서비스 - 구조화 프롬프트 실험

## 목적

구조화된 프롬프트가 코드 품질에 어떤 영향을 주는지 비교한다.

이 실험에서는 기능 요구사항, endpoint, scope 제한 등을 **구조화된 형태로 제공한다.**

Bad Prompt 실험과 비교하여 다음을 확인한다.

- 코드 구조의 안정성
- API 설계 일관성
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

# 4 게시글 등록

## 4-1 기능 요구사항

endpoint

POST /api/posts

fields

title  
content

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

Scope 제한
등록 기능만 구현

작업이 끝나면 request / response JSON 예시를 알려줘.
````

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

* 등록 API 구조
* 프론트 입력 폼

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

* 정렬 처리
* 응답 구조
* 프론트 리스트 렌더링

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

* ID 조회 구조
* 응답 데이터 구성
* 프론트 데이터 표시

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

* 수정 로직 구조
* 프론트 수정 폼

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

* 삭제 API 구조
* 프론트 삭제 흐름

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
수정 삭제 버튼 조건부 표시 처리
```

---

## 9-4 결과 비교 포인트

* 서버 권한 검증
* 프론트 UI 처리

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

* DTO validation
* 에러 응답 구조
* 프론트 입력 검증

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

* 테스트 범위
* 실행 가능성
* 핵심 기능 커버 여부
