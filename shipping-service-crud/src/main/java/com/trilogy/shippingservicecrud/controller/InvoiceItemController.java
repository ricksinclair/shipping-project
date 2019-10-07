package com.trilogy.shippingservicecrud.controller;

import com.trilogy.shippingservicecrud.model.InvoiceItem;
import com.trilogy.shippingservicecrud.repository.InvoiceItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InvoiceItemController {

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    public InvoiceItemController(InvoiceItemRepository invoiceItemRepository) {
        this.invoiceItemRepository = invoiceItemRepository;
    }

    @PostMapping(value = "/invoiceItem")
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceItem addInvoiceItem(@RequestBody InvoiceItem invoiceItem) {
        return invoiceItemRepository.save(invoiceItem);
    }

    @GetMapping(value = "/invoiceItem")
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceItem> getAllInvoiceItems() {
        return invoiceItemRepository.findAll();
    }

    @GetMapping(value = "/invoiceItem/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InvoiceItem getInvoiceItemById(@PathVariable int id) {
        return invoiceItemRepository.findById(id).orElse(null);
    }

    @PutMapping(value = "/invoiceItem")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateInvoiceItem(@RequestBody InvoiceItem invoiceItem) {
        invoiceItemRepository.save(invoiceItem);
    }

    @DeleteMapping(value = "/invoiceItem/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoiceItem(@PathVariable int id) {
        invoiceItemRepository.deleteById(id);
    }

    @GetMapping(value = "/invoiceItem/invoice/{invoiceId}")
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceItem> getInvoiceItemsByInvoiceId(@PathVariable int invoiceId) {
        return invoiceItemRepository.findByInvoiceId(invoiceId);
    }

}