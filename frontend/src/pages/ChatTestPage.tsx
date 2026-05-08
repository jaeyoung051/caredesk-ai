import { useState } from 'react'
import { sendChatMessage, getConversationDetail } from '../api/conversationApi'
import type { ChatRequest } from '../types/conversation'

type ChatEntry = {
  id: number
  role: 'USER' | 'ASSISTANT'
  content: string
  time: string
}

function ChatTestPage() {
  const [conversationId, setConversationId] = useState<number | null>(null)
  const [message, setMessage] = useState('')
  const [chatEntries, setChatEntries] = useState<ChatEntry[]>([])
  const [detailMessages, setDetailMessages] = useState<ChatEntry[]>([])
  const [lastResponse, setLastResponse] = useState<{
    intent: string
    confidence: number
    grounded: boolean
    needHandoff: boolean
  } | null>(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const addChatEntry = (entry: ChatEntry) => {
    setChatEntries((previous) => [...previous, entry])
  }

  const sendMessage = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    if (!message.trim()) {
      setError('질문을 입력해주세요.')
      return
    }

    setError(null)
    setLoading(true)

    const payload: ChatRequest = {
      conversationId,
      message: message.trim(),
    }

    try {
      addChatEntry({
        id: Date.now(),
        role: 'USER',
        content: payload.message,
        time: new Date().toLocaleTimeString(),
      })

      const response = await sendChatMessage(payload)
      setConversationId(response.conversationId)
      setLastResponse({
        intent: response.intent,
        confidence: response.confidence,
        grounded: response.grounded,
        needHandoff: response.needHandoff,
      })
      addChatEntry({
        id: Date.now() + 1,
        role: 'ASSISTANT',
        content: response.answer,
        time: new Date().toLocaleTimeString(),
      })
      setMessage('')
    } catch (error) {
      setError('대화 전송 중 오류가 발생했습니다.')
    } finally {
      setLoading(false)
    }
  }

  const loadConversationDetail = async () => {
    if (!conversationId) {
      setError('먼저 대화를 시작한 후 상세 조회를 이용하세요.')
      return
    }

    setError(null)
    setLoading(true)

    try {
      const response = await getConversationDetail(conversationId)
      const messages = response.messages
        .slice()
        .sort(
          (a, b) =>
            new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime(),
        )
        .map((message) => {
          const role: 'ASSISTANT' | 'USER' =
            message.role === 'ASSISTANT' ? 'ASSISTANT' : 'USER'
          return {
            id: message.messageId,
            role,
            content: message.content,
            time: new Date(message.createdAt).toLocaleTimeString(),
          }
        })
      setDetailMessages(messages)
    } catch (error) {
      setError('상담방 상세 조회 중 오류가 발생했습니다.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="page-card">
      <div className="page-header">
        <h2>AI 상담 테스트</h2>
        <p className="hint-text">
          현재는 Spring Boot API만 호출하며, Mock AI 응답 확인용 테스트 화면입니다.
        </p>
      </div>

      <section className="form-section">
        <form onSubmit={sendMessage}>
          <label>
            질문 입력
            <input
              value={message}
              onChange={(event) => setMessage(event.target.value)}
              placeholder="환불은 언제까지 가능한가요?"
            />
          </label>
          <div className="button-row">
            <button type="submit" disabled={loading}>
              전송
            </button>
            <button
              type="button"
              onClick={loadConversationDetail}
              disabled={loading || !conversationId}
            >
              상담방 상세 조회
            </button>
          </div>
        </form>

        {error && <div className="error-box">{error}</div>}
      </section>

      <section className="list-section">
        <div className="section-title">대화 흐름</div>
        <div className="status-text">conversationId: {conversationId ?? '없음'}</div>
        <div className="chat-list">
          {chatEntries.map((entry) => (
            <div
              key={entry.id}
              className={`chat-bubble ${entry.role === 'USER' ? 'user-bubble' : 'assistant-bubble'}`}
            >
              <div className="chat-role">{entry.role}</div>
              <p>{entry.content}</p>
              <div className="chat-time">{entry.time}</div>
            </div>
          ))}
          {chatEntries.length === 0 && (
            <div className="status-text">첫 질문을 입력하고 전송해주세요.</div>
          )}
        </div>
      </section>

      {lastResponse && (
        <section className="meta-section">
          <div className="section-title">마지막 응답 메타</div>
          <div className="meta-grid">
            <div>intent: {lastResponse.intent}</div>
            <div>confidence: {lastResponse.confidence}</div>
            <div>grounded: {String(lastResponse.grounded)}</div>
            <div>needHandoff: {String(lastResponse.needHandoff)}</div>
          </div>
        </section>
      )}

      <section className="list-section">
        <div className="section-title">상담방 상세 메시지</div>
        {detailMessages.length === 0 && (
          <div className="status-text">상세 조회 버튼을 눌러 메시지 히스토리를 확인하세요.</div>
        )}
        <div className="detail-list">
          {detailMessages.map((messageItem) => (
            <div key={messageItem.id} className="detail-row">
              <span className="detail-role">{messageItem.role}</span>
              <span className="detail-time">{messageItem.time}</span>
              <p>{messageItem.content}</p>
            </div>
          ))}
        </div>
      </section>
    </div>
  )
}

export default ChatTestPage
