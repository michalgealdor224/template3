package com.ashcollege.handlers;

import com.ashcollege.services.OpenAIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ChatWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private OpenAIService openAIService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String userMessage = message.getPayload();
        String aiResponse = openAIService.getChatResponse(userMessage);
        session.sendMessage(new TextMessage(aiResponse));
    }
}
