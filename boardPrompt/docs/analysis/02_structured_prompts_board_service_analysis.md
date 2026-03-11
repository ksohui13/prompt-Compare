# 02_structured_prompts_board_service 코드 리뷰

## 1. 리뷰 대상
- 프롬프트 방식: 02_structured_prompts_board_service
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
- 게시글 기능을 `domain`, `application`, `infrastructure`, `presentation`으로 나눠 정리한 구현이다.
- 백엔드는 생성, 목록, 상세, 수정, 삭제를 서비스별로 분리했다.
- 테스트는 도메인, 서비스, 컨트롤러까지 넓게 작성됐지만 저장소 통합과 실제 보안 동작은 다루지 않는다.
- 프론트는 정적 HTML 4개로 흐름을 구성했지만, 보안 설정과 화면 호출 경로가 일치하지 않는다.
- 설정 파일이 매우 얇아 실행은 단순하지만, 실제 동작은 Spring 기본 보안 설정 영향이 크다.

### 확인된 주요 구현 범위
- `PostController`에 게시글 생성, 목록 조회, 상세 조회, 수정, 삭제 API 구현
- `Post` 엔티티와 `PostRepository` 기반 JPA 저장소 구현
- `CreatePostRequest`, `UpdatePostRequest`에 Bean Validation 적용
- `GlobalExceptionHandler`로 validation 예외와 `ForbiddenException` 응답 처리
- `create-post.html`, `list-posts.html`, `post-detail.html`, `post-edit.html` 정적 화면 구현
- `authorId`와 `X-Author-Id`를 이용한 수정·삭제 작성자 확인 로직 구현
- 도메인 테스트, 서비스 단위 테스트, `PostController` WebMvc 테스트 작성

## 4. 평가 기준별 리뷰

### 4.1 빌드 성공
- 빌드 가능 여부
- 실패 시 원인
- 관련 근거 파일 또는 설정

평가: 성공
근거: `build.gradle`에 Spring Boot Web, JPA, Security, Validation, H2 의존성이 선언돼 있다. `gradlew.bat build`를 실제 실행했을 때 성공했고 `build/libs/boardPrompt-0.0.1-SNAPSHOT.jar`가 생성됐다.
리스크: 최초 환경에서는 Gradle Wrapper와 의존성 다운로드가 필요하다. `application.properties`에 환경별 설정이 거의 없어 빌드 후 동작은 프레임워크 기본값에 의존한다.

### 4.2 실행 가능
- 애플리케이션 실행 가능 여부
- 실행에 필요한 조건
- 실행 막는 요소

평가: 서버 기동은 가능하지만 사용자 흐름은 부분 제한됨
근거: `BoardPromptApplication` 메인 클래스가 있고, `gradlew.bat bootRun --args="--server.port=0"` 실행 시 Tomcat 시작 로그와 H2 메모리 DB 초기화 로그가 확인됐다. 다만 `SecurityConfig`는 `/api/posts`만 `permitAll()`로 열고 나머지는 `authenticated()`로 묶는다.
리스크: 정적 HTML 파일과 `/api/posts/{id}` 경로는 코드상 인증이 필요하다. 프론트 화면은 로그인 처리 없이 직접 `fetch`를 호출하므로 상세 조회, 수정, 삭제 흐름은 기본 보안 설정에 막힐 수 있다.

### 4.3 테스트 개수
- 테스트 파일 수
- 테스트 케이스 수
- 테스트 범위

평가: 테스트 파일 8개, 테스트 케이스 23개
근거: `BoardPromptApplicationTests`, `PostTest`, 서비스 테스트 5개, `PostControllerTest`가 있다. `@Test`는 총 23개다. 범위는 컨텍스트 로드, 도메인 메서드, 서비스 CRUD 분기, 컨트롤러 HTTP 응답까지다.
리스크: 저장소 통합 테스트, 실제 H2 매핑 검증, 브라우저 수준 테스트는 없다.

### 4.4 테스트 품질
- 정상 흐름만 검증하는지
- 실패/예외/경계값을 다루는지
- 테스트가 구조를 보호하는 수준인지

평가: 보통
근거: `UpdatePostServiceTest`와 `DeletePostServiceTest`는 성공, 미존재, 권한 실패를 검증한다. `PostControllerTest`는 생성 validation 실패, 조회 id 경계값, 수정·삭제 헤더 누락을 확인한다. 하지만 테스트는 모두 Mock 기반이고 `@WithMockUser`를 사용해 `SecurityConfig`의 실제 접근 제어 문제를 드러내지 않는다.
리스크: 현재 테스트만으로는 정적 화면 접근 제한, `/api/posts/{id}` 인증 요구, JPA 매핑, H2 연동 문제를 보호하기 어렵다.

### 4.5 패키지 구조
- 계층 분리 상태
- 역할 분리 명확성
- 파일 배치 일관성
- 확장 가능성

평가: 보통
근거: `post.domain`, `post.application`, `post.infrastructure`, `post.presentation`으로 역할이 구분돼 있다. `CreatePostService`, `GetPostListService`, `GetPostService`, `UpdatePostService`, `DeletePostService`로 유스케이스가 나뉜다. 반면 프론트는 화면별 HTML 파일에 CSS와 JS가 모두 인라인으로 들어 있다.
리스크: 백엔드 구조는 읽기 쉽지만, 기능이 늘어나면 유사 DTO와 서비스 수가 빠르게 늘 수 있다. 프론트는 공통 스크립트나 스타일 분리가 없어 화면 수 증가에 취약하다.

### 4.6 권한 처리
- 인증/인가 관련 처리 존재 여부
- 보호 범위
- 우회 가능성
- 하드코딩 여부

