package com.java.eshop.eshop.common;

import com.java.eshop.eshop.common.ShopException;
import com.java.eshop.eshop.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ShopExceptionHandler {

    @ExceptionHandler({ShopException.class})
    public ResponseEntity<ResponseDTO<Void>> defaultExceptionHandler(ShopException exception) {
        return new ResponseEntity(ResponseDTO.builder().message(exception.getMessage()).build(), HttpStatus.OK);
    }

}
