export type IndexStatus = 'PENDING' | 'INDEXING' | 'INDEXED' | 'FAILED'

export interface KnowledgeCreateRequest {
  title: string
  content: string
}

export interface KnowledgeCreateResponse {
  knowledgeId: number
  title: string
  indexStatus: IndexStatus
}

export interface KnowledgeResponse {
  knowledgeId: number
  title: string
  content: string
  indexStatus: IndexStatus
  createdAt: string
  updatedAt: string
}
