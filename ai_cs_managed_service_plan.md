# AI CS Managed Service 사업 기획 및 코드 기준 설계 문서

## 1. 사업 한 줄 정의

**웹서비스를 파는 것이 아니라, 기업과 소상공인의 고객 문의를 AI가 대신 처리해주고 월별 CS 사용량에 따라 과금하는 AI CS 운영 서비스**

---

## 2. 핵심 방향성

처음부터 완성형 웹 SaaS를 만들지 않는다.

초기 방향은 아래 구조로 간다.

```text
랜딩페이지로 고객 컨택
→ 고객사 CS 자료 수집
→ AI 응대 시나리오 세팅
→ 카카오톡 / 웹채팅 / 이메일 / 전화 등에 연결
→ 상담 처리량 측정
→ 월별 사용량 기반 과금
```

고객 입장에서는 복잡한 프로그램을 쓰는 것이 아니라,

> “우리 회사 고객센터 업무를 AI 직원이 대신 처리해준다.”

라고 느끼게 만들어야 한다.

---

## 3. 문제 정의

### 고객사가 겪는 문제

많은 사업자는 고객 문의를 직접 처리하느라 시간을 낭비한다.

대표적인 반복 문의는 다음과 같다.

```text
가격이 얼마인가요?
예약 가능한가요?
영업시간이 어떻게 되나요?
배송은 언제 오나요?
환불 가능한가요?
상담 가능한 시간은 언제인가요?
주차 가능한가요?
```

이런 문의는 단순하지만 계속 반복된다.

사업자 입장에서는 다음 문제가 발생한다.

```text
응답이 늦어져 고객이 이탈한다.
상담 직원의 피로도가 높아진다.
같은 답변을 계속 반복해야 한다.
중요한 문의와 단순 문의가 섞인다.
상담 내역이 체계적으로 관리되지 않는다.
업무 시간 외 문의에 대응하기 어렵다.
```

---

## 4. 해결책

### 서비스 개념

**AI가 반복적인 고객 문의를 자동 처리하고, 복잡하거나 위험한 문의는 사람에게 넘기는 AI CS 운영 서비스**

핵심은 상담원을 완전히 대체하는 것이 아니다.

```text
단순 문의 → AI 자동 처리
복잡한 문의 → 사람에게 이관
위험한 문의 → 자동 응답 차단 후 관리자 알림
상담 기록 → 자동 저장 및 요약
월별 통계 → 리포트 제공
```

---

## 5. 서비스 포지셔닝

### 잘못된 포지셔닝

```text
AI 챗봇 솔루션
LLM 기반 고객센터
전 산업 AI 상담 플랫폼
```

이런 표현은 너무 추상적이다.

### 올바른 포지셔닝

```text
반복 고객문의를 AI가 대신 처리해드립니다.

카카오톡, 웹채팅, 이메일, 전화 문의를 자동 응대하고
월별 CS 사용량에 따라 과금하는 AI CS 운영 서비스입니다.
```

고객은 기술 자체가 아니라 **업무 절감 효과**에 돈을 낸다.

---

## 6. 서비스 구조

초기에는 고객용 웹 대시보드를 크게 만들 필요 없다.

초기에는 다음 정도만 있으면 된다.

```text
1. 소개용 랜딩페이지
2. 상담 신청 폼
3. 내부 관리자 페이지
4. 고객사별 FAQ/정책 등록
5. AI 응답 테스트 기능
6. 상담 로그 저장
7. 월별 사용량 집계
8. 리포트 생성
```

고객은 직접 복잡한 설정을 하는 것이 아니라, 초기에는 운영자가 직접 세팅해주는 방식으로 간다.

---

## 7. 실제 운영 흐름

```text
1. 고객사가 상담 신청
2. 업종과 CS 유형 파악
3. FAQ, 가격표, 환불규정, 예약규정, 운영시간 등 자료 수집
4. 고객사별 AI 응대 시나리오 세팅
5. 테스트 운영
6. 실제 문의 채널 연결
7. AI가 문의 처리
8. 위험하거나 애매한 문의는 사람에게 이관
9. 월별 상담량 집계
10. 사용량 기반 청구
11. 월간 상담 리포트 제공
```

