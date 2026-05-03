# GitHub 기반 AI CS Managed Service 개발 순서 정리

## 0. 목표

이 문서의 목표는 **AI CS Managed Service**를 만들기 위해 지금부터 해야 할 일을 순서대로 정리하는 것이다.

핵심 방향은 다음과 같다.

```text
유사 GitHub 프로젝트 분석
→ RAG / Agent / CS 운영 구조 이해
→ 새 레포지토리 생성
→ 필요한 구조만 자체 구현
→ AI CS 운영형 MVP 제작
→ 실제 고객사 테스트
```

중요한 원칙:

```text
GitHub 코드를 그대로 복붙해서 판매하지 않는다.
라이선스를 확인한다.
구조와 흐름을 참고하되, 우리 서비스에 맞게 새로 구현한다.
```

---

## 1. 최종적으로 만들 서비스

### 서비스 한 줄 정의

**기업과 소상공인의 반복 고객 문의를 AI가 대신 처리하고, 월별 CS 사용량에 따라 과금하는 AI CS 운영 서비스**

### 초기 MVP 목표

```text
고객사가 FAQ/정책 문서를 등록하면
고객 질문에 대해 AI가 답변하고
상담 로그와 사용량을 저장하며
필요하면 사람 상담원에게 이관하는 내부 운영형 MVP
```

---

## 2. 먼저 봐야 할 GitHub 프로젝트 목록

GitHub 프로젝트는 한 종류만 보면 안 된다.

각 프로젝트는 참고 목적이 다르다.

---

# 2.1 RAG / 고객지원 챗봇 참고용

## 1) renaldiangsar/Customer-Support-RAG

- GitHub: https://github.com/renaldiangsar/Customer-Support-RAG
- 목적: 고객지원 RAG 챗봇 구조 이해
- 특징:
  - Streamlit 기반
  - LangChain
  - ChromaDB
  - HuggingFace Embedding
  - PDF 기반 지식베이스
  - 대화 기억 기능

### 여기서 배울 것

```text
문서 업로드
PDF 로딩
문서 chunking
embedding 생성
Vector DB 저장
사용자 질문 입력
관련 문서 검색
검색 결과 기반 답변 생성
대화 기록 유지
```

### 우리 프로젝트에 적용할 부분

```text
고객사 FAQ/정책 문서 등록
고객사별 Vector DB 저장
질문에 맞는 정책 검색
검색 근거 기반 AI 답변 생성
```

---

## 2) ejazalam831/rag-customer-support-chatbot

- GitHub: https://github.com/ejazalam831/rag-customer-support-chatbot
- 목적: LangChain + LangGraph 기반 고객지원 RAG 구조 참고
- 특징:
  - LangChain
  - LangGraph
  - Mistral AI
  - Knowledge Base 기반 답변
  - Conversation Memory
  - Session Persistence

### 여기서 배울 것

```text
LangGraph 흐름
대화 메모리
세션 유지
질문 → 검색 → 답변 생성 파이프라인
RAG 기반 환각 방지 구조
```

### 우리 프로젝트에 적용할 부분

```text
상담 세션 유지
고객별 대화 흐름 저장
고객사별 FAQ 기반 답변
AI 응답 품질 개선
```

---

## 3) raksbisht/sitechat

- GitHub: https://github.com/raksbisht/sitechat
- 목적: 웹사이트/문서 기반 self-hosted AI 챗봇 참고
- 특징:
  - Website Chatbot
  - RAG
  - Embeddable Widget
  - Self-hosted
  - Privacy-focused

### 여기서 배울 것

```text
웹사이트에 삽입 가능한 챗봇 위젯 구조
고객사 사이트 문서 기반 답변
Self-hosted 배포 흐름
간단한 웹채팅 연결 방식
```

### 우리 프로젝트에 적용할 부분

```text
초기 웹채팅 채널
고객사 사이트 삽입용 상담 위젯
카카오톡 연동 전 테스트 채널
```

---

# 2.2 Agent / Tool Calling 참고용

