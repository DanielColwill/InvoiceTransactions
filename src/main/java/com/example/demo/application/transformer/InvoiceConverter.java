package com.example.demo.application.transformer;

import com.example.demo.application.dtos.Invoice;
import com.example.demo.application.dtos.InvoiceRequest;
import com.example.demo.application.dtos.Transaction;
import com.example.demo.application.dtos.TransactionRequest;
import com.example.demo.infrastructure.entity.InvoiceEntity;
import com.example.demo.infrastructure.entity.TransactionEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class InvoiceConverter {

    public Invoice convertToDto(InvoiceEntity entity) {
        return Invoice.builder()
                .id(entity.getId())
                .invoiceNumber(entity.getInvoiceNumber())
                .grossAmount(entity.getGrossAmount())
                .gstAmount(entity.getGstAmount())
                .netAmount(entity.getNetAmount())
                .receiptDate(entity.getReceiptDate())
                .paymentDueDate(entity.getPaymentDueDate())
                .totalNumTrxn(entity.getTotalNumTrxn())
                .transactionList(convertTransactionEntitiesToDtos(entity.getTransactionList()))
                .validity(entity.isValidity())
                .build();
    }

    public InvoiceEntity convertToEntity(Invoice dto) {
        return InvoiceEntity.builder()
                .id(dto.getId())
                .invoiceNumber(dto.getInvoiceNumber())
                .grossAmount(dto.getGrossAmount())
                .gstAmount(dto.getGstAmount())
                .netAmount(dto.getNetAmount())
                .receiptDate(dto.getReceiptDate())
                .paymentDueDate(dto.getPaymentDueDate())
                .totalNumTrxn(dto.getTotalNumTrxn())
                .transactionList(convertTransactionDtosToEntities(dto.getTransactionList()))
                .validity(dto.isValidity())
                .build();
    }

    public List<Invoice> convertToDtoList(List<InvoiceEntity> entities) {
        return entities.stream()
                .map(this::convertToDto).toList();
    }

    private List<Transaction> convertTransactionEntitiesToDtos(List<TransactionEntity> entities) {
        return entities.stream()
                .map(this::convertTransactionToDto).toList();
    }

    private List<TransactionEntity> convertTransactionDtosToEntities(List<Transaction> entities) {
        return entities.stream()
                .map(this::convertTransactionToEntity).toList();
    }

    private Transaction convertTransactionToDto(TransactionEntity entity) {
        return Transaction.builder()
                .id(entity.getId())
                .dateReceived(entity.getDateReceived())
                .transactionDate(entity.getTransactionDate())
                .invoiceId(entity.getInvoiceId())
                .invoiceNumber(entity.getInvoiceNumber())
                .billingPeriodStart(entity.getBillingPeriodStart())
                .billingPeriodEnd(entity.getBillingPeriodEnd())
                .netTransactionAmount(entity.getNetTransactionAmount())
                .gstAmount(entity.getGstAmount())
                .build();
    }
    private TransactionEntity convertTransactionToEntity(Transaction dto) {
        return TransactionEntity.builder()
                .id(dto.getId())
                .dateReceived(dto.getDateReceived())
                .transactionDate(dto.getTransactionDate())
                .invoiceId(dto.getInvoiceId())
                .invoiceNumber(dto.getInvoiceNumber())
                .billingPeriodStart(dto.getBillingPeriodStart())
                .billingPeriodEnd(dto.getBillingPeriodEnd())
                .netTransactionAmount(dto.getNetTransactionAmount())
                .gstAmount(dto.getGstAmount())
                .build();
    }

    public InvoiceEntity convertTransactionToEntity(Invoice invoice) {
        return InvoiceEntity.builder()
                .id(invoice.getId())
                .invoiceNumber(invoice.getInvoiceNumber())
                .grossAmount(invoice.getGrossAmount())
                .gstAmount(invoice.getGstAmount())
                .netAmount(invoice.getNetAmount())
                .receiptDate(invoice.getReceiptDate())
                .paymentDueDate(invoice.getPaymentDueDate())
                .totalNumTrxn(invoice.getTotalNumTrxn())
                .transactionList(convertTransactionDtosToEntities(invoice.getTransactionList()))
                .build();
    }

    public Invoice validRequestToDto(InvoiceRequest invoiceRequest) {
        return Invoice.builder()
                .id(Integer.parseInt(invoiceRequest.getId()))
                .invoiceNumber(Integer.parseInt(invoiceRequest.getInvoiceNumber()))
                .grossAmount(Double.parseDouble(invoiceRequest.getGrossAmount()))
                .gstAmount(Double.parseDouble(invoiceRequest.getGstAmount()))
                .netAmount(Double.parseDouble(invoiceRequest.getNetAmount()))
                .receiptDate(LocalDateTime.parse(invoiceRequest.getReceiptDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")))
                .paymentDueDate(LocalDateTime.parse(invoiceRequest.getPaymentDueDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")))
                .totalNumTrxn(Integer.parseInt(invoiceRequest.getTotalNumTrxn()))
                .transactionList(validRequestToDto(invoiceRequest.getTransactionList()))
                .validity(true)
                .build();
    }

    private List<Transaction> validRequestToDto(List<TransactionRequest> transactionRequests) {
        List<Transaction> transactions = new ArrayList<>();
        for (TransactionRequest transactionRequest : transactionRequests) {
            Transaction transaction = Transaction.builder()
                    .id(Integer.parseInt(transactionRequest.getId()))
                    .dateReceived(LocalDateTime.parse(transactionRequest.getDateReceived(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")))
                    .transactionDate(LocalDateTime.parse(transactionRequest.getTransactionDate(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")))
                    .invoiceId(Integer.parseInt(transactionRequest.getInvoiceId()))
                    .invoiceNumber(Integer.parseInt(transactionRequest.getInvoiceNumber()))
                    .billingPeriodStart(LocalDateTime.parse(transactionRequest.getBillingPeriodStart(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")))
                    .billingPeriodEnd(LocalDateTime.parse(transactionRequest.getBillingPeriodEnd(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")))
                    .netTransactionAmount(Double.parseDouble(transactionRequest.getNetTransactionAmount()))
                    .gstAmount(Double.parseDouble(transactionRequest.getGstAmount()))
                    .build();

            transactions.add(transaction);
        }
        return transactions;
    }
}