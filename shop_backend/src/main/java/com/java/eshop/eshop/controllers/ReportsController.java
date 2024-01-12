package com.java.eshop.eshop.controllers;

import com.java.eshop.eshop.config.JasperRerportsSimpleConfig;
import com.java.eshop.eshop.reports.JasperReportService;
import com.java.eshop.eshop.reports.SimpleReportExporter;
import com.java.eshop.eshop.reports.SimpleReportFiller;
import com.java.eshop.eshop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/reports")
public class ReportsController {

    @Autowired
    private JasperReportService jasperReportService;

    @Autowired
    private OrderService orderService;

//    public void getReport(){
//        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//        ctx.register(JasperRerportsSimpleConfig.class);
//        ctx.refresh();
//
//        SimpleReportFiller simpleReportFiller = ctx.getBean(SimpleReportFiller.class);
//        simpleReportFiller.setReportFileName("employeeReport.jrxml");
//        simpleReportFiller.compileReport();
//
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("title", "Orders report");
////        parameters.put("minSalary", 15000.0);
////        parameters.put("condition", " LAST_NAME ='Smith' ORDER BY FIRST_NAME");
//
//        simpleReportFiller.setParameters(parameters);
//        simpleReportFiller.fillReport();
//
//        SimpleReportExporter simpleExporter = ctx.getBean(SimpleReportExporter.class);
//        simpleExporter.setJasperPrint(simpleReportFiller.getJasperPrint());
//
//        simpleExporter.exportToPdf("ordersReport.pdf", "Genaro Sulca");
////        simpleExporter.exportToXlsx("employeeReport.xlsx", "Employee Data");
////        simpleExporter.exportToCsv("employeeReport.csv");
////        simpleExporter.exportToHtml("employeeReport.html");
//    }

    @GetMapping("item-report/{format}")
    public ResponseEntity<Resource> getItemReport(@PathVariable String format)
            throws JRException, IOException {

        byte[] reportContent = jasperReportService.getItemReport(orderService.gerOrdersReport(), format);

        ByteArrayResource resource = new ByteArrayResource(reportContent);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("orders-report." + format)
                                .build().toString())
                .body(resource);
    }
}
