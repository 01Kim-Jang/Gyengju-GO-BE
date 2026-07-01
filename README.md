# Gyeongju-GO Backend

경주 외국인 관광객을 위한 게이미피케이션 여행 앱 **Gyeongju-GO**의 백엔드 서버입니다.

## 기술 스택

- **Java 17** / **Spring Boot 3.5.16**
- **Spring Security 6** + **JWT** 인증
- **Spring Data JPA** + **MySQL 8.0**
- **한국관광공사 Odii API** - 경주 유적지 도슨트 데이터
- **OpenAI GPT-4o-mini** - AI 여행 가이드 챗봇 / 다국어 번역

## 주요 기능

| 기능 | 엔드포인트 |
|------|-----------|
| 회원가입 | `POST /api/users/signup` |
| 로그인 (JWT 발급) | `POST /api/auth/login` |
| 회원 조회 | `GET /api/users/{id}` |
| 언어 설정 변경 | `PATCH /api/users/{id}/language` |
| 경주 유적지 목록 | `GET /api/spots` |
| 경주 유적지 상세 | `GET /api/spots/{id}` |
| 방문 완료 (GPS 체크) | `POST /api/spots/{id}/visit` |
| 방문 목록 조회 | `GET /api/users/{id}/visited` |
| AI 챗봇 (프록시) | `POST /api/proxy/ai/chat` |
| 다국어 번역 (프록시) | `POST /api/proxy/ai/translate` |
| Odii API 프록시 | `GET /api/odii/**` |

## 시작하기

### 사전 요구사항

- Java 17
- MySQL 8.0
- Maven

### DB 설정

```sql
CREATE DATABASE gyengju_go CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 환경 설정

`src/main/resources/application-sample.yml`을 복사해서 `application.yml`을 만들고 값을 채워주세요.

```bash
cp src/main/resources/application-sample.yml src/main/resources/application.yml
```

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/gyengju_go?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
    username: [DB 유저명]
    password: [DB 비밀번호]

jwt:
  secret: [256비트 이상의 시크릿 키]
  expiration: 86400000

openai:
  api-key: [OpenAI API 키]

odii:
  api-key: [한국관광공사 Odii API 키]
```

### 실행

```bash
./mvnw spring-boot:run
```

서버 시작 시 Odii API에서 경주 유적지 데이터를 자동으로 불러와 DB에 저장합니다. (최초 1회만 실행)

## API 명세

모든 `🔒` 표시 API는 Header에 JWT 토큰이 필요합니다.
```
Authorization: Bearer eyJ...토큰
```

### 인증
| 메서드 | URL | 설명 |
|--------|-----|------|
| POST | `/api/users/signup` | 회원가입 |
| POST | `/api/auth/login` | 로그인 (JWT 발급) |

### 유적지
| 메서드 | URL | 설명 |
|--------|-----|------|
| GET 🔒 | `/api/spots` | 유적지 전체 목록 |
| GET 🔒 | `/api/spots/{id}` | 유적지 상세 |

### 퀘스트
| 메서드 | URL | 설명 |
|--------|-----|------|
| POST 🔒 | `/api/spots/{id}/visit?userId={userId}` | 방문 완료 (GPS 50m 이내) |
| GET 🔒 | `/api/users/{id}/visited` | 방문 목록 조회 |

### AI / 번역
| 메서드 | URL | 설명 |
|--------|-----|------|
| POST | `/api/proxy/ai/chat` | AI 챗봇 |
| POST | `/api/proxy/ai/translate` | 다국어 번역 |

## 지원 언어

`language` 필드 및 번역 `targetLangCode`에 아래 코드를 사용합니다.

| 코드 | 언어 |
|------|------|
| `ko` | 한국어 |
| `en` | English |
| `ja` | 日本語 |
| `zh-chs` | 中文 |
| `vi` | Tiếng Việt |
| `th` | ภาษาไทย |
| `fr` | Français |

## 프로젝트 구조

```
src/main/java/com/example/gyengju/
├── config/
│   ├── SecurityConfig.java      # Spring Security 설정
│   ├── JwtProvider.java         # JWT 생성/검증
│   ├── JwtFilter.java           # JWT 인증 필터
│   ├── RestTemplateConfig.java  # RestTemplate / ObjectMapper 빈
│   └── DataInitializer.java     # Odii API 초기 데이터 로드
└── domain/
    ├── auth/                    # 로그인 인증
    ├── user/                    # 회원 관리
    ├── spot/                    # 경주 유적지
    ├── quest/                   # 퀘스트 (방문 완료 / 방문 목록)
    └── proxy/                   # OpenAI / Odii 프록시
```
