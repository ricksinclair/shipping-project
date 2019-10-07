package com.trilogy.shippingserviceedge.service;

import com.trilogy.shippingserviceedge.model.Invoice;
import com.trilogy.shippingserviceedge.model.InvoiceItem;
import com.trilogy.shippingserviceedge.util.feign.ShippingServiceCrudClient;
import com.trilogy.shippingserviceedge.viewModel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

@Component
public class ServiceLayer {

    @Autowired
    private ShippingServiceCrudClient client;

    public ServiceLayer(ShippingServiceCrudClient client) {
        this.client = client;
    }

    public InvoiceViewModel placeOrder(InvoiceViewModel ivm) {



        DecimalFormat df = new DecimalFormat("#####.##");
        //set the surcharge
        ivm.setSurcharge(calculateWeightSurcharge(ivm));

        //set the sales tax
        ivm.setSalesTax(calculateSalesTax(ivm));
        //set the total cost
        ivm.setTotalCost(ivm.getSalesTax()
                .add(ivm.getSurcharge())
                .add(calculateShippingCost(ivm)
                        .multiply(new BigDecimal(ivm.getInvoiceItems().size()))));

        //create invoice from ivm to set to repository
        Invoice invoice = new Invoice();
        invoice.setCustomerId(ivm.getCustomerId());
        //Or set to today
        invoice.setPurchaseDate(LocalDate.now());
        invoice.setSalesTax(ivm.getSalesTax());
        invoice.setShiptoZip(ivm.getShiptoZip());
        invoice.setSurcharge(ivm.getSurcharge());
        //format the total cost to constraints
        String totalCost = df.format(ivm.getTotalCost());
        invoice.setTotalCost(new BigDecimal(totalCost));
        ivm.setTotalCost(invoice.getTotalCost());
        invoice = client.addInvoice(invoice);

        Invoice finalInvoice = invoice;
        //set data for each invoice item and send it to db
        ivm.getInvoiceItems().forEach(invoiceItem -> {
            invoiceItem.setShipCost(calculateShippingCost(ivm));
            invoiceItem.setInvoiceId(finalInvoice.getInvoiceId());
            client.addInvoiceItem(invoiceItem);
        });
        ivm.setInvoiceId(invoice.getInvoiceId());
        //build ivm from db
        return buildInvoiceViewModel(finalInvoice);
    }

    public List<InvoiceViewModel> getOrdersByCustomerId(int customerId) {
        return null;
    }


    private BigDecimal calculateShippingCost(InvoiceViewModel ivm) {

        char firstDigit = ivm.getShiptoZip().charAt(0);
        BigDecimal shippingCost = new BigDecimal("0");
        if (firstDigit == '0' || firstDigit == '1' || firstDigit == '2') {
            shippingCost = new BigDecimal("9.99");
        } else if (firstDigit == '3') {
            shippingCost = new BigDecimal("14.99");
        } else if (firstDigit == '4' || firstDigit == '5' || firstDigit == '6') {
            shippingCost = new BigDecimal("19.99");
        } else if (firstDigit == '7' | firstDigit == '8' || firstDigit == '9') {
            shippingCost = new BigDecimal("24.99");
        } else {
            throw new IllegalArgumentException("Must input a valid zip code.");
        }

        return shippingCost;
    }

    private BigDecimal calculateWeightSurcharge(InvoiceViewModel ivm) {
        int totalWeight = ivm.getInvoiceItems().stream().mapToInt(InvoiceItem::getWeight).sum();
        BigDecimal weightSurcharge = new BigDecimal("0");

        if (totalWeight > 10 && totalWeight < 17) {
            weightSurcharge = new BigDecimal("8.50");
        } else if (totalWeight > 17 && totalWeight < 25) {
            weightSurcharge = new BigDecimal("12.50");
        } else if (totalWeight > 25 && totalWeight < 35) {
            weightSurcharge = new BigDecimal("19.99");
        } else if (totalWeight > 35) {
            weightSurcharge = new BigDecimal("50.00");
        }
        return weightSurcharge;
    }

    private BigDecimal calculateSalesTax(InvoiceViewModel ivm) {
        return calculateShippingCost(ivm).add(calculateWeightSurcharge(ivm)).multiply(new BigDecimal(".072"));
    }


    private InvoiceViewModel buildInvoiceViewModel(Invoice invoice) {


        InvoiceViewModel ivm = new InvoiceViewModel();

        ivm.setCustomerId(invoice.getCustomerId());
        ivm.setInvoiceId(invoice.getInvoiceId());
        //Client call
        ivm.setInvoiceItems(client.getInvoiceItemsByInvoiceId(ivm.getInvoiceId()));
        ivm.setPurchaseDate(invoice.getPurchaseDate());
        ivm.setSalesTax(invoice.getSalesTax());
        ivm.setShiptoZip(invoice.getShiptoZip());
        ivm.setSurcharge(invoice.getSurcharge());
        ivm.setTotalCost(invoice.getTotalCost());

        return ivm;

    }
}
