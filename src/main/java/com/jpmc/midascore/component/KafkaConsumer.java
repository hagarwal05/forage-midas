package com.jpmc.midascore.component;

import org.springframework.kafka.annotation.KafkaListener;
import com.jpmc.midascore.foundation.Transaction;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "${general.kafka-topic}")
    public void listen(Transaction transaction) {
        String data = transaction.toString();
        System.out.println(data);
    }
}
