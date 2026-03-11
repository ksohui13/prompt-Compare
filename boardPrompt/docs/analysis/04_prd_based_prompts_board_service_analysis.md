# 05_prd_based_prompts_board_service 코드 리뷰

## 1. 리뷰 대상
- 프롬프트 방식: 05_prd_based_prompts_board_service
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
- 백엔드는 `post/application`, `post/domain`, `post/infrastructure`, `post/presentation`으로 역할을 나눴다.
- 게시글 CRUD와 입력 검증, 전역 예외 응답까지는 구현되어 있다.
- 테스트는 `contextLoads()` 1건만 존재한다.
- 보안 설정은 포함했지만 `SecurityConfig`에서 `/api/**`를 모두 열어 두었고, 반대로 정적 페이지는 인증을 요구한다.
- 프런트는 정적 HTML/JS로 CRUD 화면을 갖췄지만, 중복된 DOM 처리와 에러 처리 코드가 많다.

### 확인된 주요 구현 범위
- `PostController` 기반 게시글 CRUD API 구현
- `Post` JPA 엔티티와 `PostJpaRepository` 구현
- `CreatePostUseCase`, `GetPostUseCase`, `UpdatePostUseCase`, `DeletePostUseCase` 분리
- `CreatePostRequest`, `UpdatePostRequest`의 `@NotBlank` 검증 적용
- `GlobalExceptionHandler`, `ErrorResponse` 기반 JSON 예외 응답 처리
- `index.html`, `post-create.html`, `post-detail.html`, `post-edit.html` 및 각 JS 파일 구현
- H2 메모리 DB 설정과 Spring Boot 실행 설정

## 4. 평가 기준별 리뷰

### 4.1 빌드 성공
- 빌드 가능 여부
- 실패 시 원인
- 관련 근거 파일 또는 설정

평가:
빌드 가능.

근거:
`build.gradle`에 Spring Boot Web, Validation, JPA, Security, H2 의존성이 정리되어 있고 Java 17 toolchain이 설정되어 있다. `2026-03-11`에 `boardPrompt` 경로에서 `./gradlew.bat build`를 실행했을 때 `BUILD SUCCESSFUL`을 확인했다.

리스크:
빌드는 통과하지만 테스트가 거의 없어 기능 회귀를 빌드 단계에서 막아 주지 못한다.

### 4.2 실행 가능
- 애플리케이션 실행 가능 여부
- 실행에 필요한 조건
- 실행 막는 요소

평가:
백엔드는 실행 가능. 기본 UI 진입은 바로 사용하기 어렵다.

근거:
`BoardPromptApplication`이 존재하고 `application.properties`는 H2 메모리 DB를 사용한다. `2026-03-11`에 `./gradlew.bat bootRun`으로 Tomcat 8080 기동을 확인했다. 같은 실행 검증에서 `GET /api/posts`는 `200`, `GET /`는 `403`이었다. `SecurityConfig`는 `/api/**`만 `permitAll()`이고 나머지 요청은 `authenticated()`로 묶는다.

리스크:
정적 화면 파일은 존재하지만 브라우저의 기본 진입점 `/`가 403이므로, 별도 인증 처리 없이는 HTML 기반 UI 흐름이 막힌다. 인증 정보도 코드에 고정되어 있지 않아 실행 시점에 생성되는 기본 사용자에 의존한다.

### 4.3 테스트 개수
- 테스트 파일 수
- 테스트 케이스 수
- 테스트 범위

평가:
매우 적다.

근거:
`src/test/java` 아래 테스트 파일은 `BoardPromptApplicationTests.java` 1개뿐이다. `@Test`는 `contextLoads()` 1건만 존재한다. CRUD API, 검증, 예외 응답, 보안 설정을 검증하는 테스트는 확인되지 않는다.

리스크:
기능 추가나 리팩토링 시 동작 보장을 해 줄 자동 검증 장치가 사실상 없다.

### 4.4 테스트 품질
- 정상 흐름만 검증하는지
- 실패/예외/경계값을 다루는지
- 테스트가 구조를 보호하는 수준인지

평가:
미흡하다.

근거:
유일한 테스트는 애플리케이션 컨텍스트 로딩만 확인한다. 실패 흐름, 검증 오류, 존재하지 않는 게시글 조회, 보안 정책, 프런트 동작을 검증하는 테스트는 없다.

리스크:
현재 테스트는 구조를 보호하지 못한다. 예외 처리와 보안 설정이 깨져도 빌드가 계속 통과할 수 있다.

### 4.5 패키지 구조
- 계층 분리 상태
- 역할 분리 명확성
- 파일 배치 일관성
- 확장 가능성

평가:
기본 분리는 되어 있으나 깊이는 얕다.

근거:
`post` 패키지가 `application`, `domain`, `infrastructure`, `presentation`으로 나뉘어 있고 `global/exception`, `global/security`도 분리되어 있다. 다만 유스케이스들이 모두 `PostJpaRepository` 구현체에 직접 의존한다. 각 유스케이스는 단일 메서드 위주로 매우 얇고, 도메인 규칙은 `Post.update()` 정도만 존재한다.

리스크:
계층 이름은 분리되어 있지만 포트/어댑터 경계가 없다. 기능이 늘어나면 유스케이스와 컨트롤러가 저장소 세부 구현에 계속 묶일 가능성이 높다.

### 4.6 권한 처리
- 인증/인가 관련 처리 존재 여부
- 보호 범위
- 우회 가능성
- 하드코딩 여부

평가:
설정은 있으나 보호 설계는 약하다.

