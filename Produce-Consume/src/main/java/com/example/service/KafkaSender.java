package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSender {
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    String kafkaTopic1 = "OnlineExam";
    String kafkaTopic2 = "NewsInfo";
    Logger log=LoggerFactory.getLogger(KafkaSender.class);
    
    public void sendExamMessage(String message) {   
        kafkaTemplate.send(kafkaTopic1, message);
        log.info("Online Exam Message Sent");
    }
    
    public void sendNewsInformation(String message) {   
        kafkaTemplate.send(kafkaTopic2, message);
        log.info("News Information Sent");
    }
}
