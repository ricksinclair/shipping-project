package com.trilogy.shippingservicecrud.controller;

import com.trilogy.shippingservicecrud.model.Invoice;
import com.trilogy.shippingservicecrud.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public InvoiceController(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @PostMapping(value = "/invoice")
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice addInvoice(@RequestBody Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @GetMapping(value = "/invoice")
    @ResponseStatus(HttpStatus.OK)
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @GetMapping(value = "/invoice/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Invoice getInvoicebyId(@PathVariable int id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    @PutMapping(value = "/invoice")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateInvoice(@RequestBody Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    @DeleteMapping(value = "/invoice/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable int id) {
        invoiceRepository.deleteById(id);
    }

}
