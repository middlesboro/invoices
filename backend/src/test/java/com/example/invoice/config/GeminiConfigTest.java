package com.example.invoice.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.context.ApplicationContext;
import dev.langchain4j.model.chat.ChatLanguageModel;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = {
    "langchain4j.google-ai-gemini.chat-model.api-key=dummy",
    "langchain4j.google-ai-gemini.chat-model.model-name=gemini-pro"
})
@ActiveProfiles("gemini")
public class GeminiConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testGeminiBeanExists() {
        assertTrue(context.containsBean("geminiChatModel"));
        ChatLanguageModel model = context.getBean(ChatLanguageModel.class);
        assertNotNull(model);
    }
}