평가: 미흡
근거: 수정·삭제 권한은 `UpdatePostService`, `DeletePostService`에서 `authorId`와 요청 헤더 `X-Author-Id`를 비교해 판단한다. 프론트는 `localStorage.currentAuthorId` 값을 읽어 헤더로 보낸다. 생성 시 작성자 식별값도 `CreatePostRequest.authorId`로 클라이언트가 직접 넣는다.
리스크: 실제 사용자 인증 모델이 없다. 헤더와 `localStorage` 값은 쉽게 바꿀 수 있다. `SecurityConfig`의 인증 요구 범위도 작성자 권한 로직과 별개라 정책이 이중화돼 있다.

### 4.7 예외 처리
- 예외 처리 방식
- 공통 예외 처리 존재 여부
- 사용자 응답 일관성
- 누락된 영역

평가: 보통
근거: `GlobalExceptionHandler`는 `MethodArgumentNotValidException`, `ConstraintViolationException`, `ForbiddenException`을 공통 처리한다. 그러나 `PostController`는 `X-Author-Id` 헤더 누락을 메서드 내부에서 직접 403 응답으로 반환한다. 조회 실패는 빈 404이고, 보안 필터에서 발생하는 인증 실패 응답은 공통 형식으로 통합되지 않는다.
리스크: 400, 403, 404, 인증 실패의 응답 형식이 서로 다르다. 예외 종류가 늘어날수록 컨트롤러 직접 처리와 전역 처리의 경계가 더 흐려질 수 있다.

### 4.8 코드 중복
- 반복되는 로직 존재 여부
- 중복된 DTO/검증/응답/쿼리/핸들러 여부
- 리팩토링 필요 수준

평가: 보통
근거: `CreatePostRequest`와 `UpdatePostRequest`는 필드와 validation 규칙이 거의 같다. `PostController`의 수정·삭제 메서드는 헤더 누락 403 응답 생성 코드가 반복된다. `create-post.html`과 `post-edit.html`은 입력 수집, 메시지 표시, validation 오류 렌더링이 유사하고, 네 개의 HTML 모두 인라인 스타일을 각각 가진다.
리스크: validation 규칙이나 에러 메시지 정책이 바뀌면 여러 파일을 함께 수정해야 한다. 프론트 화면 수가 늘수록 중복 부담이 더 커진다.

## 5. 구조적 장점
- 게시글 기능을 `domain`, `application`, `infrastructure`, `presentation`으로 분리해 책임 경계가 비교적 명확하다.
- CRUD를 유스케이스별 서비스 클래스로 나눠 서비스 메서드 책임이 짧다.
- `PostRepository.findAllByOrderByCreatedAtDesc()`로 최신순 정렬 책임을 저장소 계층에 두었다.
- validation 실패와 `ForbiddenException`을 `GlobalExceptionHandler`에서 공통 응답으로 정리한다.
- 서비스 테스트와 컨트롤러 테스트가 모두 있어 핵심 분기와 HTTP 상태 코드를 일부 보호한다.

## 6. 구조적 한계
- `SecurityConfig`가 `/api/posts`만 열어 두고 정적 화면과 `/api/posts/{id}`는 인증을 요구해, 단순 HTML/JS 흐름과 실제 보안 경로가 맞지 않는다.
- 작성자 식별은 서버 세션이나 로그인 정보가 아니라 요청 본문 `authorId`, 요청 헤더 `X-Author-Id`, `localStorage` 값에 의존한다.
- `Post.authorId` 컬럼은 `nullable = true`인데 API 요청 DTO는 `authorId`를 필수로 강제해 저장소와 API 규칙이 완전히 일치하지 않는다.
- 404 응답은 빈 본문이고, validation/forbidden은 JSON이며, 인증 실패는 Spring Security 기본 응답을 사용해 응답 형식이 통일되지 않는다.
- `CreatePostRequest`와 `UpdatePostRequest`의 validation 규칙이 중복돼 있다.
- 프론트 화면 네 개가 모두 인라인 CSS와 화면별 JS를 가져 공통 로직 재사용이 없다.
- 테스트가 Mock 중심이라 보안 설정, 정적 리소스 접근, JPA 실제 동작을 보호하지 못한다.

## 7. 유지보수성 관점 총평
아래 4개 항목으로 정리한다.

- 변경 용이성: 백엔드 유스케이스 분리가 돼 있어 서비스 단위 수정은 비교적 쉽다. 다만 보안 규칙과 화면 로직이 분산돼 있어 실제 사용자 흐름 변경 비용은 높다.
- 기능 확장 용이성: 게시글 기능 확장은 현재 구조 안에서 가능하다. 하지만 DTO 중복, 인라인 프론트 구조, 기본 보안 설정 의존 때문에 사용자 모델이나 공통 UI가 추가되면 재정리가 필요하다.
- 버그 발생 가능성: 보안 설정과 프론트 호출 경로 불일치가 가장 큰 리스크다. 테스트가 이 지점을 막지 못해 실행 환경에서만 드러나는 버그가 생길 가능성이 있다.
- 리팩토링 필요도: 높다. 권한 모델 정리, 보안 경로 수정, 예외 응답 통일, 프론트 공통화가 우선 과제다.

## 8. 비교 실험용 요약
아래 표를 작성한다.

| 항목 | 평가 |
|-----|-----|
| 빌드 | 우수 |
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
유스케이스 분리와 테스트는 구조화됐지만, 보안 경계와 실제 화면 동선이 어긋나 단순 게시판 흐름을 그대로 보장하지 못하는 결과물이다.
