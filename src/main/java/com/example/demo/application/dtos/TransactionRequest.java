package com.example.demo.application.dtos;

import lombok.Data;

@Data
public class TransactionRequest {
    private String id;
    private String dateReceived;
    private String transactionDate;
    private String invoiceId;
    private String invoiceNumber;
    private String billingPeriodStart;
    private String billingPeriodEnd;
    private String netTransactionAmount;
    private String gstAmount;
}