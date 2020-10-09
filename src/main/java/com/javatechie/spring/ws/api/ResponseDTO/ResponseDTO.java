package com.javatechie.spring.ws.api.ResponseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@Builder
public class ResponseDTO<T>{
    HttpStatus status;
    T data ;

    public ResponseDTO(HttpStatus status, T data) {
        this.status = status;
        this.data = data;
    }
}
