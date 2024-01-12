package com.java.eshop.eshop;

import com.java.eshop.eshop.config.JasperRerportsSimpleConfig;
import com.java.eshop.eshop.reports.SimpleReportExporter;
import com.java.eshop.eshop.reports.SimpleReportFiller;
import com.java.eshop.eshop.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Lazy;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
//@Import({SecurityConfig.class})
public class EshopApplication {

    @Autowired
    @Lazy
    private ClientService clientService;

    public static void main(String[] args) {
        SpringApplication.run(EshopApplication.class, args);



    }

}
