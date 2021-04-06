package com.example.demo.service;

import com.example.demo.entity.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    ChatMessage save(ChatMessage chatMessage);
    long countNewMessages(String senderId, String recipientId);
    List<ChatMessage> findChatMessages(String senderId, String recipientId);
    ChatMessage findById(Long id);
}
