import { useEffect, useState } from 'react'
import { createKnowledge, getKnowledgeList } from '../api/knowledgeApi'
import type { KnowledgeCreateRequest, KnowledgeResponse } from '../types/knowledge'

const statusText = (status: KnowledgeResponse['indexStatus']) => {
  if (status === 'PENDING') return '인덱싱 대기'
  if (status === 'INDEXING') return '인덱싱 중'
  if (status === 'INDEXED') return '인덱싱 완료'
  return '실패'
}

function KnowledgePage() {
  const [title, setTitle] = useState('')
  const [content, setContent] = useState('')
  const [knowledgeList, setKnowledgeList] = useState<KnowledgeResponse[]>([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)
  const [success, setSuccess] = useState<string | null>(null)

  const loadKnowledge = async () => {
    setLoading(true)
    setError(null)
    try {
      const list = await getKnowledgeList()
      setKnowledgeList(list)
    } catch (error) {
      setError('문서 목록 조회 중 오류가 발생했습니다.')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    loadKnowledge()
  }, [])

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    if (!title.trim() || !content.trim()) {
      setError('제목과 내용을 모두 입력해주세요.')
      return
    }

    setLoading(true)
    setError(null)
    setSuccess(null)

    try {
      const payload: KnowledgeCreateRequest = {
        title: title.trim(),
        content: content.trim(),
      }
      await createKnowledge(payload)
      setSuccess('지식 문서가 등록되었습니다.')
      setTitle('')
      setContent('')
      await loadKnowledge()
    } catch (error) {
      setError('문서 등록에 실패했습니다.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="page-card">
      <div className="page-header">
        <h2>Knowledge 관리</h2>
        <p className="hint-text">
          현재는 Spring Boot API로만 동작하며, PENDING 상태는 FastAPI 인덱싱 준비 전 정상 상태입니다.
        </p>
      </div>

      <section className="form-section">
        <form onSubmit={handleSubmit}>
          <label>
            문서 제목
            <input
              value={title}
              onChange={(event) => setTitle(event.target.value)}
              placeholder="환불 정책"
            />
          </label>
          <label>
            문서 내용
            <textarea
              value={content}
              onChange={(event) => setContent(event.target.value)}
              placeholder="결제 후 7일 이내 환불 가능합니다."
              rows={5}
            />
          </label>
          <div className="button-row">
            <button type="submit" disabled={loading}>
              등록하기
            </button>
            <button type="button" onClick={loadKnowledge} disabled={loading}>
              목록 새로고침
            </button>
          </div>
        </form>

        {error && <div className="error-box">{error}</div>}
        {success && <div className="success-box">{success}</div>}
      </section>

      <section className="list-section">
        <div className="section-title">등록된 Knowledge 문서</div>
        {loading && <div className="status-text">로딩 중...</div>}
        {!loading && knowledgeList.length === 0 && (
          <div className="status-text">등록된 문서가 없습니다.</div>
        )}

        <div className="card-grid">
          {knowledgeList.map((item) => (
            <article key={item.knowledgeId} className="info-card">
              <div className="card-row">
                <strong>{item.title}</strong>
                <span className="badge">{statusText(item.indexStatus)}</span>
              </div>
              <p>{item.content}</p>
              <div className="meta-row">
                <span>등록 시간: {new Date(item.createdAt).toLocaleString()}</span>
                <span>수정 시간: {new Date(item.updatedAt).toLocaleString()}</span>
              </div>
            </article>
          ))}
        </div>
      </section>
    </div>
  )
}

export default KnowledgePage
