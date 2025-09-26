package com.example.clientprocessing.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.clientprocessing.dto.CardRequest;
import com.example.clientprocessing.dto.ClientInfo;
import com.example.clientprocessing.dto.ClientProductMessage;
import com.example.clientprocessing.dto.ClientRegistrationRequest;
import com.example.clientprocessing.kafka.ProductRequestProducer;
import com.example.clientprocessing.service.ClientService;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController{

    @Autowired
    private ProductRequestProducer producer;

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<String> registerClient(@RequestBody ClientRegistrationRequest request) {
        Long clientId = clientService.registerClient(request);
        return ResponseEntity.status(201).body(
            "Client registered successfully. Client ID: " + clientId
        );
    }
    
    @GetMapping("/{clientId}/info")
    public ResponseEntity<ClientInfo> getClientInfo(@PathVariable Long clientId) {
        ClientInfo info = clientService.getClientInfo(clientId);
        return ResponseEntity.ok(info);
    }

    @PostMapping("/{clientId}/products")
    public ResponseEntity<String> createProduct(@PathVariable Long clientId, @RequestBody ClientProductMessage request) {
        ClientProductMessage updated = new ClientProductMessage(clientId, request.productCode(), request.amount(), request.monthCount());

        producer.sendProductRequest(updated);

        return ResponseEntity.accepted().body("Product request sent for processing.");
    }
    
    @PostMapping("/{clientId}/cards")
    public ResponseEntity<String> createCard(@PathVariable Long clientId, @RequestBody CardRequest request) {
        producer.sendCardRequest(clientId, request.accountId(), request.cardType());
        return ResponseEntity.accepted().body("Card creation request sent.");
    }

}
