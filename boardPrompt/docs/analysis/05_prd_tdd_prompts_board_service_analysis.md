# 06_prd_tdd_prompts_board_service 코드 리뷰

## 1. 리뷰 대상
- 프롬프트 방식: 06_prd_tdd_prompts_board_service
- 리뷰 기준: 동일 기능에 대한 구현 결과 비교
- 리뷰 원칙: 실제 코드 기반, 추측 금지

## 2. 실험 맥락
### 실험 조건
- 같은 초기 저장소 상태
- 같은 AI 툴
- 같은 모델
- 시간 제한: 20~30분

### 실험 목적
동일한 기능을 서로 다른 프롬프트 전략으로 개발했을 때  
구조, 품질, 테스트, 유지보수성이 어떻게 달라지는지 비교하기 위함.

### 통제 환경
- H2 Database
- 단순 HTML/CSS/JS

## 3. 구현 요약
### 전체 인상
- `post/domain`, `post/application`, `post/presentation`, `global/exception`로 계층을 분리해 CRUD 흐름을 읽기 쉽게 만들었다.
- 서비스 단위 테스트와 `@WebMvcTest` 기반 검증/예외 테스트가 함께 존재해 테스트 우선 개발 흔적은 확인된다.
- 반면 `spring-boot-starter-security`를 추가했지만 보안 설정과 인증 흐름이 없어 기본 보안 정책이 실제 API 사용과 웹 테스트를 막고 있다.
- 요청 DTO, 응답 DTO, 서비스 결과 타입이 기능별로 나뉘어 있지만 필드 구성이 거의 같아 타입 수는 늘고 중복도 증가했다.
- 프론트는 4개 HTML 페이지로 CRUD 흐름을 모두 연결했지만 공통 스크립트 분리 없이 각 페이지에 유사한 로직을 반복 작성했다.

### 확인된 주요 구현 범위
- `PostController`에 게시글 등록, 목록 조회, 상세 조회, 수정, 삭제 API가 구현되어 있다.
- `Post`, `PostRepository`, `PostCreateService`, `PostReadService`, `PostUpdateService`, `PostDeleteService`로 백엔드 CRUD 흐름이 구성되어 있다.
- `PostCreateRequest`, `PostUpdateRequest`에 `@NotBlank` 검증이 적용되어 있다.
- `GlobalExceptionHandler`가 validation 실패와 게시글 없음 예외를 공통 `ErrorResponse`로 변환한다.
- `posts.html`, `post-detail.html`, `post-create.html`, `post-edit.html`가 정적 프론트 화면으로 존재한다.
- 테스트는 총 7개 파일, 13개 케이스가 존재한다.

## 4. 평가 기준별 리뷰

### 4.1 빌드 성공
- 빌드 가능 여부
- 실패 시 원인
- 관련 근거 파일 또는 설정

평가: 전체 테스트 기준 빌드는 실패한다.  
근거: 2026-03-11에 `boardPrompt`에서 `.\gradlew.bat test`를 실행한 결과 `13 tests completed, 6 failed`가 확인됐다. 실패한 테스트는 `PostControllerValidationTest`, `PostControllerErrorResponseTest`이며, 결과 리포트에는 기대 상태코드 400/404 대신 403/401이 기록됐다. `build.gradle`에는 `spring-boot-starter-security`가 포함되어 있지만 별도 보안 설정 파일은 확인되지 않았다.  
리스크: CI 기준으로는 현재 상태를 통과하지 못한다. 기능 테스트보다 먼저 보안 기본 설정이 개입해 이후 수정 비용이 커질 수 있다.

### 4.2 실행 가능
- 애플리케이션 실행 가능 여부
- 실행에 필요한 조건
- 실행 막는 요소

평가: 애플리케이션 컨텍스트는 실행 가능하지만, 요구된 브라우저 기반 CRUD 흐름은 추가 보안 설정 없이는 바로 실행하기 어렵다.  
근거: `BoardPromptApplicationTests.contextLoads` 단독 실행은 성공했다. `build.gradle`에 H2와 Spring Web/JPA 의존성이 있고, 정적 HTML 4개 파일도 존재한다. 그러나 `post-create.html`, `posts.html`, `post-detail.html`, `post-edit.html`의 `fetch` 호출에는 인증 정보가 없고, 실제 테스트 결과는 GET 401, POST/PUT 403으로 기록됐다.  
리스크: 화면은 열리더라도 API 연동이 기본 보안에 막혀 사용자 시나리오가 끊긴다. 실행 조건이 코드에 명시되지 않아 재현성이 낮다.

### 4.3 테스트 개수
- 테스트 파일 수
- 테스트 케이스 수
- 테스트 범위

