package com.javatechie.spring.ws.api.repository;

import com.javatechie.spring.ws.api.model.Man;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class QueueRepoImplRedis implements QueueRepo {

    private final String CUSTOMER_QUEUE = "CUSTOMER_QUEUE";
    private final String CUSTOMER_SET = "CUSTOMER_SET";

    @Autowired
    RedisTemplate<String, Man> redisTemplate;
    private HashOperations<String, String, Man> hashOperations;
    private ListOperations<String, Man> listOperations;
    //private SetOperations<String, Man> setOperations;
    // This annotation makes sure that the method needs to be executed after
    // dependency injection is done to perform any initialization.
    @PostConstruct
    private void intializeHashOperations() {
        //	hashOperations = redisTemplate.opsForHash();
        listOperations = redisTemplate.opsForList();
       // setOperations = redisTemplate.opsForSet();
        hashOperations = redisTemplate.opsForHash();
    }


    QueueRepoImplRedis() {
    }

    public Man addToQueue(Man man) {
        if(!hashOperations.hasKey(CUSTOMER_SET,man.getId())) {
           // man.setTokenId(getNewToken());
            hashOperations.put(CUSTOMER_SET,man.getId(), man);
            listOperations.rightPush(CUSTOMER_QUEUE, man);
        }
        return man;
    }

    public Man removeFromQueue(Man man) {
        if(hashOperations.hasKey(CUSTOMER_SET,man.getId())){
            man = hashOperations.get(CUSTOMER_SET,man.getId());
            hashOperations.delete(CUSTOMER_SET,man.getId());
            listOperations.remove(CUSTOMER_QUEUE,1,man);
        }
        return man;
    }

    public Man processFirst() {
        if (listOperations.size(CUSTOMER_QUEUE)>0) {
            Man man = listOperations.leftPop(CUSTOMER_QUEUE);
            hashOperations.delete(CUSTOMER_SET,man.getId());
            return man;
        }
        return null;
    }

    private int getNewToken() {
        if (listOperations.size(CUSTOMER_QUEUE)==0) {
            return 1;
        }
        return listOperations.size(CUSTOMER_QUEUE).intValue() + 1;
    }

    public List<Man> getQueue() {

        List<Man> mens =  listOperations.range(CUSTOMER_QUEUE,0,-1);

        int i=1;
        for(Man man:mens){
            man.setRank(i++);
        }
        return mens;
    }
}
