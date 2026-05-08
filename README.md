# AI Customer Support SaaS - Backend

Spring Boot 기반의 AI 고객상담 자동화 SaaS 백엔드입니다.

## 프로젝트 구조

```
com.aics.backend
├── global/
│   └── type/              # Shared Enums
│       ├── IndexStatus
│       ├── MessageRole
│       ├── ConversationStatus
│       ├── HandoffStatus
│       └── FaqDraftStatus
├── ai/
│   ├── client/
│   │   ├── AiFastApiClient          # FastAPI 클라이언트
│   │   └── MockAiClient             # 테스트용 Mock 클라이언트
│   ├── config/
│   │   ├── AiFastApiProperties
│   │   └── AiWebClientConfig
│   ├── controller/
│   │   └── AiTestController
│   └── dto/
│       ├── AiRagAskRequest
│       ├── AiRagAskResponse
│       ├── AiSourceChunk
│       ├── AiDocumentIndexRequest
│       └── AiDocumentIndexResponse
├── knowledge/             # 지식 베이스 도메인
│   ├── domain/
│   │   └── KnowledgeDocument
│   ├── repository/
│   │   └── KnowledgeDocumentRepository
│   ├── service/
│   │   └── KnowledgeService
│   ├── controller/
│   │   └── KnowledgeController
│   └── dto/
│       ├── KnowledgeCreateRequest
│       ├── KnowledgeCreateResponse
│       └── KnowledgeResponse
├── conversation/         # 대화 도메인
│   ├── domain/
│   │   ├── Conversation
│   │   └── ConversationMessage
│   ├── repository/
│   │   ├── ConversationRepository
│   │   └── ConversationMessageRepository
│   ├── service/
│   │   └── ConversationService
│   ├── controller/
│   │   └── ConversationController
│   └── dto/
│       ├── ChatRequest
│       ├── ChatResponse
│       ├── ConversationMessageResponse
│       └── ConversationDetailResponse
├── tenant/               # 테넌트 도메인 (최소 구조)
│   ├── domain/
│   │   └── Tenant
│   └── repository/
│       └── TenantRepository
├── handoff/              # 상담원 인계 도메인 (최소 구조)
│   ├── domain/
│   │   └── HandoffRequest
│   └── repository/
│       └── HandoffRequestRepository
└── faq/                  # FAQ 도메인 (최소 구조)
    ├── domain/
    │   └── FaqDraft
    └── repository/
        └── FaqDraftRepository
```

## 주요 기능

### 1. 지식 베이스 (Knowledge)
- 고객 지원 문서 저장 및 관리
- 인덱싱 상태 추적 (PENDING → INDEXING → INDEXED/FAILED)
- Tenant별 문서 관리

**API:**
- `POST /api/tenants/{tenantId}/knowledge` - 문서 생성
- `GET /api/tenants/{tenantId}/knowledge` - 문서 목록 조회

### 2. 대화 (Conversation)
- 사용자와 AI의 다중 턴 대화 관리
- 대화 상태 추적 (OPEN → WAITING_AGENT → CLOSED)
- 메시지 히스토리 저장 (USER, ASSISTANT, AGENT, SYSTEM 역할)
- 메시지별 신뢰도, 근거 유무, 핸드오프 필요 여부 저장

**API:**
- `POST /api/tenants/{tenantId}/conversations/chat` - 대화 시작/계속
- `GET /api/tenants/{tenantId}/conversations/{conversationId}` - 대화 상세 조회

### 3. Mock AI 클라이언트
현재 FastAPI 서버가 준비되지 않았으므로 MockAiClient를 사용합니다.
- 테스트 답변 반환
- 신뢰도 0.5, 근거 없음 (grounded: false), 핸드오프 불필요
- FastAPI 준비 후 실제 AiFastApiClient로 교체 가능

### 4. 기타 도메인 (최소 구조)
- **Tenant**: 다중 테넌트 지원 기초
- **Handoff**: 상담원 인계 요청 기본 정보
- **FAQ**: 자동 생성 FAQ 관리

## 기술 스택

- **Spring Boot**: 3.5.14
- **Java**: 21 (LTS)
- **Database**: H2 (개발/테스트), MySQL/PostgreSQL (프로덕션)
- **ORM**: Spring Data JPA + Hibernate
- **Code Generation**: Lombok
- **Build Tool**: Gradle

## 데이터베이스 설정

### 개발 환경 (현재)
```yaml
datasource:
  url: jdbc:h2:mem:testdb
  driver-class-name: org.h2.Driver
  h2:
    console:
      enabled: true
```