평가: 테스트 수는 비교 실험용으로는 충분한 편이지만 범위는 일부 계층에 치우쳐 있다.  
근거: 테스트 파일은 `BoardPromptApplicationTests`, `PostCreateServiceTest`, `PostReadServiceTest`, `PostUpdateServiceTest`, `PostDeleteServiceTest`, `PostControllerValidationTest`, `PostControllerErrorResponseTest`로 총 7개다. 테스트 케이스는 총 13개다. 범위는 서비스 단위 CRUD, 조회 실패, 컨트롤러 validation 실패, 공통 에러 응답, 애플리케이션 컨텍스트 로딩까지 포함한다.  
리스크: 저장소 연동, 실제 HTTP 성공 흐름, 프론트 동작, 보안 동작을 검증하는 테스트는 없다.

### 4.4 테스트 품질
- 정상 흐름만 검증하는지
- 실패/예외/경계값을 다루는지
- 테스트가 구조를 보호하는 수준인지

평가: 정상 흐름과 일부 실패 흐름을 함께 다루지만 구조 보호 수준은 제한적이다.  
근거: 서비스 테스트는 등록, 조회, 수정, 삭제의 정상 흐름을 검증하고, `PostReadServiceTest`는 없는 ID 조회 예외도 검증한다. `PostControllerValidationTest`는 입력 누락 4건을 검증하고, `PostControllerErrorResponseTest`는 404/400 공통 응답 구조를 검증한다. 반면 수정/삭제의 not found, 컨트롤러 성공 응답, 보안 요구사항, 저장소 동작, 프론트 흐름은 보호하지 못한다. 실제로 현재 테스트는 보안 기본 설정을 반영하지 못해 6건이 실패한다.  
리스크: 테스트가 존재해도 실제 런타임 제약을 막지 못한다. 리팩터링 안전망으로는 아직 약하다.

### 4.5 패키지 구조
- 계층 분리 상태
- 역할 분리 명확성
- 파일 배치 일관성
- 확장 가능성

평가: 계층 분리는 명확하지만 확장 시 타입 중복과 얇은 도메인 구조가 부담이 될 수 있다.  
근거: `post/domain`, `post/application`, `post/presentation`, `global/exception` 구성이 일관적이다. 컨트롤러는 서비스 호출과 DTO 변환만 담당하고, 서비스는 저장소 접근과 예외 발생을 담당한다. 다만 `Post` 엔티티 자체의 행위는 `update` 한 개뿐이고, `PostCreateResult`, `PostReadResult`, `PostUpdateResult`, `PostCreateResponse`, `PostReadResponse`, `PostUpdateResponse`가 모두 같은 필드 구성을 반복한다.  
리스크: 기능이 늘면 DTO와 결과 타입이 빠르게 늘어난다. 도메인 규칙이 서비스와 프론트로 흩어질 가능성이 크다.

### 4.6 권한 처리
- 인증/인가 관련 처리 존재 여부
- 보호 범위
- 우회 가능성
- 하드코딩 여부

평가: 명시적 권한 처리는 구현되지 않았고, 기본 보안 설정만 남아 있어 실사용 관점에서는 미완성 상태다.  
근거: `build.gradle`에 `spring-boot-starter-security`가 포함되어 있다. 그러나 `src/main/java` 아래에 인증, 인가, `SecurityFilterChain` 설정 파일은 확인되지 않았다. 정적 프론트는 모든 API를 인증 헤더 없이 호출한다. 테스트 리포트에는 GET 401, POST/PUT 403이 기록돼 기본 보안이 실제 접근을 막고 있음을 확인했다.  
리스크: 권한 정책이 의도된 설계인지 판단하기 어렵다. 현재 상태에서는 누구도 정상적으로 CRUD를 수행하지 못할 수 있다.

### 4.7 예외 처리
- 예외 처리 방식
- 공통 예외 처리 존재 여부
- 사용자 응답 일관성
- 누락된 영역

평가: 기본적인 공통 예외 포맷은 갖췄지만 처리 범위는 좁다.  
근거: `GlobalExceptionHandler`는 `MethodArgumentNotValidException`과 `PostNotFoundException`을 `ErrorResponse`로 변환한다. `ErrorResponse`에는 `status`, `error`, `message`, `path`, `errors` 필드가 있어 응답 형식은 일관적이다. 프론트도 이 구조를 읽어 필드 오류 메시지를 표시하도록 작성되어 있다. 다만 인증/인가 오류, 예상하지 못한 런타임 예외, JSON 파싱 실패 등에 대한 공통 처리 코드는 없다.  
리스크: 현재 가장 자주 발생하는 보안 오류는 공통 포맷으로 통제되지 않는다. 예외 종류가 늘면 클라이언트 분기 코드도 함께 늘어날 수 있다.

