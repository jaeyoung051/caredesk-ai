from typing import Any

from app.services.vector_store import search_relevant_chunks
from app.services.llm_service import generate_cs_answer


def build_context_from_matches(matches: list[dict[str, Any]]) -> str:
    context_parts = []

    for index, match in enumerate(matches):
        content = match.get("content", "")
        metadata = match.get("metadata", {})

        document_name = metadata.get("documentName", "unknown")
        chunk_index = metadata.get("chunkIndex", "unknown")

        context_parts.append(
            f"[문서 {index + 1}] documentName={document_name}, chunkIndex={chunk_index}\n{content}"
        )

    return "\n\n".join(context_parts)


def build_sources_from_matches(matches: list[dict[str, Any]]) -> list[dict[str, Any]]:
    sources = []

    for match in matches:
        metadata = match.get("metadata", {})

        sources.append(
            {
                "documentName": metadata.get("documentName"),
                "chunkIndex": metadata.get("chunkIndex"),
            }
        )

    return sources


def answer_with_rag(
    company_id: int,
    question: str,
    top_k: int = 3,
) -> dict[str, Any]:
    search_result = search_relevant_chunks(
        company_id=company_id,
        query=question,
        top_k=top_k,
    )

    matches = search_result.get("matches", [])

    if not matches:
        return {
            "intent": "unknown",
            "answer": "죄송합니다. 등록된 문서에서 답변 근거를 찾지 못했습니다. 상담원 연결이 필요합니다.",
            "needHuman": True,
            "summary": "관련 문서 검색 결과 없음",
            "confidence": 0.0,
            "sources": [],
            "usage": {
                "inputTokens": 0,
                "outputTokens": 0,
            },
        }

    context = build_context_from_matches(matches)

    ai_result = generate_cs_answer(
        context=context,
        question=question,
    )

    return {
        "intent": ai_result.get("intent", "unknown"),
        "answer": ai_result.get(
            "answer",
            "죄송합니다. 답변 생성 중 문제가 발생했습니다.",
        ),
        "needHuman": ai_result.get("needHuman", True),
        "summary": ai_result.get("summary", ""),
        "confidence": ai_result.get("confidence", 0.0),
        "sources": build_sources_from_matches(matches),
        "usage": ai_result.get(
            "usage",
            {
                "inputTokens": 0,
                "outputTokens": 0,
            },
        ),
    }
