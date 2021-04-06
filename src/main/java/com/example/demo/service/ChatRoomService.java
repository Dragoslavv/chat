package com.example.demo.service;

import java.util.Optional;

public interface ChatRoomService {
    Optional<String> getChatId(String senderId, String recipientId, boolean createIfNotExist);
}
