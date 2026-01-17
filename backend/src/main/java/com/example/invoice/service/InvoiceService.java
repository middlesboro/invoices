package com.example.invoice.service;

import com.example.invoice.model.Invoice;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class InvoiceService {

    private final InvoiceExtractor invoiceExtractor;
    private final Tika tika;

    // We can inject the model based on configuration or create the service dynamically if needed.
    // However, @AiService integration in Spring Boot Starter usually auto-wires the primary ChatLanguageModel.
    // Since we need to switch between Mistral (Ollama) and Gemini, we might need a way to select the model dynamically.
    // The Spring Boot starter will auto-configure one if properties are present.
    // If both are present, we need to handle it.
    // For simplicity, let's assume one is active via profiles or we use a factory.

    // But the requirement says "add option to configure gemini model instead of mistral".
    // This implies a startup configuration or a per-request configuration?
    // "add option to configure" likely means application properties.

    // If we want to switch at runtime (per request), we'd need multiple ChatLanguageModels.
    // If we just want to configure the app to use one or the other, Spring profiles or conditional beans work.

    // Let's assume the user wants to switch via config file.
    // The starter will pick up properties.

    // However, if we want to be able to switch via frontend (which wasn't explicitly requested but "configure gemini instead of mistral" might mean that),
    // the text says "add option to configure gemini model instead of mistral". This usually means app config.
    // BUT later: "fe can upload multiple pdfs ... also add option to download it as csv."

    // I will stick to application properties for now. If the requirement meant "runtime switch from UI", I'd need to change this.
    // Given "mistral will be installed locally via ollama", it sounds like a deployment choice.

    @Autowired
    public InvoiceService(InvoiceExtractor invoiceExtractor) {
        this.invoiceExtractor = invoiceExtractor;
        this.tika = new Tika();
    }

    public Invoice extractInvoice(MultipartFile file) throws IOException, TikaException {
        String text = tika.parseToString(file.getInputStream());
        return invoiceExtractor.extract(text);
    }
}
