import { axiosInstance } from './axiosInstance'
import type {
  KnowledgeCreateRequest,
  KnowledgeCreateResponse,
  KnowledgeResponse,
} from '../types/knowledge'

const TENANT_ID = 1

export async function createKnowledge(
  payload: KnowledgeCreateRequest,
): Promise<KnowledgeCreateResponse> {
  return axiosInstance.post<KnowledgeCreateResponse>(
    `/api/tenants/${TENANT_ID}/knowledge`,
    payload,
  )
}

export async function getKnowledgeList(): Promise<KnowledgeResponse[]> {
  return axiosInstance.get<KnowledgeResponse[]>(
    `/api/tenants/${TENANT_ID}/knowledge`,
  )
}
