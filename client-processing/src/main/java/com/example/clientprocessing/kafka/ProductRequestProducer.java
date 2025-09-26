package com.example.clientprocessing.kafka;

import com.example.clientprocessing.dto.ClientProductMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductRequestProducer {

    @Autowired
    private KafkaTemplate<String, ClientProductMessage> kafkaTemplate;

    @Value("${app.kafka.topics.products}")
    private String topicNonCredit;

    @Value("${app.kafka.topics.credit-products}")
    private String topicCredit;

    @Value("${app.kafka.topics.cards}")
    private String topicCards;

    public void sendProductRequest(ClientProductMessage message) {
        String topic;
        switch (message.productCode()) {
            case "DC", "CC", "NS", "PENS" -> topic = topicNonCredit;
            case "IPO", "PC", "AC" -> topic = topicCredit;
            default -> throw new IllegalArgumentException("Unknown product code: " + message.productCode());
        }

        kafkaTemplate.send(topic, String.valueOf(message.clientId()), message);
        System.out.printf("MS-1: Sent product request to topic %s for client %d%n", topic, message.clientId());
    }

    public void sendCardRequest(Long clientId, Long accountId, String cardType) {
        ClientProductMessage cardMessage = new ClientProductMessage(clientId, "CARD_REQUEST", null, null);
        kafkaTemplate.send(topicCards, String.valueOf(clientId), cardMessage);
        System.out.printf("MS-1: Sent card request to topic %s for client %d%n", topicCards, clientId);
    }
}