# 게시글 CRUD 서비스 - PRD 기반 프롬프트 실험

## 목적

PRD 기반 개발 방식이 코드 품질에 어떤 영향을 주는지 비교한다.

이 실험에서는 기능을 직접 짧게 지시하지 않고,  
**PRD 문서를 먼저 읽고 요구사항을 해석한 뒤 구현**하도록 한다.

이 문서는 **공통 프롬프트를 먼저 입력한 뒤**, `PRD.md`를 함께 제공하고 사용한다.

---

# 규칙

- `PRD.md`를 읽고 개발한다
- 기능 구현은 반드시 **PRD 기준**으로 수행한다
- Before writing code explain reasoning step by step
- 작업 범위를 벗어난 수정은 하지 않는다
- 각 단계가 끝나면 API 경로와 request / response JSON 예시를 정리한다
- 프론트는 HTML / CSS / JavaScript로 구현한다

출력 형식

1 Plan  
2 Reasoning  
3 Implementation  
4 Files created  
5 Verification

---

# 실행 방식

각 기능은 다음 순서로 진행한다.

1. PRD 분석 또는 기능 요구사항 확인
2. 백엔드 프롬프트 입력
3. 프론트 프롬프트 입력
4. 결과 비교

---

# 1. PRD 분석

## 1-1 기능 요구사항

`PRD.md`를 읽고 전체 시스템을 분석한다.

분석 대상

- 시스템 아키텍처
- 패키지 구조
- 엔티티 모델
- API 설계

Scope

- 코드 작성 금지
- 설계와 분석만 수행

---

## 1-2 백엔드 프롬프트

```text
PRD.md를 읽고 다음을 설계하라.

- 시스템 아키텍처
- 패키지 구조
- 엔티티 모델
- API 설계

Scope
코드 작성 금지

출력 시 다음 내용을 포함해라.
- post / global 기준 패키지 구조
- Post 중심 엔티티 설계
- 예외 처리 방향
```

---

## 1-3 프론트 프롬프트

```text
PRD.md를 읽고 프론트 구조를 설계해줘.

다음을 포함해줘.
- 필요한 화면 목록
- 화면 간 이동 흐름
- 각 화면에서 호출할 API

Scope
코드 작성 금지
HTML, CSS, JavaScript 기준으로 분석만 해줘.
```

---

## 1-4 결과 비교 포인트

- PRD 해석이 정확한가
- 패키지 구조가 공통 프롬프트와 잘 맞는가
- 엔티티와 API 설계가 자연스러운가
- 프론트 화면 구조와 API 연결이 설계 단계에서 정리되는가

---

# 2. 게시글 등록 구현

## 2-1 기능 요구사항

PRD 기준 게시글 등록 구현

Scope

등록 기능만 구현

---

## 2-2 백엔드 프롬프트

```text
PRD.md 기준으로 게시글 등록 기능을 구현해줘.

조건
- 등록 기능만 구현

작업이 끝나면 `D:\prompt-compare\prompt-Compare\boardPrompt\docs\05_prd_based_prompts_board_service.md에 `D:\prompt-compare\prompt-Compare\boardPrompt\docs\05_prd_based_prompts_board_service.md에 `D:\prompt-compare\prompt-Compare\boardPrompt\docs\results\ver01_bad.md` 다음을 정리해줘.
- API endpoint
- request JSON
- response JSON
- 에러 응답 예시
```

---

## 2-3 프론트 프롬프트

```text
PRD.md 기준으로 게시글 등록 화면을 구현해줘.

기술
- HTML
- CSS
- JavaScript

요구사항
- title
- content 입력 폼
- 게시글 등록 API 호출 연결
- 성공 / 실패 메시지 표시
```

---

## 2-4 결과 비교 포인트

- PRD 요구사항이 정확히 반영되는가
- API 형식이 일관적인가
- 프론트 폼과 API 연결이 되는가

---

# 3. 게시글 조회 구현

## 3-1 기능 요구사항

PRD 기준 게시글 조회 구현

Scope

조회 기능만 구현

---

## 3-2 백엔드 프롬프트

```text
PRD.md 기준으로 게시글 조회 기능을 구현해줘.

포함 범위
- 목록 조회
- 상세 조회

작업이 끝나면 `D:\prompt-compare\prompt-Compare\boardPrompt\docs\05_prd_based_prompts_board_service.md에 `D:\prompt-compare\prompt-Compare\boardPrompt\docs\results\ver01_bad.md` 다음을 정리해줘.
- 목록 조회 endpoint / response JSON
- 상세 조회 endpoint / response JSON
```

---

## 3-3 프론트 프롬프트

```text
PRD.md 기준으로 게시글 조회 화면을 구현해줘.

기술
- HTML
- CSS
- JavaScript

