package com.trilogy.shippingservicecrud.repository;


import com.trilogy.shippingservicecrud.model.Invoice;
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
public class InvoiceRepositoryTest {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Before
    public void setUp() throws Exception{

        List<Invoice> invoices = invoiceRepository.findAll();
        invoices.forEach(invoice -> {
            invoiceRepository.deleteById(invoice.getInvoiceId());
        });
    }


    @Test
    public void addGetDeleteInvoice(){
        Invoice invoice = new Invoice();

        invoice.setCustomerId(5);
        invoice.setPurchaseDate(LocalDate.of(2019, 10, 3));
        invoice.setSalesTax(new BigDecimal("2.99"));
        invoice.setShiptoZip("10012");
        invoice.setSurcharge(new BigDecimal("12.99"));
        invoice.setTotalCost(new BigDecimal("29.99"));
        invoice = invoiceRepository.save(invoice);

        Invoice fromRepo = invoiceRepository.findById(invoice.getInvoiceId()).orElse(null);

        assertEquals(fromRepo, invoice);

        invoiceRepository.deleteById(invoice.getInvoiceId());
        fromRepo = invoiceRepository.findById(invoice.getInvoiceId()).orElse(null);

        assertNull(fromRepo);

    }
}
