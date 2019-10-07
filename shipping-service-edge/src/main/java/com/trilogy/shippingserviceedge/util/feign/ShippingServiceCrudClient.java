package com.trilogy.shippingserviceedge.util.feign;

import com.trilogy.shippingserviceedge.model.Invoice;
import com.trilogy.shippingserviceedge.model.InvoiceItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "shipping-crud")
public interface ShippingServiceCrudClient {

    @PostMapping(value = "/invoice")
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice addInvoice(@RequestBody Invoice invoice);

    @PostMapping(value = "/invoiceItem")
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceItem addInvoiceItem(@RequestBody InvoiceItem invoiceItem);

    @GetMapping(value = "/invoice/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Invoice getInvoicebyId(@PathVariable int id);

    @GetMapping(value = "/invoiceItem/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InvoiceItem getInvoiceItemById(@PathVariable int id);
}