## 4) AkshatBhat/Langflow-Customer-Support-Agent

- GitHub: https://github.com/AkshatBhat/Langflow-Customer-Support-Agent
- 목적: 고객지원 Multi-Agent 구조 참고
- 특징:
  - Langflow
  - Streamlit
  - OpenAI GPT-4o mini
  - AstraDB Vector Search
  - FAQAgent
  - OrderLookupAgent
  - ManagerAgent

### 여기서 배울 것

```text
Manager Agent가 문의를 분류하는 방식
FAQAgent와 OrderLookupAgent 역할 분리
문의 유형별 Agent Routing
파일 업로드를 통한 지식베이스 확장
```

### 우리 프로젝트에 적용할 부분

```text
문의 유형 분류
배송/환불/예약/가격 문의 분기
FAQ 처리 Agent
사람 이관 판단 Agent
상담 요약 Agent
```

---

## 5) RasaHQ/helpdesk-assistant

- GitHub: https://github.com/RasaHQ/helpdesk-assistant
- 목적: 고객 문의를 받아 실제 외부 시스템 API를 호출하는 Helpdesk Assistant 참고
- 특징:
  - Rasa 기반
  - IT Helpdesk Assistant
  - ServiceNow API 연동
  - Incident Report 생성
  - Incident Status 확인

### 여기서 배울 것

```text
문의 내용을 기반으로 필요한 정보를 수집하는 방식
상담 중 부족한 정보를 다시 질문하는 방식
외부 API 호출 구조
티켓 생성 흐름
상태 확인 흐름
```

### 우리 프로젝트에 적용할 부분

```text
예약 접수
환불 접수
상담 티켓 생성
사람 상담원 이관 요청 생성
외부 업무 시스템 연동
```

---

## 6) ro-anderson/multi-agent-rag-customer-support

- GitHub: https://github.com/ro-anderson/multi-agent-rag-customer-support
- 목적: Multi-Agent RAG 고객지원 시스템 구조 참고
- 특징:
  - Python
  - LangChain
  - LangGraph
  - Multi-Agent RAG
  - Flight / Hotel / Car Rental / Excursion 등 여러 업무 흐름 처리

### 여기서 배울 것

```text
여러 업무를 Agent별로 분리하는 방식
각 업무별 Assistant를 두는 방식
LangGraph 기반 Multi-Agent Flow
복잡한 고객지원 업무 분기 처리
```

### 우리 프로젝트에 적용할 부분

```text
업종별 CS 템플릿
쇼핑몰 Agent
예약업 Agent
학원 Agent
사람 이관 Agent
월간 요약 Agent
```

---

# 2.3 CS 운영 구조 참고용

## 7) chatwoot/chatwoot

- GitHub: https://github.com/chatwoot/chatwoot
- 목적: 실제 고객지원 플랫폼 구조 참고
- 특징:
  - 오픈소스 고객지원 플랫폼
  - Live Chat
  - Email Support
  - Omnichannel Inbox
  - Intercom / Zendesk / Salesforce Service Cloud 대안
  - 상담방, 메시지, 고객, 상담원, 채널 관리 구조

### 여기서 배울 것

```text
Inbox 구조
Conversation 구조
Message 구조
Customer 구조
Agent 구조
Channel 구조
상담 상태 관리
CS 운영 화면 구성
```

### 우리 프로젝트에 적용할 부분

```text
상담 로그 화면
상담 상태값
사람 상담원 이관 화면
고객사별 문의함
관리자 콘솔 구조
```

---

## 8) abhinavxd/libredesk

- GitHub: https://github.com/abhinavxd/libredesk
- 목적: 가벼운 self-hosted omnichannel customer support desk 구조 참고
- 특징:
  - Open source
  - Self-hosted
  - Omnichannel Customer Support Desk
  - Live Chat
  - Email
  - Single Binary
  - AGPL-3.0 License

### 여기서 배울 것

```text
가벼운 Helpdesk 구조
Live Chat + Email 통합
Conversation 관리
Self-hosted 배포 구조
CS 대시보드 구성
```

