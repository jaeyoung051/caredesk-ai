from fastapi import APIRouter, HTTPException

from app.services.document_loader import load_txt_file
from app.services.text_splitter import split_text_to_chunks

router = APIRouter(prefix="/ai/knowledge", tags=["knowledge"])


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
