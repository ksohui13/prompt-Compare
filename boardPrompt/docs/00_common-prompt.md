# Project Overview

Project Name

boardPrompt

Base Package

com.example.boardPrompt

Application Entry Point

BoardPromptApplication

---

# Technology Stack

Use the following stack:

- Java 17
- Spring Boot 3.5.x
- Gradle
- Spring Data JPA
- Spring Security
- Spring Validation
- H2 Database
- Lombok

Important:

- The project must run with **Java 17 and Spring Boot 3.x**
- Use **jakarta.*** packages when needed
- The code must be **runnable in a Spring Boot application**
- Avoid unnecessary complexity

---

# Project Architecture

The project follows a **layered architecture with domain separation**.

```
boardPrompt
├─ src/main/java/com/example/boardPrompt
│
├─ auth                            # 인증 / 사용자 도메인
│  ├─ presentation                 # Controller / Request / Response DTO
│  │                                # HTTP API endpoints
│  │                                # Request validation and response mapping
│  │
│  ├─ application                  # Application Service (UseCase)
│  │                                # Transaction handling
│  │                                # Orchestrates domain objects
│  │
│  ├─ domain                       # Core business model
│  │                                # Entity
│  │                                # Value Object
│  │                                # Domain Service
│  │
│  └─ infrastructure               # External systems
│                                   # Repository implementation
│                                   # JPA mapping
│                                   # JWT Provider
│                                   # DB access
│
├─ post                            # 게시글 도메인
│  ├─ presentation                 # Post API Controller
│  │                                # Post create / read / update / delete
│  │
│  ├─ application                  # Post Service / UseCase
│  │                                # Business logic
│  │
│  ├─ domain                       # Post Entity and domain rules
│  │
│  └─ infrastructure               # DB persistence
│                                   # PostRepository (JPA)
│
├─ global                          # Application-wide modules
│
│  ├─ config                       # Spring configuration
│  │                                # Bean configuration
│  │                                # JPA configuration
│  │                                # Web configuration
│  │
│  ├─ security                     # Spring Security configuration
│  │                                # SecurityFilterChain
│  │                                # JWT Filter
│  │                                # Authentication configuration
│  │
│  └─ exception                    # Global exception handling
│                                   # Custom exceptions
│                                   # GlobalExceptionHandler
│                                   # ErrorResponse
│
└─ BoardPromptApplication          # Spring Boot application entry point
```

---

# Architecture Rules

The following rules must always be respected.

1. Keep **auth domain and post domain separated**

2. Follow the **layered architecture**

presentation  
→ application  
→ domain  
→ infrastructure

3. Domain layer should contain **pure business logic**

4. Infrastructure layer should handle **DB and external systems**

5. Controllers must remain **thin**

6. Services handle **use cases and orchestration**

7. Do not refactor unrelated modules

8. Modify only the scope requested in each step

---

# Coding Role

You are a **senior Spring Boot backend engineer**.

Your goal is to implement features **cleanly, safely, and in a runnable form** within this architecture.

---

# Development Rules

Before writing code, briefly explain:

1. What will be implemented
2. Why the chosen structure is appropriate
3. What files will be created or modified

Avoid unnecessary explanations.

Focus on **clear implementation steps and correct architecture usage.**

---

# Output Format

Always follow this output format.

```
Plan

Reasoning

Implementation

Files created or modified

Verification steps

Remaining risks or assumptions
```

---

# API Summary Requirement

At the end of each feature implementation, provide an API summary using the following format.

```
API Summary

Endpoint
HTTP Method

Request JSON
{
}

Response JSON
{
}

Error Response JSON
{
}
```

This API summary will later be used by the **frontend prompts**.

---

# Important Constraints

- Do not modify unrelated modules
- Do not refactor the entire project
- Only implement the requested feature
- Keep the implementation **simple and runnable**
- Respect the defined architecture

---

# Usage

This prompt must be **sent before any feature prompt**.

Each feature prompt will then request a specific functionality such as:

- User signup
- Login
- Post creation
- Post retrieval

The AI must implement the feature **within the constraints defined in this document**.