### 주의점

```text
AGPL-3.0 라이선스이므로 상업 서비스 코드에 직접 가져다 쓰면 위험할 수 있다.
구조 참고용으로만 보는 것을 권장한다.
```

### 우리 프로젝트에 적용할 부분

```text
관리자 콘솔 UX
Live Chat 흐름
Email 상담 구조
Conversation 상태 관리
```

---

# 3. 공부 및 분석 순서

처음부터 큰 프로젝트를 보면 안 된다.

아래 순서로 본다.

---

## 1순위: 작은 RAG 프로젝트 실행

먼저 볼 프로젝트:

```text
renaldiangsar/Customer-Support-RAG
```

해야 할 일:

```text
1. GitHub repo 접속
2. README 읽기
3. 로컬에 clone
4. requirements.txt 확인
5. 실행 방법 확인
6. Streamlit 앱 실행
7. PDF/FAQ 문서 업로드
8. 질문 입력
9. 답변 생성 흐름 확인
```

확인할 코드:

```text
문서 로딩 코드
문서 chunking 코드
embedding 생성 코드
vector DB 저장 코드
retriever 코드
prompt 코드
LLM 호출 코드
응답 출력 코드
```

---

## 2순위: LangChain / LangGraph RAG 흐름 분석

다음 볼 프로젝트:

```text
ejazalam831/rag-customer-support-chatbot
```

해야 할 일:

```text
1. README 읽기
2. Notebook 또는 실행 파일 확인
3. LangChain 구조 확인
4. LangGraph 사용 방식 확인
5. Conversation Memory 확인
6. Session Persistence 확인
```

확인할 코드:

```text
Graph node
Retriever node
Generator node
Memory 관리
Session 관리
```

---

## 3순위: Multi-Agent 구조 분석

다음 볼 프로젝트:

```text
AkshatBhat/Langflow-Customer-Support-Agent
ro-anderson/multi-agent-rag-customer-support
```

해야 할 일:

```text
1. Agent가 몇 개로 나뉘어 있는지 확인
2. Manager Agent가 어떤 기준으로 라우팅하는지 확인
3. FAQ Agent / OrderLookup Agent 같은 역할 분리 확인
4. Agent 간 데이터 흐름 확인
5. 우리 서비스에 필요한 Agent 목록 작성
```

우리 서비스 Agent 예시:

```text
FAQAnswerAgent
IntentClassifierAgent
HumanHandoffAgent
UsageMeteringAgent
ConversationSummaryAgent
ReportAgent
```

---

## 4순위: Helpdesk 운영 구조 분석

다음 볼 프로젝트:

```text
chatwoot/chatwoot
abhinavxd/libredesk
```

해야 할 일:

```text
1. Conversation 구조 확인
2. Message 구조 확인
3. Inbox 구조 확인
4. Agent 구조 확인
5. Channel 구조 확인
6. 상담 상태값 확인
7. 관리자 화면 흐름 확인
```

이 단계에서는 전체 코드를 깊게 파지 않는다.

우선 아래만 본다.

```text
DB 구조
화면 흐름
상담 상태 관리
채널 분리 방식
```

---

# 4. 우리 프로젝트로 바꾸는 기준

GitHub 프로젝트 대부분은 데모 챗봇이다.

우리 서비스는 돈을 받는 CS 운영 서비스다.

---

## 기존 데모 프로젝트 구조

```text
사용자 질문
→ 문서 검색
→ AI 답변
→ 화면 출력
```

---

## 우리 서비스 구조

```text
고객 질문
→ 고객사 식별
→ 상담 세션 생성
→ FAQ/정책 검색
→ AI 답변 생성
→ 자동 응답 가능 여부 판단
→ 사람 이관 여부 판단
→ 상담 로그 저장
→ 사용량 집계
→ 월간 리포트 반영
```

---

## 기존 코드에서 참고할 것

