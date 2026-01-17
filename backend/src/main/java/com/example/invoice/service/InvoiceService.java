package com.example.invoice.service;

import com.example.invoice.model.Invoice;
import dev.langchain4j.data.message.Content;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceExtractor invoiceExtractor;
    private final Tika tika;

    @Value("${invoice.extraction.method:text}")
    private String extractionMethod;

    @Autowired
    public InvoiceService(InvoiceExtractor invoiceExtractor) {
        this.invoiceExtractor = invoiceExtractor;
        this.tika = new Tika();
    }

    public Invoice extractInvoice(MultipartFile file) throws IOException, TikaException {
        if ("vision".equalsIgnoreCase(extractionMethod)) {
            List<ImageContent> images = pdfToImages(file);
            List<Content> contents = new ArrayList<>();
            contents.add(TextContent.from("Extract information from this invoice:"));
            contents.addAll(images);
            UserMessage userMessage = UserMessage.from(contents);
            return invoiceExtractor.extract(userMessage);
        } else {
            String text = tika.parseToString(file.getInputStream());
            return invoiceExtractor.extract(text);
        }
    }

    private List<ImageContent> pdfToImages(MultipartFile file) throws IOException {
        List<ImageContent> images = new ArrayList<>();
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bim, "png", baos);
                String base64 = Base64.getEncoder().encodeToString(baos.toByteArray());
                images.add(ImageContent.from(base64, "image/png"));
            }
        }
        return images;
    }
}
