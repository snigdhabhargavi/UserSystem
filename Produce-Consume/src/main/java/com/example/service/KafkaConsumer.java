package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
	String consumedmessage;
    @KafkaListener(topics = {"OnlineExam" , "NewsInfo"})
    public void consume(String message)
    {
        Logger log=LoggerFactory.getLogger(KafkaConsumer.class);
        consumedmessage= message;
        log.info("Cosumed Message----> {}", message);  
    }
    
    public String getConsumedmessage() {
        return consumedmessage;
    }
}
