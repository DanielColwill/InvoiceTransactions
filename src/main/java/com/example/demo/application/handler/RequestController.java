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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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



    @PostMapping("/invoice")
    public ResponseEntity<?> createInvoice(@RequestBody InvoiceRequest invoiceRequest) {
        List<String> validationErrors = invoiceValidator.validateRequest(invoiceRequest);
        if (!validationErrors.isEmpty()) {
            return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
        }
        Invoice savedInvoice = invoiceService.createInvoice(invoiceConverter.requestToDto(invoiceRequest));
        return new ResponseEntity<>(savedInvoice, HttpStatus.CREATED);
    }

}
