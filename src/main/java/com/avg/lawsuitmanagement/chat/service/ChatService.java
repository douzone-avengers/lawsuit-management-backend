package com.avg.lawsuitmanagement.chat.service;

import com.avg.lawsuitmanagement.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    
}