```text
RAG 흐름
문서 chunking
embedding 생성
vector DB 검색
prompt 구성
LLM 호출
대화 메모리
Agent routing
```

---

## 새로 만들어야 할 것

```text
고객사 관리
상담 로그 관리
사용량 집계
사람 이관
월간 리포트
과금 기준
채널 연동 구조
관리자 콘솔
```

---

# 5. 새 프로젝트 레포 구조

새 GitHub repo를 만든다.

추천 이름:

```text
ai-cs-managed-service
```

추천 구조:

```text
ai-cs-managed-service/
  frontend/
    landing/
    admin/
    components/
    api/
    types/

  backend/
    auth/
    companies/
    channels/
    knowledge/
    conversations/
    messages/
    ai/
      llm-gateway/
      prompt-manager/
      rag/
      guardrails/
      structured-output/
    handoff/
    usage/
    billing/
    reports/

  infra/
    docker/
    database/
    redis/
    vector-db/
    monitoring/

  docs/
    business-plan.md
    github-study-roadmap.md
    api-spec.md
    architecture.md
    db-erd.md
    prompt-design.md
    deployment.md
```

---

# 6. 실제 개발 순서

## Phase 1. RAG 챗봇 최소 구현

목표:

```text
FAQ 문서를 넣으면 질문에 답하는 기능
```

구현 항목:

```text
고객사 등록
FAQ 등록
문서 chunking
embedding 생성
vector DB 저장
질문 입력
관련 FAQ 검색
AI 답변 생성
```

이 단계에서는 아래 기능은 제외한다.

```text
결제
카카오톡
보이스
복잡한 대시보드
```

---

## Phase 2. CS 운영 데이터 추가

목표:

```text
답변만 하는 챗봇이 아니라 상담 기록이 남는 CS 시스템으로 변경
```

구현 항목:

```text
conversations 테이블
messages 테이블
ai_responses 테이블
handoff_requests 테이블
상담 상태값
```

상담 상태 예시:

```text
AI_HANDLED
NEED_HUMAN
HUMAN_IN_PROGRESS
RESOLVED
FAILED
```

---

## Phase 3. AI 응답을 JSON 구조로 변경

목표:

```text
AI 답변을 백엔드가 처리 가능한 형태로 받기
```

예시 출력:

```json
{
  "intent": "refund_inquiry",
  "urgency": "medium",
  "sentiment": "neutral",
  "can_ai_answer": true,
  "need_human": false,
  "answer": "환불은 상품 수령 후 7일 이내 신청 가능합니다.",
  "summary": "고객이 환불 가능 여부를 문의함",
  "usage_unit": 1
}
```

이 구조가 필요한 이유:

```text
need_human = true → 관리자 알림
intent = refund_inquiry → 환불 정책 검색
intent = reservation → 예약 프로세스 연결
usage_unit → 사용량 집계
summary → 월간 리포트 반영
```

---

## Phase 4. 사용량 집계 추가

목표:

```text
월별 과금 가능한 구조 만들기
```

구현 항목:

```text
usage_records 테이블
AI 응답 건수 저장
상담 세션 수 저장
사람 이관 건수 저장
월별 사용량 조회 API
```

예시 테이블:

```text
usage_records
- id
- company_id
- conversation_id
- usage_type
- quantity
- unit_price
- created_at
```

사용량 타입 예시:

```text
AI_RESPONSE
CONVERSATION_SESSION
HUMAN_HANDOFF
SUMMARY_GENERATION
VOICE_MINUTE
```

---

## Phase 5. 내부 관리자 화면 제작

목표:

```text
고객사별 운영 상태를 확인할 수 있게 만들기
```

화면 구성:

```text
고객사 목록
FAQ 등록 화면
질문 테스트 화면
상담 로그 화면
사람 이관 목록
월별 사용량 화면
간단 리포트 화면
```

초기에는 고객사가 직접 쓰는 화면보다 내부 운영자가 쓰는 화면을 먼저 만든다.

---

## Phase 6. 실제 채널 연결