### 4.8 코드 중복
- 반복되는 로직 존재 여부
- 중복된 DTO/검증/응답/쿼리/핸들러 여부
- 리팩토링 필요 수준

평가: 중복은 분명히 존재하며, 현재는 감당 가능하지만 확장 전 정리가 필요하다.  
근거: `PostCreateRequest`와 `PostUpdateRequest`는 동일한 필드와 validation 규칙을 가진다. `PostCreateResult`, `PostReadResult`, `PostUpdateResult`도 동일 구조다. `PostCreateResponse`, `PostReadResponse`, `PostUpdateResponse` 역시 모두 `id`, `title`, `content`만 가진다. 프론트 4개 HTML에도 메시지 처리, `fetch` 응답 파싱, 에러 분기 로직이 반복된다.  
리스크: 요구사항이 늘수록 수정 지점이 여러 파일로 분산된다. 테스트가 일부만 보호하는 상태라 중복 수정 중 회귀가 발생하기 쉽다.

## 5. 구조적 장점
- `post/domain`, `post/application`, `post/presentation`, `global/exception`로 계층을 나눠 책임 경계가 비교적 분명하다.
- CRUD API와 정적 프론트 화면 4종이 모두 존재해 PRD의 기본 범위는 빠짐없이 구현되어 있다.
- validation과 not found를 `GlobalExceptionHandler`의 공통 `ErrorResponse`로 맞춰 응답 포맷 일관성을 확보했다.
- 서비스 테스트와 WebMvc 테스트를 분리해 단위 검증과 웹 계층 검증을 구분하려는 시도가 보인다.
- `PostReadService`에 `@Transactional(readOnly = true)`를 적용해 조회 책임을 쓰기 로직과 분리했다.

## 6. 구조적 한계
- `spring-boot-starter-security`만 추가되고 실제 보안 설계가 빠져 있어, 테스트와 실행 흐름 모두 기본 보안 정책에 막힌다.
- 전체 테스트 실행이 실패하므로 TDD 결과물이라고 보기에는 마지막 통합 검증이 마무리되지 않았다.
- 테스트의 다수가 Mockito 기반 단위 테스트라 JPA 매핑, H2 연동, 실제 HTTP 성공 흐름을 검증하지 못한다.
- 예외 테스트는 조회 not found와 validation에 집중되어 있고 수정/삭제 not found, 보안 오류, 기타 런타임 예외는 비어 있다.
- 요청 DTO, 서비스 결과, 응답 DTO가 기능별로 세분화됐지만 실질적으로 같은 필드를 반복해 구조 복잡도만 높인다.
- 프론트 4개 HTML에 메시지 처리와 응답 파싱 로직이 반복되어 수정 포인트가 많다.
- `Post` 엔티티가 단순 데이터 보관 수준에 머물러 있어 규칙이 늘면 서비스 계층 비대화가 발생하기 쉽다.

## 7. 유지보수성 관점 총평
아래 4개 항목으로 정리한다.

- 변경 용이성: 계층 분리는 명확해서 작은 기능 수정은 어렵지 않다. 다만 동일한 필드를 가진 DTO와 프론트 스크립트가 여러 군데에 흩어져 있어 변경 범위가 넓어지기 쉽다.
- 기능 확장 용이성: CRUD 한정 구조로는 확장이 가능하다. 하지만 권한, 검색, 페이징, 공통 프론트 유틸 같은 횡단 관심사가 들어오면 현재 구조는 빠르게 중복이 늘어날 가능성이 높다.
- 버그 발생 가능성: 현재도 보안 기본 설정과 테스트 기대값이 어긋나 있다. 이 상태는 새 기능보다 기존 흐름을 깨뜨릴 가능성을 더 크게 만든다.
- 리팩토링 필요도: 중간 이상이다. 특히 보안 설정 정리, 중복 DTO 축소, 프론트 공통 로직 분리, 통합 테스트 보강이 우선순위다.

## 8. 비교 실험용 요약
아래 표를 작성한다.

| 항목 | 평가 |
|-----|-----|
| 빌드 | 미흡 |
| 실행 | 보통 |
| 테스트 수 | 보통 |
| 테스트 품질 | 보통 |
| 구조 | 보통 |
| 권한 처리 | 미흡 |
| 예외 처리 | 보통 |
| 코드 중복 | 보통 |

평가값 작성 규칙
- 우수 / 보통 / 미흡 / 확인 불가
중 하나만 사용한다.

## 9. 한 줄 결론
이 프롬프트 방식의 결과물은 계층 분리와 테스트 흔적은 남겼지만, 보안 통합과 중복 정리가 끝나지 않아 완성 직전 단계에 머문 CRUD 구현이다.
