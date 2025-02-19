package com.example.demo

import com.example.demo.application.dtos.InvoiceRequest
import com.example.demo.application.dtos.TransactionRequest
import com.example.demo.application.validator.InvoiceValidator
import com.example.demo.application.validator.TransactionValidator
import spock.lang.Specification

class InvoiceValidationTest extends Specification {
    private TransactionValidator transactionValidator
    private InvoiceValidator invoiceValidator

    def setup() {
        transactionValidator = Mock(TransactionValidator)
        invoiceValidator = new InvoiceValidator(transactionValidator)
    }

    def "invalid invoice request"() {
        given:
        def invoiceRequest = new InvoiceRequest(
                id: "31558",
                invoiceNumber: "893018131C", // invalid invoice number
                grossAmount: "-27.32", // invalid gross amount
                gstAmount: "-2.49", // invalid GST amount
                netAmount: "-24.83", // invalid net amount
                receiptDate: "2007-12-12 18:30:52.000",
                paymentDueDate: "2006-04-28 00:00:00.000",
                totalNumTrxn: "2",
                transactionList: [] // assume no transactions for this test
        )

        when:
        def isValid = invoiceValidator.isValidRequest(invoiceRequest)

        then:
        assert !isValid
//        validationErrors.size() > 0
//        validationErrors.contains("Invoice number must be a valid integer.")
//        validationErrors.contains("Gross amount must be greater than or equal to zero.")
//        validationErrors.contains("GST amount must be greater than or equal to zero.")
//        validationErrors.contains("Net amount must be greater than or equal to zero.")
    }

    def "valid invoice request"() {
        given:
        def invoiceRequest = new InvoiceRequest(
                id: "31550",
                invoiceNumber: "588008565", // Valid invoice number
                grossAmount: "50.08", // Valid gross amount
                gstAmount: "4.56", // Valid GST amount
                netAmount: "45.52", // Valid net amount
                receiptDate: "2007-12-12 18:30:52.000",
                paymentDueDate: "2006-04-28 00:00:00.000",
                totalNumTrxn: "4", // Valid totalNumTrxn
                transactionList: [
                        new TransactionRequest(
                                id: 728441,
                                dateReceived: "2007-12-12 00:00:00.000",
                                transactionDate: "2006-03-17 00:00:00.000",
                                invoiceId: 31550,
                                invoiceNumber: "588008565",
                                billingPeriodStart: "2006-01-01 00:00:00.000",
                                billingPeriodEnd: "2006-03-17 00:00:00.000",
                                netTransactionAmount: 32.0,
                                gstAmount: 3.2
                        ),
                        new TransactionRequest(
                                id: 728435,
                                dateReceived: "2007-12-12 00:00:00.000",
                                transactionDate: "2006-03-17 00:00:00.000",
                                invoiceId: 31550,
                                invoiceNumber: "588008565",
                                billingPeriodStart: "2005-12-14 00:00:00.000",
                                billingPeriodEnd: "2005-12-31 00:00:00.000",
                                netTransactionAmount: 8.39,
                                gstAmount: 0.84
                        ),
                        new TransactionRequest(
                                id: 728438,
                                dateReceived: "2007-12-12 00:00:00.000",
                                transactionDate: "2006-03-17 00:00:00.000",
                                invoiceId: 31550,
                                invoiceNumber: "588008565",
                                billingPeriodStart: "2006-01-01 00:00:00.000",
                                billingPeriodEnd: "2006-03-17 00:00:00.000",
                                netTransactionAmount: 4.06,
                                gstAmount: 0.41
                        ),
                        new TransactionRequest(
                                id: 728432,
                                dateReceived: "2007-12-12 00:00:00.000",
                                transactionDate: "2006-03-17 00:00:00.000",
                                invoiceId: 31550,
                                invoiceNumber: "588008565",
                                billingPeriodStart: "2005-12-14 00:00:00.000",
                                billingPeriodEnd: "2005-12-31 00:00:00.000",
                                netTransactionAmount: 1.07,
                                gstAmount: 0.07
                        )
                ]
        )

        when:
        def isValid = invoiceValidator.isValidRequest(invoiceRequest)

        then:
        assert isValid
    }
}