---

## 8. 수익 모델

가장 좋은 방식은 **기본료 + 사용량 과금**이다.

순수 사용량 과금만 하면 초기 매출이 불안정하고, 기본료만 받으면 많이 쓰는 고객에게 손해가 날 수 있다.

### 추천 과금 구조

| 플랜 | 월 기본료 | 포함 상담량 | 초과 과금 |
|---|---:|---:|---:|
| Starter | 49,000원 | 월 500건 텍스트 상담 | 건당 추가 과금 |
| Basic | 99,000원 | 월 1,500건 텍스트 상담 | 건당 추가 과금 |
| Pro | 199,000원 | 월 5,000건 텍스트 상담 | 건당 추가 과금 |
| Voice Add-on | 별도 | 통화 분 단위 | 분당 과금 |

초기에는 가격을 너무 낮게 잡지 않는다.

이 서비스는 단순 챗봇이 아니라 **CS 인건비와 시간 절감 서비스**로 팔아야 한다.

---

## 9. 텍스트 CS와 보이스 CS 구분

### 1단계: 텍스트 CS

초기에는 텍스트 상담부터 시작한다.

대상 채널은 다음과 같다.

```text
카카오톡 채널
웹채팅
이메일
인스타 DM
네이버 톡톡
```

과금 기준은 다음과 같이 잡을 수 있다.

```text
상담 건수
AI 응답 건수
메시지 건수
상담 요약 건수
```

### 2단계: 보이스 CS

보이스 CS는 난이도가 높기 때문에 나중에 붙인다.

필요한 기술은 다음과 같다.

```text
STT: 음성 → 텍스트
LLM: 의도 파악 및 답변 생성
TTS: 텍스트 → 음성
실시간 음성 응답
통화 녹취
상담 요약
상담원 이관
전화망 연동
```

따라서 순서는 이렇게 가야 한다.

```text
텍스트 CS 자동화
→ 상담 요약/분류
→ 웹 음성 상담
→ 전화 AI 상담
```

---

## 10. 초기 타겟 시장

전 산업으로 확장하는 것이 최종 목표지만, 처음부터 전 산업을 대상으로 하면 안 된다.

처음에는 문의가 반복적이고 규제가 낮은 업종부터 들어간다.

### 1순위: 쇼핑몰

반복 문의가 많고 자동화하기 쉽다.

```text
배송 문의
교환 문의
환불 문의
주문 취소
상품 재고
사이즈 문의
쿠폰 문의
```

### 2순위: 예약업

미용실, 네일샵, 음식점, 카페, 스튜디오 등이 대상이다.

```text
예약 가능 여부
가격 문의
영업시간 문의
위치 문의
주차 문의
예약 변경
예약 취소
```

### 3순위: 학원/교육업

상담 문의가 많고 정형화하기 좋다.

```text
수강료 문의
시간표 문의
레벨테스트 문의
상담 예약
환불 규정
교재 안내
```

초기 추천은 **쇼핑몰 CS** 또는 **예약업 CS**다.

---

## 11. 제품의 핵심 기능

### MVP 기능

첫 번째 버전은 아래 기능만 있으면 된다.

```text
1. 고객사 등록
2. FAQ/정책 문서 등록
3. 고객 질문 입력 테스트
4. AI 자동 답변 생성
5. 문의 유형 분류
6. 상담원 이관 여부 판단
7. 상담 로그 저장
8. 관리자 메모
9. 월별 사용량 집계
10. 월간 리포트 생성
```

처음부터 완성도 높은 웹서비스를 만들 필요 없다.

중요한 것은 고객사의 문의를 실제로 줄여주는 것이다.

---

## 12. AI 응답 구조

AI는 단순히 답변만 하면 안 된다.

아래처럼 구조화된 판단을 해야 한다.

```json
{
  "intent": "refund_inquiry",
  "urgency": "medium",
  "customer_sentiment": "neutral",
  "can_ai_answer": true,
  "need_human": false,
  "answer": "환불은 상품 수령 후 7일 이내 신청 가능합니다.",
  "summary": "고객이 환불 가능 여부를 문의함"
}
```

