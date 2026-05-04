from pathlib import Path
from typing import Any

import chromadb
from chromadb.utils import embedding_functions

CHROMA_PERSIST_DIR = "./chroma_db"
COLLECTION_NAME = "caredesk_knowledge"


def get_chroma_client():
    Path(CHROMA_PERSIST_DIR).mkdir(parents=True, exist_ok=True)
    return chromadb.PersistentClient(path=CHROMA_PERSIST_DIR)


def get_embedding_function():
    return embedding_functions.DefaultEmbeddingFunction()


def get_or_create_collection():
    client = get_chroma_client()

    return client.get_or_create_collection(
        name=COLLECTION_NAME,
        embedding_function=get_embedding_function(),
        metadata={"description": "CareDesk AI company knowledge chunks"},
    )


def store_chunks(
    company_id: int,
    document_name: str,
    chunks: list[str],
) -> dict[str, Any]:
    collection = get_or_create_collection()

    ids = []
    documents = []
    metadatas = []

    for index, chunk in enumerate(chunks):
        chunk_id = f"company_{company_id}_{document_name}_chunk_{index}"

        ids.append(chunk_id)
        documents.append(chunk)
        metadatas.append(
            {
                "companyId": company_id,
                "documentName": document_name,
                "chunkIndex": index,
            }
        )

    collection.upsert(
        ids=ids,
        documents=documents,
        metadatas=metadatas,
    )

    return {
        "companyId": company_id,
        "documentName": document_name,
        "storedChunkCount": len(chunks),
        "collectionName": COLLECTION_NAME,
    }


def search_relevant_chunks(
    company_id: int,
    query: str,
    top_k: int = 3,
) -> dict[str, Any]:
    collection = get_or_create_collection()

    results = collection.query(
        query_texts=[query],
        n_results=top_k,
        where={"companyId": company_id},
    )

    documents = results.get("documents", [[]])[0]
    metadatas = results.get("metadatas", [[]])[0]
    distances = results.get("distances", [[]])[0]

    matches = []

    for index, document in enumerate(documents):
        metadata = metadatas[index] if index < len(metadatas) else {}
        distance = distances[index] if index < len(distances) else None

        matches.append(
            {
                "content": document,
                "metadata": metadata,
                "distance": distance,
            }
        )

    return {
        "companyId": company_id,
        "query": query,
        "topK": top_k,
        "matchCount": len(matches),
        "matches": matches,
    }
