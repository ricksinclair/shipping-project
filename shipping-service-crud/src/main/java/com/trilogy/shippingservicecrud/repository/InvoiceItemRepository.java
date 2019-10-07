package com.trilogy.shippingservicecrud.repository;

import com.trilogy.shippingservicecrud.model.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Integer> {
}
