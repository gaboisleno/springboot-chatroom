package com.gabo.ChatRoom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {
    Logger logger = LoggerFactory.getLogger(WebSocketController.class);
    private final SimpMessagingTemplate template;

    WebSocketEventListener(SimpMessagingTemplate template){
        this.template = template;
    }

    @EventListener
    private void handleSessionConnected(SessionConnectEvent event) {
        template.convertAndSend("/chat-response", "User conected");
    }

    @EventListener
    private void handleSessionDisconnected(SessionDisconnectEvent event) {
        template.convertAndSend("/chat-response", "User disconnected");
    }
}
