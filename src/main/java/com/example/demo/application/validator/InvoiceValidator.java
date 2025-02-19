package com.example.demo.application.validator;

import com.example.demo.application.dtos.InvoiceRequest;
import com.example.demo.application.dtos.Transaction;
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
    public boolean isValidRequest(InvoiceRequest invoiceRequest) {
        List<String> errorMessages = new ArrayList<>();
        boolean isValid = true;
        try {
            Integer.parseInt(invoiceRequest.getId());
        } catch (NumberFormatException e) {
            errorMessages.add("Invoice ID must be a valid integer.");
            isValid = false;
        }

        if (invoiceRequest.getInvoiceNumber() == null || !invoiceRequest.getInvoiceNumber().matches("\\d+")) {
            errorMessages.add("Invoice number must be a valid integer.");
            isValid = false;
        }

        try {
            double grossAmount = Double.parseDouble(invoiceRequest.getGrossAmount());
            if (grossAmount < 0) {
                errorMessages.add("Gross amount must be greater than or equal to zero.");
                isValid = false;
            }

            //
            double transactionsListGrossAmount = invoiceRequest.getTransactionList().stream()
                    .mapToDouble(it -> Double.parseDouble(it.getNetTransactionAmount())).sum();
            if (Double.parseDouble(invoiceRequest.getNetAmount()) != transactionsListGrossAmount) {
                log.warn("Invoice gross amount doesn't match summed gross amount of transactionList");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            errorMessages.add("Gross amount must be a valid decimal number.");
            isValid = false;
        }

        try {
            double gstAmount = Double.parseDouble(invoiceRequest.getGstAmount());
            if (gstAmount < 0) {
                errorMessages.add("GST amount must be greater than or equal to zero.");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            errorMessages.add("GST amount must be a valid decimal number.");
            isValid = false;
        }

        try {
            double netAmount = Double.parseDouble(invoiceRequest.getNetAmount());
            if (netAmount < 0) {
                errorMessages.add("Net amount must be greater than or equal to zero.");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            errorMessages.add("Net amount must be a valid decimal number.");
            isValid = false;
        }

        try {
            LocalDateTime.parse(invoiceRequest.getReceiptDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        } catch (Exception e) {
            errorMessages.add("Receipt date must be in the format 'yyyy-MM-dd HH:mm:ss.SSS'.");
            isValid = false;
        }

        try {
            LocalDateTime.parse(invoiceRequest.getPaymentDueDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        } catch (Exception e) {
            errorMessages.add("Payment due date must be in the format 'yyyy-MM-dd HH:mm:ss.SSS'.");
            isValid = false;
        }

        if (invoiceRequest.getTotalNumTrxn() == null || !invoiceRequest.getTotalNumTrxn().matches("\\d+")) {
            errorMessages.add("Total number of transactions must be a valid integer.");
            isValid = false;
        }

        int totalNumTrxn = Integer.parseInt(invoiceRequest.getTotalNumTrxn());
        if (totalNumTrxn < 0) {
            errorMessages.add("Total number of transactions must be zero or greater.");
            isValid = false;
        }

        if (invoiceRequest.getTransactionList() != null) {
            for (TransactionRequest transaction : invoiceRequest.getTransactionList()) {
                if (!transactionValidator.validateTransaction(transaction)) {
                    isValid = false;
                }
            }

        }

        if (!invoiceRequest.getTransactionList().isEmpty() && invoiceRequest.getTransactionList().size() != totalNumTrxn) {
            log.warn("Total number of transaction lines doesn't match totalNumTrxn " +
                    "transactionsListSize: {}  totalNumTrxn: {}", invoiceRequest.getTransactionList().size(), totalNumTrxn);
            isValid = false;
        }

        if (!errorMessages.isEmpty()) {
            log.info(errorMessages.toString());
        }
        return isValid;
    }
}
