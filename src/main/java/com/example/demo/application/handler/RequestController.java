package com.example.demo.application.handler;

import com.example.demo.application.dtos.Invoice;
import com.example.demo.application.service.InvoiceService;
import com.example.demo.infrastructure.entity.InvoiceEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class RequestController {
    private final InvoiceService invoiceService;

    public RequestController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/invoice")
    public List<Invoice> getInvoices(){
        return invoiceService.getInvoices();
    }

    @PostMapping("/invoice")
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        Invoice savedInvoice = invoiceService.createInvoice(invoice);

        return new ResponseEntity<>(savedInvoice, HttpStatus.CREATED);
    }
}