이렇게 해야 백엔드에서 자동으로 처리할 수 있다.

예를 들어:

```text
need_human = true → 관리자 알림
intent = reservation → 예약 프로세스 실행
intent = refund → 환불 정책 조회
sentiment = angry → 사람 상담원 우선 연결
```

---

# 13. 코드 기준 기술 구조

## 13.1 기술 구조 선정 기준

초기 기술 스택은 개발자 개인의 숙련도보다, **AI CS 서비스를 안정적으로 운영하기 위한 코드 구조와 확장성**을 기준으로 선정한다.

이 서비스는 단순 웹사이트가 아니라 다음 기능을 처리해야 한다.

```text
고객 문의 수신
고객사별 지식 검색
AI 답변 생성
자동 응답 여부 판단
상담원 이관
상담 로그 저장
사용량 집계
월별 리포트 생성
과금 기준 산정
```

따라서 핵심은 고객 문의 수신, AI 응답 생성, 상담원 이관, 사용량 집계, 월별 리포트 생성이 독립적으로 동작할 수 있도록 모듈을 분리하는 것이다.

---

## 13.2 Frontend

```text
Landing Page
Admin Dashboard
Internal Operation Console
```

### 역할

프론트엔드는 고객이 서비스를 직접 사용하는 복잡한 SaaS 화면이 아니라, 초기에는 다음 역할에 집중한다.

```text
서비스 소개
상담 신청
고객사 등록
FAQ/정책 문서 등록
AI 답변 테스트
상담 로그 확인
월별 사용량 확인
```

### 권장 구조

```text
/frontend
  /landing
  /admin
  /components
  /api
  /types
```

---

## 13.3 Backend API Server

```text
REST API Server
Authentication
Business Logic
Customer Management
Conversation Management
Usage Metering
```

### 역할

백엔드는 전체 서비스의 중심이다.

```text
고객사 관리
FAQ/문서 관리
상담 요청 처리
AI 응답 요청
상담 로그 저장
사용량 집계
관리자 기능 제공
```

### 주요 모듈

```text
/backend
  /auth
  /companies
  /knowledge
  /conversations
  /messages
  /ai
  /handoff
  /usage
  /billing
  /reports
```

---

## 13.4 AI Orchestration Layer

```text
LLM Gateway
Prompt Manager
RAG Pipeline
Tool Calling
Safety Guard
Structured Output Parser
```

### 역할

AI 기능은 백엔드 내부에 단순히 API 호출 코드로 박아두면 안 된다.  
별도의 AI 처리 계층으로 분리해야 한다.

이 계층은 다음 작업을 담당한다.

```text
문의 유형 분류
고객사 지식 검색
AI 답변 생성
자동 응답 가능 여부 판단
상담원 이관 여부 판단
답변 금지 주제 필터링
JSON 형태 결과 반환
```

### 예시 출력

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

## 13.5 Database

```text
Relational DB
Vector DB
Object Storage
```

### 관계형 DB

관계형 DB는 서비스 운영 데이터를 저장한다.

```text
companies
users
channels
conversations
messages
ai_responses
handoff_requests
usage_records
billing_records
reports
```

### Vector DB

Vector DB는 고객사별 FAQ, 환불정책, 배송정책, 가격표, 예약규정 등을 검색하기 위해 사용한다.

```text
company_knowledge_chunks
embedding_vector
source_document
metadata
```

### Object Storage

문서 파일, 상담 첨부파일, 녹취 파일은 DB에 직접 저장하지 않고 스토리지에 저장한다.

```text
PDF
Excel
이미지
녹취 파일
상담 첨부파일
```

---

## 13.6 Messaging Adapter Layer

```text
KakaoTalk Adapter
Web Chat Adapter
Email Adapter
Instagram DM Adapter
Voice Adapter
```

### 역할

각 채널마다 API 방식이 다르기 때문에, 채널 연동 코드를 핵심 비즈니스 로직과 분리한다.

