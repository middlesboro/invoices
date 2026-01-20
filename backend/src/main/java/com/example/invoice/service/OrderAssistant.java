package com.example.invoice.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface OrderAssistant {

    @SystemMessage("You are a helpful customer support assistant. You can check order statuses for users.")
    String chat(String userMessage);
}
