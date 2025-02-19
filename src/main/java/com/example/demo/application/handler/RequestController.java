package com.example.demo.application.handler;

import com.example.demo.application.dtos.Invoice;
import com.example.demo.application.dtos.InvoiceRequest;
import com.example.demo.application.service.InvoiceService;
import com.example.demo.application.transformer.InvoiceConverter;
import com.example.demo.application.validator.InvoiceValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Validated
@AllArgsConstructor
public class RequestController {
    private final InvoiceService invoiceService;
    private final InvoiceConverter invoiceConverter;
    private final InvoiceValidator invoiceValidator;

    @GetMapping("/invoice")
    public List<Invoice> getInvoices(){
        return invoiceService.getInvoices();
    }

    @GetMapping("/invoice/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable("id") int id) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        if (invoice == null) {
            log.warn("Invoice not found for that ID");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(invoice);
    }

    @GetMapping("/invoice/{id}/status")
    public ResponseEntity<Boolean> getInvoiceStatus(@PathVariable("id") int id) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        if (invoice == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(invoice.isValidity());
    }

    @PostMapping("/invoice")
    public ResponseEntity<?> createInvoice(@RequestBody InvoiceRequest invoiceRequest) {
        if (!invoiceValidator.isValidRequest(invoiceRequest)) {
            log.warn("invalid request, saving");
            Invoice invalidInvoice = Invoice.builder()
                    .id(Integer.parseInt(invoiceRequest.getId())) // assuming IDs are ints
                    .validity(false)
                    .build();
            invoiceService.createInvoice(invalidInvoice);
            return new ResponseEntity<>(invalidInvoice, HttpStatus.BAD_REQUEST);
        }
        Invoice savedInvoice = invoiceService.createInvoice(invoiceConverter.validRequestToDto(invoiceRequest));
        return new ResponseEntity<>(savedInvoice, HttpStatus.CREATED);
    }


}