```text
외부 채널에서 메시지 수신
내부 표준 메시지 형식으로 변환
AI 처리 요청
응답을 다시 외부 채널 형식으로 변환
```

### 내부 표준 메시지 예시

```json
{
  "companyId": 1,
  "channel": "KAKAO",
  "externalUserId": "customer_123",
  "messageType": "TEXT",
  "content": "환불 가능한가요?",
  "receivedAt": "2026-05-01T22:00:00"
}
```

이렇게 하면 나중에 카카오톡, 웹채팅, 이메일, 전화 채널을 추가해도 핵심 AI 로직은 그대로 유지할 수 있다.

---

## 13.7 Usage Metering & Billing Layer

```text
Usage Counter
Plan Limit Checker
Billing Calculator
Monthly Report Generator
```

### 역할

이 서비스는 사용량 기반 과금이 핵심이므로, 사용량 집계 코드는 처음부터 분리해야 한다.

측정 기준은 다음과 같이 잡을 수 있다.

```text
AI 응답 건수
상담 세션 수
메시지 수
상담원 이관 건수
상담 요약 건수
보이스 통화 시간
```

### 예시 테이블

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

---

## 13.8 Human Handoff System

```text
Escalation Rule
Admin Alert
Manual Reply
Conversation Status
```

### 역할

AI가 모든 문의를 처리하면 안 된다.  
위험하거나 애매한 문의는 사람에게 넘겨야 한다.

이관 조건 예시는 다음과 같다.

```text
고객이 화난 경우
환불/보상 분쟁
법적 문제
의료/금융 등 민감한 질문
AI가 답변 근거를 찾지 못한 경우
고객이 상담원을 직접 요청한 경우
```

상담 상태는 다음처럼 관리한다.

```text
AI_HANDLED
NEED_HUMAN
HUMAN_IN_PROGRESS
RESOLVED
FAILED
```

---

## 13.9 Report System

```text
Monthly CS Report
Usage Report
FAQ Improvement Report
Automation Rate Report
```

### 역할

고객이 돈을 계속 내게 하려면 “얼마나 도움이 됐는지”를 보여줘야 한다.

월간 리포트에는 다음 데이터가 들어간다.

```text
총 상담 수
AI 자동 처리 수
사람 이관 수
자동 처리율
가장 많은 문의 유형
절감 예상 시간
개선이 필요한 FAQ
월 사용량
예상 청구 금액
```

---

# 14. 기본 아키텍처

```text
[Customer Channel]
카카오톡 / 웹채팅 / 이메일 / 전화
        ↓
[Messaging Adapter]
채널별 메시지 표준화
        ↓
[Conversation API]
상담 세션 생성 및 메시지 저장
        ↓
[AI Orchestration Layer]
의도 분류 + RAG 검색 + 답변 생성 + 이관 판단
        ↓
[Response Router]
자동 응답 or 사람 상담원 이관
        ↓
[Usage Metering]
AI 응답/상담 건수/통화 시간 집계
        ↓
[Report & Billing]
월간 리포트 + 과금 산정
```

---

# 15. 코드 기준 추천 기술 스택

| 영역 | 권장 선택 |
|---|---|
| Frontend | React / Next.js |
| Backend API | Spring Boot 또는 Node.js NestJS |
| DB | PostgreSQL 또는 MySQL |
| Vector DB | Qdrant / Pinecone / Chroma |
| Cache / Queue | Redis |
| Background Job | BullMQ / Celery / Spring Batch / Quartz |
| AI API | OpenAI API / Anthropic / Local LLM 확장 가능 구조 |
| File Storage | S3 호환 스토리지 |
| Auth | JWT + Role Based Access Control |
| Logging | Structured Log |
| Monitoring | Prometheus / Grafana / Sentry |
| Deployment | Docker + Cloud Run / ECS / EC2 |
| Billing | Toss Payments / Stripe / 수동 청구 MVP |

---

# 16. 초기 MVP 기준 최소 구현 범위

처음부터 모든 걸 만들지 않고, 코드 기준으로 최소 구현 범위는 이렇게 잡는다.

