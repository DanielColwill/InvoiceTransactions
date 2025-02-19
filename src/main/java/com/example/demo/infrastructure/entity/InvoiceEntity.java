package com.example.demo.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invoices")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Transactional
public class InvoiceEntity {

    @Id
    private int id;

    @Column(name = "invoice_number")
    private int invoiceNumber;

    @Column(name = "gross_amount")
    private double grossAmount;

    @Column(name = "gst_amount")
    private double gstAmount;

    @Column(name = "net_amount")
    private double netAmount;

    @Column(name = "receipt_date")
    private LocalDateTime receiptDate;

    @Column(name = "payment_due_date")
    private LocalDateTime paymentDueDate;

    @Column(name = "total_num_trxn")
    private int totalNumTrxn;

    @OneToMany(mappedBy = "invoiceId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionEntity> transactionList;

    @Column(name = "validity", nullable = false)
    private boolean validity;
}
