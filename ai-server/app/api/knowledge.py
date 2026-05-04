from fastapi import APIRouter, HTTPException
from pydantic import BaseModel

from app.services.document_loader import load_txt_file
from app.services.text_splitter import split_text_to_chunks
from app.services.vector_store import store_chunks, search_relevant_chunks

router = APIRouter(prefix="/ai/knowledge", tags=["knowledge"])


class TestSearchRequest(BaseModel):
    companyId: int = 1
    query: str
    topK: int = 3


@router.post("/test-load")
def test_load_knowledge():
    file_path = "samples/refund_policy.txt"

    try:
        text = load_txt_file(file_path)
        chunks = split_text_to_chunks(text)

        return {
            "fileName": "refund_policy.txt",
            "textLength": len(text),
            "chunkCount": len(chunks),
            "chunks": [
                {
                    "chunkIndex": index,
                    "content": chunk,
                    "length": len(chunk),
                }
                for index, chunk in enumerate(chunks)
            ],
        }

    except FileNotFoundError as e:
        raise HTTPException(status_code=404, detail=str(e))

    except ValueError as e:
        raise HTTPException(status_code=400, detail=str(e))


@router.post("/test-store")
def test_store_knowledge():
    file_path = "samples/refund_policy.txt"
    company_id = 1
    document_name = "refund_policy.txt"

    try:
        text = load_txt_file(file_path)
        chunks = split_text_to_chunks(text)
        result = store_chunks(
            company_id=company_id,
            document_name=document_name,
            chunks=chunks,
        )

        return {
            "status": "stored",
            "fileName": document_name,
            "chunkCount": len(chunks),
            "result": result,
        }

    except FileNotFoundError as e:
        raise HTTPException(status_code=404, detail=str(e))

    except ValueError as e:
        raise HTTPException(status_code=400, detail=str(e))


@router.post("/test-search")
def test_search_knowledge(request: TestSearchRequest):
    try:
        result = search_relevant_chunks(
            company_id=request.companyId,
            query=request.query,
            top_k=request.topK,
        )

        return result

    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
