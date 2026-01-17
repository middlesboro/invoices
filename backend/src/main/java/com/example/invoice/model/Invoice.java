package com.example.invoice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    private Double totalAmount;
    private Double amountWithoutVat;
    private Double vatAmount;
    private String supplierName;
    private String issueDate;
    private String dueDate;
    private String invoiceNumber;
    private String variableSymbol;
    private String vatId;
}