H2 Console: http://localhost:8081/h2-console

### 프로덕션 설정 (향후)
MySQL/PostgreSQL로 변경 필요

## 실행 방법

### 1. 의존성 설치
```bash
./gradlew build
```

### 2. 애플리케이션 시작
```bash
./gradlew bootRun
```

서버는 `http://localhost:8081`에서 실행됩니다.

## API 테스트

[API_TEST_EXAMPLES.md](API_TEST_EXAMPLES.md) 파일에서 curl 예제와 상세 요청/응답을 확인할 수 있습니다.

### 빠른 테스트

```bash
# 문서 등록
curl -X POST http://localhost:8081/api/tenants/1/knowledge \
  -H "Content-Type: application/json" \
  -d '{"title":"환불 정책","content":"결제 후 7일 이내 환불 가능합니다."}'

# 대화 시작
curl -X POST http://localhost:8081/api/tenants/1/conversations/chat \
  -H "Content-Type: application/json" \
  -d '{"conversationId":null,"message":"환불은 언제까지 가능한가요?"}'

# 대화 상세 조회
curl -X GET http://localhost:8081/api/tenants/1/conversations/1
```

## 설계 원칙

### 1. 다중 테넌트
모든 API는 `tenantId`를 경로에 포함하여 테넌트 격리 보장

### 2. Enum 사용
상태 관리는 Enum으로 타입 안전성 확보

### 3. TEXT 컬럼
긴 텍스트(content, question, answer 등)는 `columnDefinition = "TEXT"` 사용

### 4. Lombok
보일러플레이트 코드 최소화
- `@Entity`에 `@NoArgsConstructor(access = AccessLevel.PROTECTED)` 사용
- `@Getter` 사용 (JPA 지연 로딩 호환성)

### 5. 트랜잭션 관리
- `Service` 계층에 `@Transactional` 적용
- 읽기 작업에 `@Transactional(readOnly = true)` 적용

## 향후 개발 계획

1. **FastAPI 통합**
   - MockAiClient → AiFastApiClient 교체
   - 실제 RAG/Agent 구현 연결

2. **Tenant 서비스 확대**
   - Tenant CRUD API
   - 테넌트별 설정 관리

3. **Handoff 서비스 구현**
   - 상담원 인계 요청/처리
   - 상담원 할당 로직

4. **FAQ 자동 생성**
   - 대화에서 자동으로 FAQ 생성
   - FAQ 승인/거부 워크플로우

5. **인덱싱 서비스**
   - Knowledge Document 인덱싱 로직
   - Async 작업 처리

6. **모니터링 및 로깅**
   - 대화 분석
   - 성능 메트릭

## 개발 가이드

### Entity 작성 체크리스트
- [ ] JPA `@Entity` 적용
- [ ] Primary Key `@Id`와 `@GeneratedValue` 설정
- [ ] Enum 필드에 `@Enumerated(EnumType.STRING)` 적용
- [ ] 긴 텍스트 필드에 `columnDefinition = "TEXT"` 적용
- [ ] `@NoArgsConstructor(access = AccessLevel.PROTECTED)` 적용
- [ ] 비즈니스 로직 메서드 구현

### Repository 작성 체크리스트
- [ ] `JpaRepository` 상속
- [ ] `@Repository` 어노테이션 적용
- [ ] 필요한 커스텀 쿼리 메서드 정의

### Service 작성 체크리스트
- [ ] `@Service` 어노테이션 적용
- [ ] `@RequiredArgsConstructor`로 의존성 주입
- [ ] 쓰기 메서드에 `@Transactional` 적용
- [ ] 읽기 메서드에 `@Transactional(readOnly = true)` 적용

### Controller 작성 체크리스트
- [ ] `@RestController` 어노테이션 적용
- [ ] `@RequestMapping` 경로 설정
- [ ] `@PathVariable` 및 `@RequestBody` 적절히 사용
- [ ] 적절한 HTTP Status Code 반환

## 문제 해결

### H2 Console 접근 불가
- URL이 `/h2-console`인지 확인
- 브라우저에서 JDBC URL: `jdbc:h2:mem:testdb` 입력

### 포트 충돌
- `application.yml`에서 `server.port` 변경

### Gradle 빌드 실패
```bash
./gradlew clean build
```

## 참고 자료

- [Spring Boot 공식 문서](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Lombok](https://projectlombok.org/)
- [API 테스트 예제](API_TEST_EXAMPLES.md)