```text
1. 고객사 등록
2. FAQ/정책 문서 등록
3. 고객 질문 입력 API
4. RAG 기반 답변 생성
5. AI 응답 JSON 구조화
6. 상담 로그 저장
7. 사람 이관 여부 판단
8. 사용량 집계
9. 월간 리포트 생성
10. 내부 관리자 화면
```

초기에는 카카오톡, 전화, 결제 자동화까지 전부 구현하지 않아도 된다.

먼저 내부 테스트 채널을 만들고, AI CS 처리 흐름이 제대로 작동하는지 검증한다.

```text
웹 테스트 채널
→ 실제 고객사 FAQ 등록
→ AI 응답 테스트
→ 상담 로그 저장
→ 월간 리포트 생성
→ 이후 카카오톡/웹채팅/전화 연동
```

---

# 17. 반드시 공부해야 할 것

## 17.1 CS 업무 지식

```text
FAQ 응대
상담원 이관
클레임 처리
환불/교환 프로세스
예약 변경 프로세스
SLA
CSAT
NPS
AHT
FCR
상담 로그 관리
```

## 17.2 LLM 기술

```text
Prompt Engineering
RAG
Embedding
Vector DB
Tool Calling
Structured Output
Fine-tuning
AI Evaluation
Hallucination 방지
Human-in-the-loop
```

## 17.3 AI Voice 기술

```text
STT
TTS
Realtime Voice Agent
전화 연동
녹취
상담 요약
Barge-in
Latency 최적화
```

## 17.4 보안/법률

```text
개인정보보호법
개인정보 수집 동의
상담 녹취 고지
AI 응대 고지
데이터 보관 기간
민감정보 처리 제한
고객사별 데이터 분리
로그 암호화
삭제 요청 대응
```

---

# 18. 차별화 전략

경쟁 서비스와 정면승부하면 안 된다.

대기업용 AI 고객센터 솔루션은 이미 많다.

따라서 아래처럼 포지셔닝한다.

```text
대기업용 복잡한 솔루션 ❌
중소사업자용 빠른 도입형 AI CS 운영 서비스 ⭕

범용 챗봇 ❌
산업별 CS 템플릿 ⭕

완전 자동화 ❌
AI 자동 처리 + 사람 이관 ⭕

웹 SaaS 판매 ❌
CS 처리량 기반 운영 서비스 ⭕
```

---

# 19. 사업 확장 전략

## 19.1 1단계: 운영대행형

초기에는 직접 세팅한다.

```text
고객사 자료 수집
FAQ 정리
AI 응답 테스트
월간 리포트 제공
```

이 단계의 목표는 개발 완성도가 아니라 **첫 유료 고객 확보**다.

## 19.2 2단계: 반자동화

반복되는 설정을 관리자 페이지로 만든다.

```text
FAQ 등록 자동화
상담 로그 자동 분류
리포트 자동 생성
사용량 자동 집계
```

## 19.3 3단계: SaaS화

고객사가 직접 설정할 수 있는 대시보드를 제공한다.

```text
고객사 로그인
FAQ 직접 수정
상담 통계 확인
요금제 관리
결제 자동화
```

## 19.4 4단계: 보이스 확장

텍스트 상담이 안정화되면 보이스를 붙인다.

```text
웹 음성 상담
전화 AI 상담
통화 요약
상담원 연결
분당 과금
```

---

# 20. 월간 리포트 예시

고객에게 매월 이런 리포트를 제공하면 돈 받을 명분이 생긴다.

```text
월간 AI CS 리포트

총 상담 수: 1,240건
AI 자동 처리: 860건
사람 이관: 380건
자동 처리율: 69.3%

가장 많은 문의:
1. 배송 문의 420건
2. 환불 문의 270건
3. 상품 문의 180건

절감 예상 시간:
총 43시간

개선 필요 항목:
1. 환불 정책 FAQ 보완 필요
2. 배송 지연 관련 응답 추가 필요
3. 사이즈 문의 답변 정확도 개선 필요
```

이 리포트가 중요하다.

