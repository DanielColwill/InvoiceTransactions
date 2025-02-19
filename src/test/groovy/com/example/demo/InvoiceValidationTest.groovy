package com.example.demo

import com.example.demo.application.dtos.InvoiceRequest
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
        def validationErrors = invoiceValidator.validateRequest(invoiceRequest)

        then:
        validationErrors.size() > 0
        validationErrors.contains("Invoice number must be a valid integer.")
        validationErrors.contains("Gross amount must be greater than or equal to zero.")
        validationErrors.contains("GST amount must be greater than or equal to zero.")
        validationErrors.contains("Net amount must be greater than or equal to zero.")
    }
}