초기에는 웹 테스트 채널만 만든다.

그다음 아래 순서로 확장한다.

```text
1. 웹채팅
2. 이메일
3. 카카오톡
4. 전화/보이스
```

주의:

```text
카카오톡이나 보이스부터 시작하면 개발 난이도가 급격히 올라간다.
처음에는 웹채팅 또는 내부 테스트 채널로 AI CS 처리 흐름을 검증한다.
```

---

# 7. 지금 당장 해야 하는 체크리스트

## Step 1. GitHub 프로젝트 북마크

아래 프로젝트를 모두 북마크한다.

```text
RAG:
- https://github.com/renaldiangsar/Customer-Support-RAG
- https://github.com/ejazalam831/rag-customer-support-chatbot
- https://github.com/raksbisht/sitechat

Agent:
- https://github.com/AkshatBhat/Langflow-Customer-Support-Agent
- https://github.com/RasaHQ/helpdesk-assistant
- https://github.com/ro-anderson/multi-agent-rag-customer-support

CS 운영:
- https://github.com/chatwoot/chatwoot
- https://github.com/abhinavxd/libredesk
```

---

## Step 2. 첫 번째 repo 실행

첫 번째로 실행할 repo:

```text
https://github.com/renaldiangsar/Customer-Support-RAG
```

해야 할 것:

```text
git clone
가상환경 생성
requirements 설치
환경변수 설정
Streamlit 실행
FAQ/PDF 문서 업로드
질문 테스트
답변 흐름 확인
```

---

## Step 3. 실행하면서 코드 흐름 기록

`docs/github-study-roadmap.md` 파일에 아래 내용을 기록한다.

```text
1. 질문 입력은 어느 파일에서 받는가?
2. 문서 로딩은 어디서 하는가?
3. chunking은 어디서 하는가?
4. embedding은 어디서 생성하는가?
5. vector DB는 무엇을 쓰는가?
6. 검색 함수는 어디에 있는가?
7. prompt는 어디서 만드는가?
8. LLM 호출은 어디서 하는가?
9. 응답은 어떤 형태로 반환되는가?
10. 우리 서비스에 그대로 쓸 수 있는 구조는 무엇인가?
```

---

## Step 4. 새 프로젝트 repo 생성

새 repo 이름:

```text
ai-cs-managed-service
```

처음 커밋할 문서:

```text
README.md
docs/business-plan.md
docs/github-study-roadmap.md
docs/architecture.md
```

---

## Step 5. 첫 번째 MVP 구현

첫 번째 구현 목표:

```text
고객사 FAQ를 등록하고
고객 질문에 대해 AI가 답변하며
상담 로그와 사용량을 저장하는 MVP
```

최소 API:

```text
POST /companies
POST /knowledge
POST /conversations
POST /messages
POST /ai/answer
GET /conversations
GET /usage/monthly
GET /reports/monthly
```

---

# 8. MVP DB 초안

```text
companies
- id
- name
- industry
- created_at

users
- id
- company_id
- email
- password_hash
- role
- created_at

knowledge_documents
- id
- company_id
- title
- content
- source_type
- created_at

knowledge_chunks
- id
- company_id
- document_id
- chunk_text
- embedding_id
- metadata
- created_at

conversations
- id
- company_id
- channel
- external_user_id
- status
- started_at
- ended_at

messages
- id
- conversation_id
- sender_type
- content
- created_at

ai_responses
- id
- conversation_id
- message_id
- intent
- urgency
- sentiment
- can_ai_answer
- need_human
- answer
- summary
- created_at

handoff_requests
- id
- company_id
- conversation_id
- reason
- status
- created_at
- resolved_at

usage_records
- id
- company_id
- conversation_id
- usage_type
- quantity
- unit_price
- created_at

monthly_reports
- id
- company_id
- month
- total_conversations
- ai_handled_count
- human_handoff_count
- automation_rate
- estimated_saved_hours
- created_at
```

---

# 9. MVP API 초안

## 고객사 등록

```http
POST /api/companies
```

