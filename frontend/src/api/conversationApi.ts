import { axiosInstance } from './axiosInstance'
import type {
  ChatRequest,
  ChatResponse,
  ConversationDetailResponse,
} from '../types/conversation'

const TENANT_ID = 1

export async function sendChatMessage(
  payload: ChatRequest,
): Promise<ChatResponse> {
  return axiosInstance.post<ChatResponse>(
    `/api/tenants/${TENANT_ID}/conversations/chat`,
    payload,
  )
}

export async function getConversationDetail(
  conversationId: number,
): Promise<ConversationDetailResponse> {
  return axiosInstance.get<ConversationDetailResponse>(
    `/api/tenants/${TENANT_ID}/conversations/${conversationId}`,
  )
}
