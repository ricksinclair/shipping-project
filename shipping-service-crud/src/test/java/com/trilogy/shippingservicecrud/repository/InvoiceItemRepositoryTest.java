package com.trilogy.shippingservicecrud.repository;


import com.netflix.discovery.converters.Auto;
import com.trilogy.shippingservicecrud.model.Invoice;
import com.trilogy.shippingservicecrud.model.InvoiceItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceItemRepositoryTest {

    @Autowired
    InvoiceItemRepository invoiceItemRepository;

    @Autowired
    InvoiceRepository invoiceRepository;


    @Before
    public void setUp() throws Exception{

        List<InvoiceItem> invoiceItems = invoiceItemRepository.findAll();

        List<Invoice> invoices = invoiceRepository.findAll();


        invoiceItems.forEach(invoiceItem -> {
            invoiceItemRepository.deleteById(invoiceItem.getInvoiceItemId());
        });

        invoices.forEach(invoice -> {
            invoiceRepository.deleteById(invoice.getInvoiceId());
        });
    }



    @Test
    public void addGetDeleteInvoiceItem(){

        Invoice invoice = new Invoice();

        invoice.setCustomerId(5);
        invoice.setPurchaseDate(LocalDate.of(2019, 10, 3));
        invoice.setSalesTax(new BigDecimal("2.99"));
        invoice.setShiptoZip("10012");
        invoice.setSurcharge(new BigDecimal("12.99"));
        invoice.setTotalCost(new BigDecimal("29.99"));
        invoice = invoiceRepository.save(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();

        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setItemName("Acme 3000");
        invoiceItem.setWeight(10);
        invoiceItem.setItemDescription("Wyle E Coyote's favorite product");
        invoiceItem.setShipCost(new BigDecimal("10.99"));
        invoiceItem = invoiceItemRepository.save(invoiceItem);


        InvoiceItem fromRepo = invoiceItemRepository.findById(invoiceItem.getInvoiceItemId()).orElse(null);

        assertEquals(invoiceItem, fromRepo);

        invoiceItemRepository.deleteById(invoiceItem.getInvoiceItemId());

        fromRepo = invoiceItemRepository.findById(invoiceItem.getInvoiceItemId()).orElse(null);

        assertNull(fromRepo);

    }
}






