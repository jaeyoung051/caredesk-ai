import json
from typing import Any

from openai import OpenAI

from app.config import settings
from app.prompts.cs_answer_prompt import CS_ANSWER_SYSTEM_PROMPT


def get_openai_client() -> OpenAI:
    if not settings.OPENAI_API_KEY:
        raise ValueError("OPENAI_API_KEY is not set. Please check your .env file.")

    return OpenAI(api_key=settings.OPENAI_API_KEY)


def build_user_prompt(context: str, question: str) -> str:
    return f"""
[참고 문서]
{context}

[고객 질문]
{question}

위 참고 문서를 기반으로 고객센터 답변을 생성해라.
반드시 JSON 형식으로만 응답해라.
"""


def generate_cs_answer(
    context: str,
    question: str,
) -> dict[str, Any]:
    client = get_openai_client()

    user_prompt = build_user_prompt(
        context=context,
        question=question,
    )

    response = client.chat.completions.create(
        model=settings.OPENAI_MODEL,
        messages=[
            {
                "role": "system",
                "content": CS_ANSWER_SYSTEM_PROMPT,
            },
            {
                "role": "user",
                "content": user_prompt,
            },
        ],
        response_format={"type": "json_object"},
        temperature=0.2,
    )

    content = response.choices[0].message.content

    try:
        parsed = json.loads(content)
    except json.JSONDecodeError:
        parsed = {
            "intent": "unknown",
            "answer": "죄송합니다. 현재 문의 내용을 정확히 확인하기 어려워 상담원 연결이 필요합니다.",
            "needHuman": True,
            "summary": "AI 응답 JSON 파싱 실패",
            "confidence": 0.0,
        }

    usage = response.usage

    parsed["usage"] = {
        "inputTokens": usage.prompt_tokens if usage else 0,
        "outputTokens": usage.completion_tokens if usage else 0,
    }

    return parsed
