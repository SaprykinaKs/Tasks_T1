package com.example.accountprocessing.kafka;

import com.example.accountprocessing.dto.ClientProductMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProductConsumer{

    @KafkaListener(topics = "client_products", groupId = "account-products-group")
    public void listenForProducts(ClientProductMessage message) {
        System.out.printf("MS-2: Received NON-CREDIT product request for client %d, type: %s%n",
                          message.clientId(), message.productCode());
    }

    @KafkaListener(topics = "client_cards", groupId = "account-cards-group")
    public void listenForCards(ClientProductMessage message) {
        System.out.printf("MS-2: Received CARD request for client %d%n", message.clientId());

        Long accountId = 9876L; 
        boolean isBlocked = checkAccountStatus(accountId); 
        
        if (!isBlocked) {
            System.out.printf("MS-2: Card created successfully for account %d.%n", accountId);
            // cardService.createCard(message);
        } else {
            System.out.printf("MS-2: Card creation FAILED. Account %d is blocked.%n", accountId);
            // отправить сообщение об отказе 
        }
    }
    
    @KafkaListener(topics = "client_transactions", groupId = "account-tx-group")
    public void listenForTransactions(String transactionJson) {
        // Логика обработки транзакций
    }

    private boolean checkAccountStatus(Long accountId) {
        // Условный метод проверки
        return accountId % 2 == 0; 
    }
}