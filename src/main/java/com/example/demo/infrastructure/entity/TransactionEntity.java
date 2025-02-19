package com.example.demo.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    @Id
    private int id;

    @Column(name = "date_received")
    private LocalDateTime dateReceived;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Column(name = "invoice_id")
    private int invoiceId;

    @Column(name = "invoice_number")
    private int invoiceNumber;

    @Column(name = "billing_period_start")
    private LocalDateTime billingPeriodStart;

    @Column(name = "billing_period_end")
    private LocalDateTime billingPeriodEnd;

    @Column(name = "net_transaction_amount")
    private double netTransactionAmount;

    @Column(name = "gst_amount")
    private double gstAmount;

}
