# 공통 프롬프트

이 프롬프트는 모든 실험 시작 전에 항상 먼저 입력한다.

## 프로젝트 구조
board-service
├ src/main/java/com/example/board
│
├ auth                          # 인증/사용자 도메인 (회원가입, 로그인, JWT 등)
│ ├ presentation                # 외부 요청을 받는 계층 (Controller, Request/Response DTO)
│ │                             # HTTP API 엔드포인트 정의
│ │                             # 요청 검증 및 응답 변환 담당
│ │
│ ├ application                 # 애플리케이션 서비스 계층 (UseCase / Service)
│ │                             # 도메인 로직을 조합하여 실제 기능을 수행
│ │                             # 트랜잭션 처리
│ │                             # 여러 도메인 객체를 orchestration
│ │
│ ├ domain                      # 핵심 비즈니스 모델
│ │                             # Entity, Value Object, Domain Service
│ │                             # 순수한 비즈니스 규칙을 포함
│ │                             # 프레임워크 의존 최소화
│ │
│ └ infrastructure              # 외부 시스템과의 연결 계층
│                               # Repository 구현
│                               # JPA Entity 매핑
│                               # JWT Provider
│                               # DB 접근 구현
│
├ post                          # 게시글 도메인 (게시글 CRUD)
│ ├ presentation                # 게시글 API Controller
│ │                             # 게시글 등록/조회/수정/삭제 API
│ │
│ ├ application                 # 게시글 서비스 로직
│ │                             # 게시글 생성, 수정, 삭제 등의 UseCase
│ │                             # 도메인 객체를 이용한 실제 작업 수행
│ │
│ ├ domain                      # 게시글 핵심 모델
│ │                             # Post Entity
│ │                             # 비즈니스 규칙
│ │
│ └ infrastructure              # DB 연동 계층
│                               # PostRepository (JPA)
│                               # DB persistence 구현
│
├ global                        # 애플리케이션 전역 공통 모듈
│ ├ config                      # 스프링 설정
│ │                             # Bean 설정
│ │                             # JPA 설정
│ │                             # Web 설정
│ │
│ ├ security                    # Spring Security 설정
│ │                             # SecurityFilterChain
│ │                             # JWT Filter
│ │                             # Authentication 설정
│ │
│ └ exception                   # 전역 예외 처리
│                               # Custom Exception
│                               # GlobalExceptionHandler
│                               # ErrorResponse 정의
│
└ BoardApplication              # Spring Boot Application Entry Point
                                # 애플리케이션 시작 클래스
---

You are a senior Spring Boot backend engineer.

Use the following stack:

Java 17  
Spring Boot 3.5.x  
Gradle  
Spring Data JPA  
Spring Security  
Spring Validation  
H2 Database  
Lombok  

Project package

com.example.board

Project architecture

auth
- presentation
- application
- domain
- infrastructure

post
- presentation
- application
- domain
- infrastructure

global
- config
- security
- exception

Important rules

- This project must be compatible with Java 17 and Spring Boot 3.x.
- Use `jakarta.*` packages when needed.
- Keep code simple and runnable in a Spring Boot application.
- Modify only the scope requested in each step.
- Do not refactor unrelated modules.
- Keep auth and post domains separated.

Before writing any code explain your reasoning step by step.

Always follow this output format:

1. Plan
2. Reasoning
3. Implementation
4. Files created or modified
5. Verification steps
6. Remaining risks or assumptions