고객은 “AI가 몇 번 답했는지”보다 **얼마나 업무가 줄었는지**를 보고 싶어하기 때문이다.

---

# 21. 최종 사업 문장

> **AI CS Managed Service는 카카오톡, 웹채팅, 이메일, 전화 등 다양한 고객 문의 채널에서 반복적인 문의를 AI가 자동 처리하고, 복잡한 문의는 사람에게 넘겨주는 사용량 기반 고객응대 운영 서비스입니다. 고객사는 별도의 복잡한 시스템을 구축하지 않고도 AI 상담 인력을 도입할 수 있으며, 월별 상담 처리량에 따라 합리적으로 비용을 지불합니다.**

---

# 22. 지금 당장 해야 할 일

## 22.1 1단계: 랜딩페이지 제작

내용은 단순하게 간다.

```text
반복 고객문의를 AI가 대신 처리해드립니다.
카톡/웹채팅/이메일 문의 자동응대
월 사용량 기반 과금
무료 진단 신청
```

## 22.2 2단계: 내부 관리자 MVP 제작

```text
고객사 등록
FAQ 등록
질문 테스트
AI 답변 생성
로그 저장
사용량 집계
```

## 22.3 3단계: 초기 타겟 1개 선정

추천은 둘 중 하나다.

```text
쇼핑몰 CS
예약업 CS
```

## 22.4 4단계: 실제 고객에게 제안

```text
현재 고객문의 중 반복되는 질문을 AI가 대신 처리해드립니다.
초기 세팅은 저희가 해드리고, 월 사용량 기준으로 비용을 받습니다.
먼저 2주간 테스트 운영해보실 수 있습니다.
```

---

# 23. 최종 결론

방향은 다음과 같이 정리된다.

```text
웹은 고객을 데려오는 입구
AI는 상담을 처리하는 엔진
리포트는 고객이 돈을 내는 근거
사용량 과금은 수익 모델
산업별 템플릿은 확장 전략
보이스 AI는 고도화 단계
```

가장 중요한 한 문장은 이거다.

> **웹서비스를 만드는 것이 목표가 아니라, 고객사의 반복 CS 업무를 AI가 대신 처리하고 그 처리량만큼 돈을 받는 구조를 만드는 것이 목표다.**

---

# 24. 개발 우선순위 요약

## Phase 1: 내부 MVP

```text
고객사 등록
FAQ 등록
질문 테스트
AI 답변 생성
로그 저장
사용량 집계
간단 리포트 생성
```

## Phase 2: 실제 채널 연결

```text
웹채팅 연동
카카오톡 채널 연동
이메일 연동
상담원 이관 알림
```

## Phase 3: 과금 및 운영 자동화

```text
월별 사용량 산정
청구서 생성
요금제 관리
고객사별 리포트 자동 발송
```

## Phase 4: 보이스 AI 확장

```text
STT/TTS 적용
웹 음성 상담
전화 AI 상담
통화 요약
분당 과금
```

---

# 25. 코드 구조 예시

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
    api-spec.md
    architecture.md
    deployment.md
```

---

# 26. 첫 번째 구현 목표

처음 구현할 목표는 다음 하나다.

> **고객사가 FAQ를 등록하면, 고객 질문에 대해 AI가 FAQ를 참고해 답변하고, 상담 로그와 사용량을 저장하는 내부 운영형 MVP**

이 MVP가 완성되면 다음을 검증할 수 있다.

```text
AI가 정확히 답변하는가?
어떤 문의가 자주 들어오는가?
사람 이관 기준이 적절한가?
사용량 기반 과금이 가능한가?
월간 리포트로 고객에게 가치를 보여줄 수 있는가?
```

---

# 27. 향후 문서화해야 할 추가 문서

이 문서 이후에는 다음 문서를 따로 작성하는 것이 좋다.

```text
1. API 명세서
2. DB ERD
3. 관리자 화면 정의서
4. AI 프롬프트 설계서
5. RAG 파이프라인 설계서
6. 사용량 과금 정책서
7. 보안/개인정보 처리 정책
8. 고객 제안서
9. 랜딩페이지 문구
10. MVP 개발 일정표
```
