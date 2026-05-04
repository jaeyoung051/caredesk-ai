from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

from app.api.health import router as health_router
from app.api.knowledge import router as knowledge_router

app = FastAPI(
    title="CareDesk AI Server",
    description="RAG-based AI CS engine for CareDesk AI",
    version="0.1.0",
)

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

app.include_router(health_router)
app.include_router(knowledge_router)


@app.get("/")
def root():
    return {
        "service": "CareDesk AI Server",
        "status": "running",
    }
