# PRD + Bottom-Up + TDD 실험

## 개발 방식

Test Driven Development

Red → Green → Refactor

---

## 개발 규칙

반드시 다음을 따른다.

1 실패하는 테스트 작성  
2 테스트 실행  
3 최소 코드 구현  
4 Refactor  

Before writing code explain reasoning step by step.

출력 형식

1 Plan  
2 Tests  
3 Implementation  
4 Refactor  
5 Verification  

---

## 1 PRD 분석

PRD.md 기반 TDD 개발 전략 작성

출력

- 구현 단계
- 테스트 전략
- 위험 요소

Scope

코드 작성 금지

---

## 2 회원가입

TDD 방식 구현

Tests

회원가입 성공  
이메일 중복 실패

Scope

회원가입 기능만 구현

---

## 3 로그인

TDD 방식 구현

Tests

로그인 성공  
로그인 실패

Scope

로그인 기능만 구현

---

## 4 JWT 인증

JWT 인증 테스트 작성 후 구현

---

## 5 게시글 등록

게시글 등록 TDD 구현

---

## 6 게시글 조회

목록 조회 테스트  
상세 조회 테스트

---

## 7 게시글 수정

작성자만 수정 가능 테스트 후 구현

---

## 8 게시글 삭제

작성자만 삭제 가능 테스트 후 구현

---

## 9 Validation

Validation 테스트 작성 후 구현

---

## 10 예외 처리

예외 응답 구조 테스트 작성