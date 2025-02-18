package com.example.demo

import com.example.demo.application.dtos.Invoice
import com.example.demo.application.service.InvoiceService;
import com.example.demo.application.transformer.InvoiceConverter
import com.example.demo.infrastructure.entity.InvoiceEntity;
import com.example.demo.infrastructure.repository.InvoiceRepository;
import org.mockito.Mock;
import spock.lang.Specification

import java.time.LocalDateTime;


class InvoiceServiceTest extends Specification {
    private InvoiceRepository invoiceRepository;
    private InvoiceConverter invoiceConverter;
    private InvoiceService invoiceService;

    def setup() {
        invoiceRepository = Mock(InvoiceRepository)
        invoiceConverter = Mock(InvoiceConverter)
        invoiceService = new InvoiceService(invoiceRepository, invoiceConverter)
    }

    def "getInvoices"() {
        given:
        def id = 31550
        def invoiceNumber = 588008565
        def grossAmount = 50.08 as Double
        def gstAmount = 4.56 as Double
        def netAmount = 45.52 as Double
        def receiptDate = LocalDateTime.now()
        def paymentDueDate = LocalDateTime.now().plusDays(30)
        def totalNumTrxn = 2
        List<InvoiceEntity> entityList = List.of(InvoiceEntity.builder()
                .id(id)
                .invoiceNumber(invoiceNumber)
                .grossAmount(grossAmount)
                .gstAmount(gstAmount)
                .netAmount(netAmount)
                .receiptDate(receiptDate)
                .paymentDueDate(paymentDueDate)
                .totalNumTrxn(totalNumTrxn)
                .build())
        List<Invoice> dtoList = List.of(Invoice.builder()
                .id(31550)
                .invoiceNumber(588008565)
                .grossAmount(50.08)
                .gstAmount(4.56)
                .netAmount(45.52)
                .receiptDate(entityList.get(0).getReceiptDate())
                .paymentDueDate(entityList.get(0).getPaymentDueDate())
                .totalNumTrxn(2)
        .build())
        invoiceRepository.findAll() >> entityList
        invoiceConverter.convertToDtoList(_)  >> dtoList

        when:
        def result = invoiceService.getInvoices()

        then:
        result.each {
            it ->
                assert it.getGrossAmount() == grossAmount
                assert it.getGstAmount() == gstAmount
                assert it.getInvoiceNumber() == invoiceNumber
                assert it.getReceiptDate() == receiptDate
                assert it.getPaymentDueDate() == paymentDueDate
                assert it.getNetAmount() == netAmount
                assert it.getTotalNumTrxn() == totalNumTrxn
                assert it.getId() == id
        }
    }

    def "should create an invoice with transactions"() {
        given:
        def invoice = Invoice.builder()
                .id(31550)
                .invoiceNumber(588008565)
                .grossAmount(50.08)
                .gstAmount(4.56)
                .netAmount(45.52)
                .receiptDate(LocalDateTime.now())
                .paymentDueDate(LocalDateTime.now().plusDays(30))
                .totalNumTrxn(2)
                .build()

        def savedInvoiceEntity = InvoiceEntity.builder()
                .id(31550)
                .invoiceNumber(588008565)
                .grossAmount(50.08)
                .gstAmount(4.56)
                .netAmount(45.52)
                .receiptDate(invoice.getReceiptDate())
                .paymentDueDate(invoice.getPaymentDueDate())
                .totalNumTrxn(2)
                .build()

        when:
        def result = invoiceService.createInvoice(invoice)

        then:
        result != null
        result.id == 31550
        result.invoiceNumber == 588008565

        and:
        1 * invoiceRepository.save(_) >> savedInvoiceEntity
        invoiceConverter.convertToEntity(invoice) >> savedInvoiceEntity
        invoiceConverter.convertToDto(_ ) >> invoice
    }
}
