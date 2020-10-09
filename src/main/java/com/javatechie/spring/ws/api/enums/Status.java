package com.javatechie.spring.ws.api.enums;

public enum  Status {

    NEW(0),
    IN_QUEUE(1),
    PROCESSED(2),
    CANCELLED(3);

    int id;
    Status(int id) {
     this.id=id;
    }
}
