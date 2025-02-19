package com.example.demo.application.validator;

import com.example.demo.application.dtos.TransactionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TransactionValidator {
    public List<String> validateTransaction(TransactionRequest transactionRequest) {
        List<String> errorMessages = new ArrayList<>();
        try {
            Integer.parseInt(transactionRequest.getId());
        } catch (NumberFormatException e) {
            errorMessages.add("Invoice ID must be a valid integer.");
        }

        if (transactionRequest.getInvoiceNumber() == null ||
                !transactionRequest.getInvoiceNumber().matches("\\d+")) {
            errorMessages.add("Invoice number must be a valid integer.");
        }

        try {
            double gstAmount = Double.parseDouble(transactionRequest.getGstAmount());
            if (gstAmount < 0) {
                errorMessages.add("GST amount must be greater than or equal to zero.");
            }
        } catch (NumberFormatException e) {
            errorMessages.add("GST amount must be a valid decimal number.");
        }

        try {
            double netAmount = Double.parseDouble(transactionRequest.getNetTransactionAmount());
            if (netAmount < 0) {
                errorMessages.add("Net amount must be greater than or equal to zero.");
            }
        } catch (NumberFormatException e) {
            errorMessages.add("Net amount must be a valid decimal number.");
        }

        try {
            LocalDateTime.parse(transactionRequest.getBillingPeriodStart(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        } catch (Exception e) {
            errorMessages.add("Billing Period Start date must be in the format 'yyyy-MM-dd HH:mm:ss.SSS'.");
        }

        try {
            LocalDateTime.parse(transactionRequest.getBillingPeriodEnd(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        } catch (Exception e) {
            errorMessages.add("Billing Period End date must be in the format 'yyyy-MM-dd HH:mm:ss.SSS'.");
        }
        if (errorMessages.size() > 0) {
            errorMessages.add(0, "Transaction Errors for " + transactionRequest.getId());
        }
        return errorMessages;
    }
}