요구사항
- 게시글 목록 화면
- 게시글 상세 화면
- 목록 조회 API 호출
- 상세 조회 API 호출
```

---

## 3-4 결과 비교 포인트

- 목록 / 상세 API가 분리되는가
- 응답 구조가 깔끔한가
- 프론트 목록과 상세 화면 연결이 되는가

---

# 4. 게시글 수정 구현

## 4-1 기능 요구사항

PRD 기준 수정

---

## 4-2 백엔드 프롬프트

```text
PRD.md 기준으로 게시글 수정 기능을 구현해줘.

작업이 끝나면 `D:\prompt-compare\prompt-Compare\boardPrompt\docs\05_prd_based_prompts_board_service.md에 `D:\prompt-compare\prompt-Compare\boardPrompt\docs\results\ver01_bad.md` 다음을 정리해줘.
- API endpoint
- request JSON
- response JSON
- 에러 응답 예시
```

---

## 4-3 프론트 프롬프트

```text
PRD.md 기준으로 게시글 수정 화면을 구현해줘.

기술
- HTML
- CSS
- JavaScript

요구사항
- 기존 게시글 데이터 표시
- 수정 API 호출 연결
```

---

## 4-4 결과 비교 포인트

- 수정 권한 검증이 되는가
- 프론트 수정 흐름이 구현되는가

---

# 5. 게시글 삭제 구현

## 5-1 기능 요구사항

PRD 기준 삭제

---

## 5-2 백엔드 프롬프트

```text
PRD.md 기준으로 게시글 삭제 기능을 구현해줘.

작업이 끝나면 `D:\prompt-compare\prompt-Compare\boardPrompt\docs\05_prd_based_prompts_board_service.md에 `D:\prompt-compare\prompt-Compare\boardPrompt\docs\results\ver01_bad.md` 다음을 정리해줘.
- API endpoint
- response JSON
- 에러 응답 예시
```

---

## 5-3 프론트 프롬프트

```text
PRD.md 기준으로 게시글 삭제 기능을 구현해줘.

기술
- HTML
- CSS
- JavaScript

요구사항
- 삭제 버튼 제공
- 삭제 API 호출 연결
```

---

## 5-4 결과 비교 포인트

- API 응답이 명확한가
- 프론트 삭제 동작이 되는가
- 삭제 후 화면 흐름이 자연스러운가

---

# 6. Validation 적용

## 6-1 기능 요구사항

PRD 기준 Validation 적용

---

## 6-2 백엔드 프롬프트

```text
PRD.md 기준으로 Validation을 적용해줘.

포함 대상
- 게시글 등록
- 게시글 수정

작업이 끝나면 `D:\prompt-compare\prompt-Compare\boardPrompt\docs\05_prd_based_prompts_board_service.md에 `D:\prompt-compare\prompt-Compare\boardPrompt\docs\results\ver01_bad.md` 다음을 정리해줘.
- 어떤 필드에 어떤 검증을 적용했는지
- validation 실패 응답 예시
```

---

## 6-3 프론트 프롬프트

```text
PRD.md 기준으로 프론트 입력값 validation을 적용해줘.

기술
- HTML
- CSS
- JavaScript

요구사항
- 게시글 등록 / 수정 입력 검증
- 검증 실패 메시지 표시
```

---

## 6-4 결과 비교 포인트

- PRD의 검증 요구사항이 반영되는가
- 서버 검증과 프론트 검증이 일관적인가
- 에러 메시지 구조가 정리되는가

---

# 7. 예외 처리 구현

## 7-1 기능 요구사항

PRD 기준 공통 예외 처리

---

## 7-2 백엔드 프롬프트

```text
PRD.md 기준으로 공통 예외 처리를 구현해줘.

대상 예시
- 게시글 없음
- validation 실패

작업이 끝나면 `D:\prompt-compare\prompt-Compare\boardPrompt\docs\05_prd_based_prompts_board_service.md에 `D:\prompt-compare\prompt-Compare\boardPrompt\docs\results\ver01_bad.md` 공통 에러 응답 구조와 예외별 응답 예시를 정리해줘.
```

---

## 7-3 프론트 프롬프트

```text
PRD.md 기준으로 API 에러를 화면에서 처리해줘.

기술
- HTML
- CSS
- JavaScript

요구사항
- 게시글 오류 메시지 표시
- validation 오류 메시지 표시
```

---

## 7-4 결과 비교 포인트

- 공통 예외 처리 구조가 생기는가
- 에러 응답이 일관적인가
- 프론트에서 서버 에러를 적절히 표시하는가

---

# 정리

이 문서는 **PRD 기반 개발 실험용 프롬프트 문서**이다.

특징은 다음과 같다.

- 구현 전에 PRD 분석 단계가 있다
- 요구사항 해석이 먼저 들어간다
- 기능 구현은 PRD 기준으로 제한된다
- 백엔드와 프론트를 함께 구현 가능하다
- 구조화 프롬프트보다 문서 해석 능력을 더 많이 요구한다

이 실험 결과는 이후의 **PRD + TDD 기반 프롬프트 실험**과 비교하기 위한 기준점으로 사용한다.
