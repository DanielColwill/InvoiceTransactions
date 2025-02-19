package com.example.demo.application.dtos;

import lombok.Data;

import java.util.List;

@Data
public class InvoiceRequest {
    private String id;
    private String invoiceNumber;
    private String grossAmount;
    private String gstAmount;
    private String netAmount;
    private String receiptDate;
    private String paymentDueDate;
    private String totalNumTrxn;
    private List<TransactionRequest> transactionList;
}
