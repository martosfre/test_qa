package com.java.eshop.eshop.controllers;

import com.java.eshop.eshop.dto.OrderDTO;
import com.java.eshop.eshop.dto.ResponseDTO;
import com.java.eshop.eshop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Servicio para crear un pedido
     */
    @PostMapping
    public ResponseEntity<ResponseDTO<OrderDTO>> createOrder(@RequestBody OrderDTO data) {
        return new ResponseEntity<>(ResponseDTO.<OrderDTO>builder()
                .data(orderService.createOrder(data))
                .build(), HttpStatus.OK);
    }

    /**
     * Servicio para obtener los pedidos de un cliente en un rango de fechas
     */
    @GetMapping("/client/{personId}")
    public ResponseEntity<ResponseDTO<List<OrderDTO>>> findOrdersByClientAndDateRanges(@PathVariable Long personId,
                                                                                       @RequestParam Long initialDate,
                                                                                       @RequestParam Long finishDate) {
        return new ResponseEntity<>(ResponseDTO.<List<OrderDTO>>builder()
                .data(orderService.findOrdersByClientAndDateRanges(personId, initialDate, finishDate))
                .build(), HttpStatus.OK);
    }

    /**
     * Servicio para eliminar un pedido por id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Boolean>> deleteOrderById(@PathVariable Long id) {
        return new ResponseEntity<>(ResponseDTO.<Boolean>builder()
                .data(orderService.deleteOrder(id))
                .build(), HttpStatus.OK);
    }

}
