## 게시글 CRUD 서비스 PRD 기반 요구사항 분석 (Backend & Frontend, TDD 관점)

### 1. 백엔드 기능 요구사항 분석

#### 1.1 주요 도메인 및 엔드포인트

- **도메인 개요**
  - 게시판 서비스의 핵심 도메인은 “게시글(Post)”이다.
  - 게시글은 최소한 다음 필드를 가진다.
    - `id`: 고유 식별자
    - `title`: 게시글 제목 (필수)
    - `content`: 게시글 내용 (필수)

- **엔드포인트 요약 (PRD 기준)**
  - **게시글 등록**
    - Endpoint: `POST /api/posts`
    - Request Fields: `title`, `content`
  - **게시글 목록 조회**
    - Endpoint: `GET /api/posts`
  - **게시글 상세 조회**
    - Endpoint: `GET /api/posts/{id}`
  - **게시글 수정**
    - Endpoint: `PUT /api/posts/{id}`
    - Request Fields: `title`, `content`
  - **게시글 삭제**
    - Endpoint: `DELETE /api/posts/{id}`

#### 1.2 검증 및 비즈니스 규칙

- **입력값 검증 (Validation)**
  - 게시글 등록/수정 시 다음 규칙을 반드시 적용해야 한다.
    - `title` 필수
    - `content` 필수
  - 위 필드가 비어 있거나 누락된 경우, 적절한 에러 응답(HTTP 상태 코드 및 에러 메시지)을 반환해야 한다.

- **비즈니스 규칙 관점**
  - PRD 상에서 복잡한 비즈니스 로직은 정의되어 있지 않고, **기본적인 CRUD와 필수값 검증**이 핵심이다.
  - 존재하지 않는 게시글 `id`에 대한 조회/수정/삭제 요청 시, 일관된 에러 응답을 제공해야 한다.

#### 1.3 아키텍처 및 구현 방향

- **기술 스택**
  - Java 17
  - Spring Boot 3.x
  - Spring Data JPA
  - H2 Database

- **레이어드 아키텍처**
  - `presentation` (Controller, Request/Response DTO)
  - `application` (Service/UseCase – 트랜잭션, 도메인 조합)
  - `domain` (Entity, 도메인 규칙)
  - `infrastructure` (JPA Repository, DB 접근)

- **구현 방향 요약**
  - 도메인 계층에 `Post` 엔티티를 정의하고, 필수 필드에 대한 기본 제약 조건을 설정한다.
  - 애플리케이션 계층에서 등록/수정/삭제/조회 유스케이스를 명확히 구분하고, 검증 및 에러 처리를 책임진다.
  - 프레젠테이션 계층에서는 HTTP 요청/응답을 DTO로 변환하고, 컨트롤러는 가능한 한 얇게 유지한다.

---

### 2. 프론트엔드 기능 요구사항 분석 (HTML / CSS / JavaScript)

#### 2.1 필요한 화면 목록 (PRD 기반)

- **게시글 목록 화면**
  - 모든 게시글의 제목(및 필요 시 일부 내용 요약)을 리스트 형태로 보여준다.
  - 각 게시글을 선택하면 상세 화면으로 이동할 수 있다.
  - “새 글 작성” 버튼을 제공하여 작성 화면으로 이동한다.

- **게시글 상세 화면**
  - 특정 게시글의 제목과 내용을 보여준다.
  - “수정” 버튼을 제공하여 수정 화면으로 이동한다.
  - “삭제” 버튼을 제공하여 삭제를 실행한다.
  - “목록으로” 버튼/링크로 목록 화면으로 돌아갈 수 있다.

- **게시글 작성 화면**
  - 제목 입력 필드 (`title`)
  - 내용 입력 필드 (`content`)
  - “저장” 버튼으로 등록 API를 호출한다.
  - “취소/목록” 버튼으로 목록 화면으로 이동한다.

- **게시글 수정 화면**
  - 기존 게시글의 제목과 내용을 입력 필드에 미리 채워 보여준다.
  - “수정 저장” 버튼으로 수정 API를 호출한다.
  - “취소/목록” 버튼으로 목록 화면으로 이동한다.

#### 2.2 화면별 API 연동 흐름

- **게시글 목록 화면**
  - 진입 시 `GET /api/posts` 호출.
  - 응답으로 받은 게시글 배열을 HTML 리스트로 렌더링.
  - 각 리스트 아이템 클릭 시, 해당 게시글의 `id`를 이용해 상세 화면으로 이동 (`GET /api/posts/{id}` 연계).
  - 상단 또는 하단에 “새 글 작성” 버튼을 두어 작성 화면으로 이동.

- **게시글 상세 화면**
  - URL 또는 상태 관리 객체에서 게시글 `id`를 획득.
  - 진입 시 `GET /api/posts/{id}` 호출 후, 제목/내용을 화면에 출력.
  - “수정” 버튼 클릭 시 수정 화면으로 이동 (`id` 전달).
  - “삭제” 버튼 클릭 시 `DELETE /api/posts/{id}` 호출.
  - 삭제 성공 시 목록 화면으로 이동 후, `GET /api/posts`로 리스트 갱신.
  - 존재하지 않는 `id` 등 에러 응답 시 에러 메시지를 표시하거나 목록으로 리다이렉트.

