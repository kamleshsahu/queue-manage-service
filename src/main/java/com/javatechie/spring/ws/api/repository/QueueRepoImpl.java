package com.javatechie.spring.ws.api.repository;


import com.javatechie.spring.ws.api.model.Man;
import org.springframework.stereotype.Repository;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

@Repository
public class QueueRepoImpl implements QueueRepo {

    ArrayDeque<Man> queue = new ArrayDeque<>();
    Set<Man> set = new HashSet<>();

    QueueRepoImpl() {
    }

    public Man addToQueue(Man man) {
        if (!set.contains(man)) {
            set.add(man);
            man.setTokenId(getNewToken());
            queue.add(man);
        }
        return man;
    }

    public Man removeFromQueue(Man man) {
        if (set.contains(man)) {
            set.remove(man);
            queue.remove(man);
        }
        return man;
    }

    public Man processFirst() {
        if (!queue.isEmpty()) {
            Man man = queue.poll();
            set.remove(man);
            return man;
        }
        return null;
    }

    private int getNewToken() {
        if (queue.isEmpty()) {
            return 1;
        }
        return queue.getLast().getTokenId() + 1;
    }

    public Queue<Man> getQueue() {
        return queue;
    }
}