근거:
`SecurityConfig`가 존재하고 세션을 `STATELESS`로 설정한다. 그러나 `/api/**`는 전부 `permitAll()`로 열려 있다. 반대로 `/`, `/index.html`, `/post-create.html` 같은 정적 리소스는 `anyRequest().authenticated()`에 걸린다. 사용자/역할 모델, 로그인 엔드포인트, 권한별 접근 제어는 코드상 확인되지 않는다.

리스크:
API는 인증 없이 CRUD가 가능하다. 정적 UI는 인증이 필요해 UX와 권한 정책이 서로 맞지 않는다.

### 4.7 예외 처리
- 예외 처리 방식
- 공통 예외 처리 존재 여부
- 사용자 응답 일관성
- 누락된 영역

평가:
기본 공통 처리만 구현되어 있다.

근거:
`GlobalExceptionHandler`가 `MethodArgumentNotValidException`, `IllegalArgumentException`, `Exception`을 공통 처리하고 `ErrorResponse` 형식으로 응답한다. 입력 검증 실패는 필드별 메시지를 내려 준다. 반면 도메인별 예외 타입 분리는 없고, 보안 예외나 데이터 접근 예외에 대한 별도 응답 설계는 확인되지 않는다.

리스크:
`IllegalArgumentException`이 모두 404로 매핑되어 예외 의미가 넓다. 예외 유형이 늘어나면 응답 정책이 빠르게 흐트러질 수 있다.

### 4.8 코드 중복
- 반복되는 로직 존재 여부
- 중복된 DTO/검증/응답/쿼리/핸들러 여부
- 리팩토링 필요 수준

평가:
중복이 눈에 띈다.

근거:
`CreatePostRequest`와 `UpdatePostRequest`는 필드와 검증 규칙이 동일하다. `GetPostUseCase`, `UpdatePostUseCase`, `DeletePostUseCase`는 게시글 존재 확인 로직과 예외 메시지를 반복한다. `post-create.js`, `post-edit.js`, `post-detail.js`, `post-list.js`는 메시지 표시, JSON 파싱, 네트워크 오류 처리, 날짜 포맷팅을 각각 다시 구현한다.

리스크:
검증 규칙이나 응답 형식을 바꿀 때 수정 지점이 여러 곳이다. 프런트와 백엔드 모두 작은 변경이 반복 수정으로 번질 가능성이 높다.

## 5. 구조적 장점
- `post` 패키지를 `application/domain/infrastructure/presentation`으로 나눠 최소한의 책임 분리를 확보했다.
- `PostController`는 HTTP 입출력만 담당하고 저장과 조회는 유스케이스 클래스로 넘긴다.
- `GlobalExceptionHandler`와 `ErrorResponse`로 검증 오류와 일반 오류의 응답 형식을 한곳에 모았다.
- 정적 HTML과 JS가 목록, 상세, 작성, 수정으로 나뉘어 있어 화면 흐름은 파일 단위로 구분된다.
- `build.gradle`과 `application.properties`만으로 Java 17, H2, Spring Boot 실행 조건을 바로 파악할 수 있다.

## 6. 구조적 한계
- 테스트가 `contextLoads()` 1건뿐이라 비교 실험의 핵심 항목인 테스트 품질을 거의 보여 주지 못한다.
- 계층은 나뉘었지만 유스케이스가 `PostJpaRepository` 구현체에 직접 연결되어 추상화 이점이 작다.
- 권한 처리가 UI와 API에서 반대로 적용된다. 정적 화면은 막혀 있고 API는 모두 열려 있다.
- `CreatePostRequest`와 `UpdatePostRequest`가 사실상 동일해 DTO 분리가 중복으로 남았다.
- 프런트 JS에서 메시지 처리, 검증, 응답 파싱이 파일마다 반복된다.
- 예외 모델이 `IllegalArgumentException` 중심이라 오류 의미가 세분화되어 있지 않다.
- 엔티티의 시간 값이 `LocalDateTime.now()`로 직접 설정되어 공통 감사 정책이나 테스트 제어 지점이 없다.

## 7. 유지보수성 관점 총평
아래 4개 항목으로 정리한다.

- 변경 용이성: 단일 게시판 CRUD 수준에서는 파일 수가 적고 흐름이 단순해 변경 지점을 찾기 쉽다. 반면 검증, 예외, 프런트 로직의 중복 때문에 정책 변경은 여러 파일 수정으로 이어진다.
- 기능 확장 용이성: 현재 패키지 구조는 확장 출발점으로는 쓸 수 있다. 하지만 저장소 추상화와 권한 모델이 없어 기능이 늘수록 구조적 이점이 빨리 약해질 가능성이 높다.
- 버그 발생 가능성: 테스트 공백이 크고 보안 정책이 UI/API 간에 어긋나 있어 회귀와 설정 버그 가능성이 높다. 프런트 중복 코드도 화면별 불일치 위험을 만든다.
- 리팩토링 필요도: 중간 이상이다. 특히 권한 정책 정리, 공통 예외 모델 정비, 프런트 공통 함수 추출, 테스트 보강이 우선이다.

## 8. 비교 실험용 요약
아래 표를 작성한다.

| 항목 | 평가 |
|-----|-----|
| 빌드 | 우수 |
| 실행 | 보통 |
| 테스트 수 | 미흡 |
| 테스트 품질 | 미흡 |
| 구조 | 보통 |
| 권한 처리 | 미흡 |
| 예외 처리 | 보통 |
| 코드 중복 | 미흡 |

평가값 작성 규칙
- 우수 / 보통 / 미흡 / 확인 불가
중 하나만 사용한다.

## 9. 한 줄 결론
이 프롬프트 방식의 결과물은 CRUD와 기본 구조를 빠르게 갖췄지만, 테스트와 권한 정책이 약해 비교 실험에서 유지보수성 차이를 분명하게 드러내는 구현이다.
