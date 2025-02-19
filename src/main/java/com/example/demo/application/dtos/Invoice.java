package com.example.demo.application.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Slf4j
@Data
public class Invoice {
    private int id;
    @NotNull(message = "Invoice number cannot be null")
    @Min(value = 0, message = "Invoice number must be a positive integer")
    private int invoiceNumber;
    private double grossAmount;
    private double gstAmount;
    private double netAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime receiptDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime paymentDueDate;
    private int totalNumTrxn;
    private List<Transaction> transactionList;

    @Builder
    private Invoice(int id, int invoiceNumber, double grossAmount, double gstAmount,
                    double netAmount, LocalDateTime receiptDate, LocalDateTime paymentDueDate,
                    int totalNumTrxn, List<Transaction> transactionList) {
        if (transactionList == null) {
            transactionList = new ArrayList<>();
        }
        double transactionsListGrossAmount = transactionList.stream()
                .mapToDouble(Transaction::getNetTransactionAmount).sum();

        if (!transactionList.isEmpty() && transactionList.size() != totalNumTrxn) {
            log.warn("Total number of transaction lines doesn't match totalNumTrxn " +
                    "transactionsListSize: {}  totalNumTrxn: {}", transactionList.size(), totalNumTrxn);
        }
        if (grossAmount != transactionsListGrossAmount) {
            log.warn("Invoice gross amount doesn't match summed gross amount of transactionList");
        }

        this.id = id;
        this.invoiceNumber = invoiceNumber;
        this.grossAmount = grossAmount;
        this.gstAmount = gstAmount;
        this.netAmount = netAmount;
        this.receiptDate = receiptDate;
        this.paymentDueDate = paymentDueDate;
        this.totalNumTrxn = totalNumTrxn;
        this.transactionList = transactionList;
    }

}
