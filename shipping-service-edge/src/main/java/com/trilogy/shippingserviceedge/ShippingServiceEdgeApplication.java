package com.trilogy.shippingserviceedge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class ShippingServiceEdgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShippingServiceEdgeApplication.class, args);
	}

}
