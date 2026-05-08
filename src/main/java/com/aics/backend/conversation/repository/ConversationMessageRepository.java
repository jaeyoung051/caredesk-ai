package com.aics.backend.conversation.repository;

import com.aics.backend.conversation.domain.ConversationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationMessageRepository extends JpaRepository<ConversationMessage, Long> {
    List<ConversationMessage> findByConversationIdOrderByCreatedAtAsc(Long conversationId);
}
