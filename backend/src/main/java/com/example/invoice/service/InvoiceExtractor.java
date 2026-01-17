package com.example.invoice.service;

import com.example.invoice.model.Invoice;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface InvoiceExtractor {

    @SystemMessage("You are an expert invoice parser. Extract the following information from the invoice text. " +
            "If a field is missing, leave it null. Return the result as a JSON object matching the Invoice structure. " +
            "Fields to extract: " +
            "- totalAmount (finalnu cenu) " +
            "- amountWithoutVat (cenu bez dph) " +
            "- vatAmount (dph) " +
            "- supplierName (nazov dodavatela) " +
            "- issueDate (datum vystavenia - format YYYY-MM-DD) " +
            "- dueDate (datum splatnosti - format YYYY-MM-DD) " +
            "- invoiceNumber (cislo faktury) " +
            "- variableSymbol (variabilny symbol) " +
            "- vatId (IC DPH) " +
            "Do not return markdown, just the fields.")
    @UserMessage("Extract information from this invoice:\n{{it}}")
    Invoice extract(String text);
}
