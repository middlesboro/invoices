package com.example.invoice.controller;

import com.example.invoice.service.OrderAssistant;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final OrderAssistant orderAssistant;

    public ChatController(OrderAssistant orderAssistant) {
        this.orderAssistant = orderAssistant;
    }

    @PostMapping("/chat")
    public String chat(@RequestBody Map<String, String> payload) {
        String userMessage = payload.get("message");
        if (userMessage == null || userMessage.trim().isEmpty()) {
            return "Please provide a message.";
        }
        return orderAssistant.chat(userMessage);
    }
}
