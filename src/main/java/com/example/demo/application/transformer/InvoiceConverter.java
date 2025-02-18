package com.example.demo.application.transformer;

import com.example.demo.application.dtos.Invoice;
import com.example.demo.application.dtos.Transaction;
import com.example.demo.infrastructure.entity.InvoiceEntity;
import com.example.demo.infrastructure.entity.TransactionEntity;
import org.springframework.stereotype.Component;

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
}