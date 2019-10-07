package com.trilogy.shippingservicecrud.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Proxy(lazy=false)
public class Invoice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int invoiceId;
    @NotNull
    private int customerId;
    @NotNull
    private String shiptoZip;
    private LocalDate purchaseDate;
    private BigDecimal totalCost;
    private BigDecimal salesTax;
    private BigDecimal surcharge;


    public Invoice(){

    }


    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getShiptoZip() {
        return shiptoZip;
    }

    public void setShiptoZip(String shiptoZip) {
        this.shiptoZip = shiptoZip;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(BigDecimal salesTax) {
        this.salesTax = salesTax;
    }

    public BigDecimal getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(BigDecimal surcharge) {
        this.surcharge = surcharge;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return getInvoiceId() == invoice.getInvoiceId() &&
                getCustomerId() == invoice.getCustomerId() &&
                getShiptoZip().equals(invoice.getShiptoZip()) &&
                getPurchaseDate().equals(invoice.getPurchaseDate()) &&
                getTotalCost().equals(invoice.getTotalCost()) &&
                getSalesTax().equals(invoice.getSalesTax()) &&
                getSurcharge().equals(invoice.getSurcharge());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInvoiceId(), getCustomerId(), getShiptoZip(), getPurchaseDate(), getTotalCost(), getSalesTax(), getSurcharge());
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", customerId=" + customerId +
                ", shiptoZip='" + shiptoZip + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", totalCost=" + totalCost +
                ", salesTax=" + salesTax +
                ", surcharge=" + surcharge +
                '}';
    }
}

