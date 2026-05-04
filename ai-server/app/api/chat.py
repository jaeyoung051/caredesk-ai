from fastapi import APIRouter, HTTPException
from pydantic import BaseModel

from app.services.rag_service import answer_with_rag

router = APIRouter(prefix="/ai", tags=["chat"])


class ChatRequest(BaseModel):
    companyId: int
    conversationId: int | None = None
    message: str


@router.post("/chat")
def chat(request: ChatRequest):
    try:
        result = answer_with_rag(
            company_id=request.companyId,
            question=request.message,
            top_k=3,
        )
        return result

    except ValueError as e:
        raise HTTPException(status_code=400, detail=str(e))

    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
