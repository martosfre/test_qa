package com.java.eshop.eshop.controllers;

import com.java.eshop.eshop.dto.ClientDTO;
import com.java.eshop.eshop.dto.ResponseDTO;
import com.java.eshop.eshop.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    /**
     * Servicio para obtener todos los clientes
     */
    @GetMapping
    public ResponseEntity<ResponseDTO<List<ClientDTO>>> findAll() {
        return new ResponseEntity<>(ResponseDTO.<List<ClientDTO>>builder()
                .data(clientService.findAll())
                .build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<ClientDTO>> create(@RequestBody ClientDTO data) {
        return new ResponseEntity<>(ResponseDTO.<ClientDTO>builder()
                .data(clientService.createPerson(data))
                .build(), HttpStatus.OK);
    }

}
