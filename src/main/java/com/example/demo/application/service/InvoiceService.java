package com.example.demo.application.service;

import com.example.demo.application.dtos.Invoice;
import com.example.demo.application.transformer.InvoiceConverter;
import com.example.demo.infrastructure.entity.InvoiceEntity;
import com.example.demo.infrastructure.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    InvoiceConverter invoiceConverter;

    public List<Invoice> getInvoices() {
        return invoiceConverter.convertToDtoList(invoiceRepository.findAll());
    }

    public Invoice createInvoice(Invoice invoice) {
        if (invoiceRepository.existsById(invoice.getId())) {
            throw new IllegalArgumentException("Invoice with ID " + invoice.getId() + " already exists.");
        }

        InvoiceEntity invoiceEntity = invoiceConverter.convertToEntity(invoice);
        log.info("Saving InvoiceEntity: {}", invoiceEntity);

        InvoiceEntity savedInvoice = invoiceRepository.save(invoiceEntity);
        log.info("Saved InvoiceEntity: {}", savedInvoice);

        return invoiceConverter.convertToDto(savedInvoice);
    }

    public Invoice getInvoiceById(int id) {
        Optional<InvoiceEntity> invoiceOptional = invoiceRepository.findById(id);
        if (invoiceOptional.isEmpty()) {
            return null;
        }
        return invoiceConverter.convertToDto(invoiceOptional.get());
    }
}
