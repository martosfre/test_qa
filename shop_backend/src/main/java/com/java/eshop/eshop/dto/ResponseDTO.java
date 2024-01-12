package com.java.eshop.eshop.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseDTO<T> {

    private T data;
    private String message;

}
