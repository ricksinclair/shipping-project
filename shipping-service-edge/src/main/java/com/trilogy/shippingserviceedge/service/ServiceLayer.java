package com.trilogy.shippingserviceedge.service;

import com.trilogy.shippingserviceedge.model.Invoice;
import com.trilogy.shippingserviceedge.model.InvoiceItem;
import com.trilogy.shippingserviceedge.viewModel.InvoiceViewModel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ServiceLayer {


    private BigDecimal calculateShippingCost(InvoiceViewModel  ivm){


        char firstDigit = ivm.getShiptoZip().charAt(0);

     if(firstDigit == '0'|| firstDigit=='1'||firstDigit=='2'){
         return new BigDecimal("9.99");
     }else if (firstDigit == '3'){
         return new BigDecimal("14.99");

     }else if(firstDigit == '4'|| firstDigit == '5' || firstDigit == '6'){
         return new BigDecimal("19.99");
     }else if (firstDigit == '7'| firstDigit == '8' || firstDigit == '9'){
         return new BigDecimal("24.99");
     }else{
         throw new IllegalArgumentException("Must input a valid zip code.");
     }
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
        return  weightSurcharge;
    }


    private BigDecimal calculateSalesTax(InvoiceViewModel ivm){
      return   calculateShippingCost(ivm).add(calculateWeightSurcharge(ivm)).multiply(new BigDecimal(".072"));
    }




    private InvoiceViewModel buildInvoiceViewModel(Invoice invoice, List<InvoiceItem> invoiceItems){

        invoiceItems.forEach(invoiceItem -> {
            invoiceItem.setInvoiceId(invoice.getInvoiceId());
        });

        InvoiceViewModel ivm = new InvoiceViewModel();

        ivm.setCustomerId(invoice.getCustomerId());
        ivm.setInvoiceId(invoice.getInvoiceId());
        ivm.setInvoiceItems(invoiceItems);
        ivm.setPurchaseDate(invoice.getPurchaseDate());
        ivm.setSalesTax(invoice.getSalesTax());
        ivm.setShiptoZip(invoice.getShiptoZip());
        ivm.setSurcharge(invoice.getSurcharge());
        ivm.setTotalCost(invoice.getTotalCost());

        return ivm;

    }
}
