package com.trilogy.shippingservicecrud.repository;

import com.trilogy.shippingservicecrud.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    List<Invoice> findByCustomerId(int customerid);
}