- **게시글 작성 화면**
  - 사용자가 제목/내용을 입력한다.
  - “저장” 버튼 클릭 시, `POST /api/posts`로 JSON 요청을 전송.
  - 필수 필드 누락 등 검증 에러 응답 시, 각 필드 또는 전체 폼 영역에 에러 메시지를 표시.
  - 등록 성공 시:
    - 새 게시글 상세 화면으로 이동하거나,
    - 목록 화면으로 돌아가 `GET /api/posts`를 다시 호출해 리스트를 갱신한다.

- **게시글 수정 화면**
  - 진입 시 `GET /api/posts/{id}` 호출로 기존 값을 조회하여 입력 필드에 채운다.
  - “수정 저장” 버튼 클릭 시 `PUT /api/posts/{id}` 호출.
  - 검증 에러 발생 시 작성 화면과 동일한 방식으로 에러 메시지를 표시.
  - 수정 성공 시 상세 화면 또는 목록 화면으로 이동.

---

### 3. TDD 및 Bottom-Up 관점에서의 구현·연동 전략

#### 3.1 백엔드 TDD 흐름 (요약)

- **Red → Green → Refactor** 순서로 기능별 구현.
- **Bottom-Up** 접근:
  1. 도메인/애플리케이션 계층의 작은 단위(서비스, 도메인 로직) 테스트부터 작성.
  2. 단위 테스트를 통과시킨 후, 컨트롤러/통합 테스트로 HTTP 레벨 동작을 검증.
  3. 기능별로 “실패하는 테스트 작성 → 테스트 실행 → 최소 코드 구현 → 리팩터” 순서를 유지.

- **우선순위가 높은 기능 (PRD 기준)**
  - 게시글 등록 (`POST /api/posts`)
  - 게시글 수정 (`PUT /api/posts/{id}`)
  - 게시글 삭제 (`DELETE /api/posts/{id}`)
  - 이후 상세 조회, 목록 조회 순으로 확장.

#### 3.2 프론트엔드 연동 순서 (백엔드 TDD에 맞춘 흐름)

- **1단계: 정적 UI 스케치**
  - 모든 화면(목록/상세/작성/수정)을 HTML/CSS로 먼저 정적 구성.
  - JavaScript는 최소한의 네비게이션/이벤트 핸들러 구조만 잡아둔다.

- **2단계: 게시글 등록(Create) API가 TDD로 안정화된 시점**
  - 작성 화면에 `POST /api/posts` 호출 로직을 추가.
  - 백엔드의 검증 실패 응답(필수 필드 누락)을 화면에서 시각적으로 표현.
  - “테스트 케이스에서 정의된 예외 상황”이 프론트에서도 그대로 재현되는지 확인.

- **3단계: 게시글 수정(Update) API가 TDD로 안정화된 시점**
  - 수정 화면에서 `GET /api/posts/{id}`로 초기 데이터 로딩.
  - `PUT /api/posts/{id}`로 수정 요청을 보내고, 검증 실패/없는 id 등의 에러를 처리.

- **4단계: 게시글 삭제(Delete) API 연동**
  - 상세 화면에서 `DELETE /api/posts/{id}` 호출을 연결.
  - 삭제 성공 시 목록 화면으로 이동 후, `GET /api/posts`로 목록을 재조회.

- **5단계: 목록/상세 조회(Read) 연동 마무리**
  - 목록 화면에서 `GET /api/posts` 연동 및 정렬/표시 방식 정리.
  - 상세 화면에서 `GET /api/posts/{id}`를 통한 상세 데이터 표시, 에러 처리 보완.

- **6단계: 통합 플로우 검증**
  - 브라우저에서 실제 사용자 시나리오로 다음 흐름을 검증:
    - 목록 → 작성 → 등록 → 목록 갱신 → 상세 → 수정 → 삭제.
  - 백엔드 테스트에서 정의된 케이스(검증 실패, 존재하지 않는 id 등)가 UI에서 모두 적절히 표현되는지 확인.

---

### 4. 예상되는 위험 요소 (Backend & Frontend 공통)

- **검증/에러 응답 포맷 불일치**
  - 엔드포인트마다 에러 응답 구조가 다르면 프론트 처리 로직이 복잡해지고, TDD로 정의한 동작과 UI 표현이 어긋날 수 있다.
  - 초기에 공통 에러 응답 스펙을 정의하는 것이 중요하다.

- **TDD 절차 미준수**
  - 테스트 없이 바로 구현하면 PRD에서 요구한 동작(특히 예외 케이스)에 대한 보장이 약해진다.
  - 회귀 버그가 발생해도 감지하기 어렵다.