```json
{
  "name": "ABC 쇼핑몰",
  "industry": "ECOMMERCE"
}
```

---

## FAQ/정책 등록

```http
POST /api/knowledge
```

```json
{
  "companyId": 1,
  "title": "환불 정책",
  "content": "상품 수령 후 7일 이내 환불 가능합니다.",
  "sourceType": "FAQ"
}
```

---

## 고객 질문 입력

```http
POST /api/messages
```

```json
{
  "companyId": 1,
  "channel": "WEB_CHAT",
  "externalUserId": "customer_123",
  "content": "환불 가능한가요?"
}
```

---

## AI 답변 생성

```http
POST /api/ai/answer
```

```json
{
  "companyId": 1,
  "conversationId": 10,
  "message": "환불 가능한가요?"
}
```

응답 예시:

```json
{
  "intent": "refund_inquiry",
  "urgency": "medium",
  "sentiment": "neutral",
  "can_ai_answer": true,
  "need_human": false,
  "answer": "환불은 상품 수령 후 7일 이내 신청 가능합니다.",
  "summary": "고객이 환불 가능 여부를 문의함",
  "usage_unit": 1
}
```

---

## 월별 사용량 조회

```http
GET /api/usage/monthly?companyId=1&month=2026-05
```

응답 예시:

```json
{
  "companyId": 1,
  "month": "2026-05",
  "aiResponseCount": 860,
  "conversationCount": 1240,
  "humanHandoffCount": 380,
  "estimatedCharge": 99000
}
```

---

# 10. 라이선스 주의

GitHub 코드를 참고할 때는 반드시 라이선스를 확인한다.

특히 주의할 라이선스:

```text
GPL
AGPL
SSPL
```

이 계열은 상업 서비스에 직접 섞으면 공개 의무나 배포 의무 문제가 생길 수 있다.

상대적으로 참고하기 쉬운 라이선스:

```text
MIT
Apache-2.0
BSD
```

권장 방식:

```text
1. 코드 복붙 금지
2. 구조와 설계만 참고
3. 새 프로젝트에서 직접 구현
4. README에 참고 프로젝트 링크만 정리
5. 실제 배포 전 라이선스 재확인
```

---

# 11. 최종 실행 순서 요약

```text
1. GitHub 참고 repo 북마크
2. renaldiangsar/Customer-Support-RAG 로컬 실행
3. RAG 흐름 분석
4. ejazalam831/rag-customer-support-chatbot 분석
5. Multi-Agent 프로젝트 분석
6. Chatwoot / Libredesk에서 CS 운영 구조 확인
7. 새 repo ai-cs-managed-service 생성
8. 고객사/FAQ/질문/AI답변 MVP 구현
9. 상담 로그 저장
10. AI 응답 JSON 구조화
11. 사람 이관 판단 추가
12. 사용량 집계 추가
13. 관리자 화면 제작
14. 월간 리포트 생성
15. 웹채팅 연결
16. 실제 고객사 1곳 테스트
17. 카카오톡/이메일 연동
18. 보이스 AI는 마지막 단계에서 확장
```

---

# 12. 가장 먼저 해야 할 일

오늘 바로 해야 할 것은 이것이다.

```text
1. 새 폴더 생성: ai-cs-managed-service
2. GitHub repo 생성
3. docs 폴더 생성
4. 이 문서를 docs/github-based-roadmap.md로 저장
5. renaldiangsar/Customer-Support-RAG 클론
6. 로컬 실행
7. 질문 → 검색 → 답변 흐름을 노트에 기록
```

---

# 13. 첫 번째 목표

첫 번째 목표는 거창한 서비스 출시가 아니다.

```text
FAQ 등록
→ 고객 질문 입력
→ 관련 FAQ 검색
→ AI 답변 생성
→ 상담 로그 저장
→ 사용량 1건 기록
```

이 흐름이 완성되면, 그때부터 이 프로젝트는 단순 공부가 아니라 **사업화 가능한 AI CS 엔진**이 된다.
