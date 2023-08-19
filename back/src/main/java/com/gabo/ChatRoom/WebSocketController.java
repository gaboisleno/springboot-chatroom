package com.gabo.ChatRoom;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    Logger logger = LoggerFactory.getLogger(WebSocketController.class);
    
    private final SimpMessagingTemplate template;

    @Autowired
    WebSocketController(SimpMessagingTemplate template){
        this.template = template;
    }

    @MessageMapping("/chat-room")
    public void onReceivedMessage(String message) {
        this.template.convertAndSend("/chat-response", new SimpleDateFormat("HH:mm").format(new Date()) + " - " + message);
    }

}
