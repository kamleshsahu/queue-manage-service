package com.javatechie.spring.ws.api.repository;

import com.javatechie.spring.ws.api.model.Man;

import java.util.List;
import java.util.Queue;

public interface QueueRepo {

    Man addToQueue(Man man);

    Man removeFromQueue(Man man);

    Man processFirst();

    List<Man> getQueue();
}
