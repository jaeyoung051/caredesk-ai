export type MessageRole = 'USER' | 'ASSISTANT' | 'AGENT' | 'SYSTEM'
export type ConversationStatus = 'OPEN' | 'WAITING_AGENT' | 'CLOSED'

export interface ChatRequest {
  conversationId: number | null
  message: string
}

export interface ChatResponse {
  conversationId: number
  answer: string
  intent: string
  confidence: number
  grounded: boolean
  needHandoff: boolean
  sources: unknown[]
}

export interface ConversationMessageResponse {
  messageId: number
  role: MessageRole
  content: string
  confidence: number | null
  grounded: boolean | null
  needHandoff: boolean | null
  createdAt: string
}

export interface ConversationDetailResponse {
  conversationId: number
  status: ConversationStatus
  messages: ConversationMessageResponse[]
}
