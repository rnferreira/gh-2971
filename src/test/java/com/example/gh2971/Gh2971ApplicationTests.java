package com.example.gh2971;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.kafka.support.KafkaNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@ImportAutoConfiguration(TestChannelBinderConfiguration.class)
@SpringBootTest(properties = {"spring.cloud.function.definition=myFunction"})
class Gh2971ApplicationTests {

    @Value("${spring.cloud.stream.bindings.myFunction-in-0.destination}")
    private String inputTopic;

    @Value("${spring.cloud.stream.bindings.myFunction-out-0.destination}")
    private String outputTopic;

    @Autowired
    private InputDestination input;
    @Autowired
    private OutputDestination output;

    @Test
    void test() {
        var message =
                MessageBuilder.withPayload(KafkaNull.INSTANCE)
                        .build();
        this.input.send(message, this.inputTopic);

        var received = this.output.receive(0L, this.outputTopic);
        assertThat(received)
                .isNotNull()
                .extracting(Message::getPayload)
                .isEqualTo(KafkaNull.INSTANCE);
    }
}
