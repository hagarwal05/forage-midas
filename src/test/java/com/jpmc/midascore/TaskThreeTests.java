package com.jpmc.midascore;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class TaskThreeTests {
    static final Logger logger = LoggerFactory.getLogger(TaskThreeTests.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private UserPopulator userPopulator;

    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private com.jpmc.midascore.repository.UserRepository userRepository;

    @Test
    void task_three_verifier() throws InterruptedException {
        userPopulator.populate();
        String[] transactionLines = fileLoader.loadStrings("/test_data/mnbvcxz.vbnm");
        for (String transactionLine : transactionLines) {
            kafkaProducer.send(transactionLine);
        }
        Thread.sleep(2000);


        logger.info("----------------------------------------------------------");
        logger.info("----------------------------------------------------------");
        logger.info("----------------------------------------------------------");
        logger.info("use your debugger to find out what waldorf's balance is after all transactions are processed");
        logger.info("kill this test once you find the answer");
        
        com.jpmc.midascore.entity.UserRecord wilbur = userRepository.findById(39281); // Assuming 39281 or we just find by name if id is unknown. Wait, let's just find by name or print all.
        if (wilbur == null) {
            System.out.println("COULD NOT FIND WILBUR. Printing all users:");
            userRepository.findAll().forEach(u -> {
                if(u.getName().equals("wilbur")) {
                    System.out.println("FINAL WILBUR BALANCE: " + u.getBalance());
                }
            });
        } else {
             System.out.println("FINAL WILBUR BALANCE: " + wilbur.getBalance());
        }

        while (true) {
            Thread.sleep(20000);
            logger.info("...");
        }
    }
}
