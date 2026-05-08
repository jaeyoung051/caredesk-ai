# AI Customer Support SaaS - Backend API Test Examples

## Overview
This Spring Boot backend is designed for AI-based customer support automation. The current implementation includes Knowledge Base management and Conversation features, with mock AI client for testing without FastAPI server.

## Knowledge API

### 1. Create Knowledge Document
```http
POST http://localhost:8081/api/tenants/1/knowledge
Content-Type: application/json

{
  "title": "환불 정책",
  "content": "결제 후 7일 이내 환불 가능합니다."
}
```

**Expected Response (201 Created):**
```json
{
  "knowledgeId": 1,
  "title": "환불 정책",
  "indexStatus": "PENDING"
}
```

### 2. Get Knowledge Documents List
```http
GET http://localhost:8081/api/tenants/1/knowledge
```

**Expected Response (200 OK):**
```json
[
  {
    "knowledgeId": 1,
    "title": "환불 정책",
    "content": "결제 후 7일 이내 환불 가능합니다.",
    "indexStatus": "PENDING",
    "createdAt": "2026-05-08T10:30:00",
    "updatedAt": "2026-05-08T10:30:00"
  }
]
```

---

## Conversation API

### 3. Start New Conversation with AI Question
```http
POST http://localhost:8081/api/tenants/1/conversations/chat
Content-Type: application/json

{
  "conversationId": null,
  "message": "환불은 언제까지 가능한가요?"
}
```

**Expected Response (200 OK):**
```json
{
  "conversationId": 1,
  "answer": "현재 AI 서버 준비 전 테스트 응답입니다. 실제 RAG 응답은 추후 연결됩니다.",
  "intent": "FAQ_QUESTION",
  "confidence": 0.5,
  "grounded": false,
  "needHandoff": false,
  "sources": []
}
```

### 4. Continue Conversation with Existing Conversation ID
```http
POST http://localhost:8081/api/tenants/1/conversations/chat
Content-Type: application/json

{
  "conversationId": 1,
  "message": "다른 결제 수단으로는 어떤가요?"
}
```

**Expected Response (200 OK):**
```json
{
  "conversationId": 1,
  "answer": "현재 AI 서버 준비 전 테스트 응답입니다. 실제 RAG 응답은 추후 연결됩니다.",
  "intent": "FAQ_QUESTION",
  "confidence": 0.5,
  "grounded": false,
  "needHandoff": false,
  "sources": []
}
```

### 5. Get Conversation Details with Message History
```http
GET http://localhost:8081/api/tenants/1/conversations/1
```

**Expected Response (200 OK):**
```json
{
  "conversationId": 1,
  "status": "OPEN",
  "messages": [
    {
      "messageId": 1,
      "role": "USER",
      "content": "환불은 언제까지 가능한가요?",
      "confidence": null,
      "grounded": null,
      "needHandoff": null,
      "createdAt": "2026-05-08T10:30:00"
    },
    {
      "messageId": 2,
      "role": "ASSISTANT",
      "content": "현재 AI 서버 준비 전 테스트 응답입니다. 실제 RAG 응답은 추후 연결됩니다.",
      "confidence": 0.5,
      "grounded": false,
      "needHandoff": false,
      "createdAt": "2026-05-08T10:30:01"
    },
    {
      "messageId": 3,
      "role": "USER",
      "content": "다른 결제 수단으로는 어떤가요?",
      "confidence": null,
      "grounded": null,
      "needHandoff": null,
      "createdAt": "2026-05-08T10:30:02"
    },
    {
      "messageId": 4,
      "role": "ASSISTANT",
      "content": "현재 AI 서버 준비 전 테스트 응답입니다. 실제 RAG 응답은 추후 연결됩니다.",
      "confidence": 0.5,
      "grounded": false,
      "needHandoff": false,
      "createdAt": "2026-05-08T10:30:03"
    }
  ]
}
```

---

## Error Responses

### Invalid Conversation ID
```http
GET http://localhost:8081/api/tenants/1/conversations/999
```

**Expected Response (400 Bad Request):**
```json
{
  "error": "Conversation not found: 999"
}
```

### Tenant Mismatch
```http
POST http://localhost:8081/api/tenants/2/conversations/chat
Content-Type: application/json

{
  "conversationId": 1,
  "message": "Hello"
}
```

**Expected Response (400 Bad Request):**
```json
{
  "error": "Tenant ID mismatch"
}
```

---

## Architecture Overview

### Current Implementation
- **Enums**: IndexStatus, MessageRole, ConversationStatus, HandoffStatus, FaqDraftStatus
- **Knowledge Domain**: Document storage with indexing status tracking
- **Conversation Domain**: Multi-turn conversation management with message history
- **Mock AI Client**: Test responses without FastAPI server

### Future Integration
- FastAPI server integration for real RAG/Agent implementation
- Tenant domain for multi-tenancy support
- Handoff domain for escalation to human agents
- FAQ domain for FAQ generation from conversations

### Technology Stack
- Spring Boot 3.5.14
- Java 21
- Spring Data JPA with Hibernate
- H2 Database (for development/testing)
- Lombok for code generation

---

## Testing with curl

```bash
# Create knowledge
curl -X POST http://localhost:8081/api/tenants/1/knowledge \
  -H "Content-Type: application/json" \
  -d '{"title":"환불 정책","content":"결제 후 7일 이내 환불 가능합니다."}'

# Start conversation
curl -X POST http://localhost:8081/api/tenants/1/conversations/chat \
  -H "Content-Type: application/json" \
  -d '{"conversationId":null,"message":"환불은 언제까지 가능한가요?"}'

# Get conversation details
curl -X GET http://localhost:8081/api/tenants/1/conversations/1
```

---

## Notes

1. **Mock AI Client**: Currently uses MockAiClient for testing. Replace with real AiFastApiClient when FastAPI server is ready.
2. **Database**: Using H2 in-memory database. Change to MySQL/PostgreSQL for production.
3. **Tenant ID**: All APIs require tenantId in the path. This supports multi-tenancy.
4. **Message Roles**: USER (사용자), ASSISTANT (어시스턴트), AGENT (상담원), SYSTEM (시스템)
5. **Conversation Status**: OPEN (진행중), WAITING_AGENT (상담원대기중), CLOSED (종료)
