package com.example.gh2971;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.KafkaNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Function;

@SpringBootApplication
public class Gh2971Application {

    public static void main(String[] args) {
        SpringApplication.run(Gh2971Application.class, args);
    }

    @Bean
    public Function<Message<?>,Message<?>> myFunction() {
        return v -> MessageBuilder.withPayload(KafkaNull.INSTANCE).build();
    }

}