- **Bottom-Up과 실제 사용자 플로우 사이의 간극**
  - 백엔드 단위/통합 테스트는 통과하지만, 실제 프론트 연동 시 CORS, JSON 포맷, 상태 코드 처리, 네트워크 에러 처리 등에서 문제가 발생할 수 있다.
  - 이를 줄이기 위해 백엔드 TDD 진행 중간중간에 프론트와 간단한 연동 검증을 수행하는 것이 필요하다.

- **프론트–백엔드 계약(Contract) 변경 위험**
  - 개발 과정에서 API 스펙(필드명, URL, 상태 코드 등)이 PRD와 다르게 변경되면, 프론트 수정 비용이 커진다.
  - PRD를 기준으로 계약을 고정하고, 변경이 필요할 경우 명시적으로 합의·문서화해야 한다.

- **테스트 데이터 및 환경 격리 문제**
  - H2 인메모리 DB 사용 시 테스트 간 데이터 격리가 명확히 되지 않으면, 테스트 결과가 비결정적이 될 수 있다.
  - 프론트에서 수동/E2E 검증 시에도, 테스트 환경과 실제(또는 데모) 환경의 데이터 차이로 인한 혼란이 생길 수 있다.

이 문서는 게시글 CRUD 서비스 구현 시, **PRD 기반 요구사항**을 백엔드와 프론트 관점에서 정리하고,  

---

## 게시글 등록 기능 – TDD 구현 정리 (1차)

### 작성한 테스트 목록

- `PostCreateServiceTest.createPost_success_whenTitleAndContentProvided`
  - 내용: `title`과 `content`가 모두 존재할 때 게시글이 정상적으로 저장되고, 저장된 엔티티의 `id`, `title`, `content`를 포함한 결과가 반환되는지 검증.

### API Endpoint

- **Endpoint**
  - `POST /api/posts`

### Request JSON

```json
{
  "title": "게시글 제목",
  "content": "게시글 내용"
}
```

### Response JSON

성공(`201 Created`) 시:

```json
{
  "id": 1,
  "title": "게시글 제목",
  "content": "게시글 내용"
}
```

---

## 게시글 조회 기능 – TDD 구현 정리 (목록 / 상세)

### 작성한 테스트 목록

- `PostReadServiceTest.getAll_returnsAllPosts`
  - 내용: 리포지토리에서 반환된 모든 게시글이 `PostReadResult` 리스트로 매핑되어 반환되는지 검증.
- `PostReadServiceTest.getById_returnsPost`
  - 내용: 특정 ID로 조회 시 해당 게시글의 `id`, `title`, `content`가 포함된 결과가 반환되는지 검증.
- `PostReadServiceTest.getById_throwsWhenNotFound`
  - 내용: 존재하지 않는 ID로 조회할 경우 `PostNotFoundException`이 발생하는지 검증.

### 목록 조회

- **Endpoint**
  - `GET /api/posts`

- **Response JSON (200 OK)**

```json
[
  {
    "id": 1,
    "title": "첫 번째 게시글",
    "content": "첫 번째 게시글 내용"
  },
  {
    "id": 2,
    "title": "두 번째 게시글",
    "content": "두 번째 게시글 내용"
  }
]
```

### 상세 조회

- **Endpoint**
  - `GET /api/posts/{id}`

- **Response JSON (200 OK)**

```json
{
  "id": 1,
  "title": "첫 번째 게시글",
  "content": "첫 번째 게시글 내용"
}
```

---

## 게시글 수정 기능 – TDD 구현 정리

### 작성한 테스트 목록

- `PostUpdateServiceTest.update_success_whenPostExists`
  - 내용: 존재하는 게시글의 제목과 내용을 수정하면 수정된 값이 반영된 결과가 반환되는지 검증.

### API Endpoint

- **Endpoint**
  - `PUT /api/posts/{id}`

### Request JSON

```json
{
  "title": "수정된 제목",
  "content": "수정된 내용"
}
```

### Response JSON (성공 – 200 OK)

```json
{
  "id": 1,
  "title": "수정된 제목",
  "content": "수정된 내용"
}
```

### 에러 응답 예시

- **존재하지 않는 게시글 ID로 수정 요청한 경우 (예: 404 Not Found 가정)**

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "게시글을 찾을 수 없습니다. id=999",
  "path": "/api/posts/999"
}
```

---

## 게시글 삭제 기능 – TDD 구현 정리

### 작성한 테스트 목록

- `PostDeleteServiceTest.delete_success_whenPostExists`
  - 내용: 존재하는 게시글 삭제 시, 리포지토리에서 `findById`와 `delete`가 호출되는지 검증.

### API Endpoint

- **Endpoint**
  - `DELETE /api/posts/{id}`

### Response JSON (성공 – 204 No Content)

```json
null
```

> HTTP 204 응답으로 바디는 없음을 의미.

### 에러 응답 예시

- **존재하지 않는 게시글 ID로 삭제 요청한 경우 (예: 404 Not Found 가정)**

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "게시글을 찾을 수 없습니다. id=999",
  "path": "/api/posts/999"
}
```
