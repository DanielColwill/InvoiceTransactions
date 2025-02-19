package com.example.demo.application.validator;

import com.example.demo.application.dtos.InvoiceRequest;
import com.example.demo.application.dtos.TransactionRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class InvoiceValidator {

    private TransactionValidator transactionValidator;
    public List<String> validateRequest(InvoiceRequest invoiceRequest) {
        List<String> errorMessages = new ArrayList<>();

        log.info(invoiceRequest.toString());

        try {
            Integer.parseInt(invoiceRequest.getId());
        } catch (NumberFormatException e) {
            errorMessages.add("Invoice ID must be a valid integer.");
        }

        if (invoiceRequest.getInvoiceNumber() == null || !invoiceRequest.getInvoiceNumber().matches("\\d+")) {
            errorMessages.add("Invoice number must be a valid integer.");
        }

        try {
            double grossAmount = Double.parseDouble(invoiceRequest.getGrossAmount());
            if (grossAmount < 0) {
                errorMessages.add("Gross amount must be greater than or equal to zero.");
            }
        } catch (NumberFormatException e) {
            errorMessages.add("Gross amount must be a valid decimal number.");
        }

        try {
            double gstAmount = Double.parseDouble(invoiceRequest.getGstAmount());
            if (gstAmount < 0) {
                errorMessages.add("GST amount must be greater than or equal to zero.");
            }
        } catch (NumberFormatException e) {
            errorMessages.add("GST amount must be a valid decimal number.");
        }

        try {
            double netAmount = Double.parseDouble(invoiceRequest.getNetAmount());
            if (netAmount < 0) {
                errorMessages.add("Net amount must be greater than or equal to zero.");
            }
        } catch (NumberFormatException e) {
            errorMessages.add("Net amount must be a valid decimal number.");
        }

        try {
            LocalDateTime.parse(invoiceRequest.getReceiptDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        } catch (Exception e) {
            errorMessages.add("Receipt date must be in the format 'yyyy-MM-dd HH:mm:ss.SSS'.");
        }

        try {
            LocalDateTime.parse(invoiceRequest.getPaymentDueDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        } catch (Exception e) {
            errorMessages.add("Payment due date must be in the format 'yyyy-MM-dd HH:mm:ss.SSS'.");
        }

        if (invoiceRequest.getTotalNumTrxn() == null || !invoiceRequest.getTotalNumTrxn().matches("\\d+")) {
            errorMessages.add("Total number of transactions must be a valid integer.");
        }

        int totalNumTrxn = Integer.parseInt(invoiceRequest.getTotalNumTrxn());
        if (totalNumTrxn < 0) {
            errorMessages.add("Total number of transactions must be zero or greater.");
        }

        // Validate transactionList
        if (invoiceRequest.getTransactionList() != null) {
            for (TransactionRequest transaction : invoiceRequest.getTransactionList()) {
                errorMessages.addAll(transactionValidator.validateTransaction(transaction));
            }
        }

        return errorMessages;
    }
}
