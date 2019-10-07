package com.trilogy.shippingserviceedge.controller;

import com.trilogy.shippingserviceedge.service.ServiceLayer;
import com.trilogy.shippingserviceedge.viewModel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShippingEdgeController {

    @Autowired
    private ServiceLayer serviceLayer;

    public ShippingEdgeController(ServiceLayer serviceLayer) {
        this.serviceLayer = serviceLayer;
    }

    @PostMapping(value = "/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceViewModel placeShippingOrder(@RequestBody InvoiceViewModel ivm) {
        return null;
        //Service layer add invoice
    }

    @GetMapping(value = "/orders/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceViewModel> getOrdersByCustomer(@PathVariable int customerId) {
        return null;
        //Service layer get orders by customer ID
    }
}
