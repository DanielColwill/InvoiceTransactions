package com.example.demo.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    @Id
    private int id;

    @Column(name = "date_received", nullable = false)
    private LocalDateTime dateReceived;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "invoice_id", nullable = false)
    private int invoiceId;

    @Column(name = "invoice_number", nullable = false)
    private int invoiceNumber;

    @Column(name = "billing_period_start", nullable = false)
    private LocalDateTime billingPeriodStart;

    @Column(name = "billing_period_end", nullable = false)
    private LocalDateTime billingPeriodEnd;

    @Column(name = "net_transaction_amount", nullable = false)
    private double netTransactionAmount;

    @Column(name = "gst_amount", nullable = false)
    private double gstAmount;
}
