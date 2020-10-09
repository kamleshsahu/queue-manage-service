package com.javatechie.spring.ws.api.service;

import com.javatechie.spring.ws.api.model.Man;
import com.javatechie.spring.ws.api.repository.QueueRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Queue;

@Service
public class QueueManager {

    QueueRepoImpl queueRepo;

    @Autowired
    public QueueManager(QueueRepoImpl queueRepo) {
       this.queueRepo= queueRepo;
    }

    public Man addToQueue(Man man) {
        return queueRepo.addToQueue(man);
    }

    public Man removeFromQueue(Man man) {
        return queueRepo.removeFromQueue(man);
    }

    public Man processFirst(){
        return queueRepo.processFirst();
    }

    public Queue<Man> getQueue(){
        return queueRepo.getQueue();
    }


}
