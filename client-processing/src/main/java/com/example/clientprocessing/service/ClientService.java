package com.example.clientprocessing.service;

import com.example.clientprocessing.dto.ClientInfo;
import com.example.clientprocessing.dto.ClientRegistrationRequest;
import com.example.clientprocessing.entity.Client;
import com.example.clientprocessing.entity.User;
import com.example.clientprocessing.repository.ClientRepository;
import com.example.clientprocessing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Long registerClient(ClientRegistrationRequest request) {
        if (request.blacklistCheck()) {
            // blacklist check 
            throw new RuntimeException("Client is in blacklist");
        }

        User user = User.builder()
                .login(generateLogin(request.email()))
                .password(generatePassword()) 
                .email(request.email())
                .build();
        user = userRepository.save(user);

        String[] nameParts = parseFullName(request.fullName());
        Client client = Client.builder()
                .clientId(generateClientId())
                .user(user)
                .firstName(nameParts[0])
                .middleName(nameParts.length > 2 ? nameParts[1] : null)
                .lastName(nameParts.length > 1 ? nameParts[nameParts.length - 1] : null)
                .dateOfBirth(request.dateOfBirth())
                .documentType(request.documentType())
                .documentId(request.documentNumber())
                .build();
        client = clientRepository.save(client);

        return client.getId();
    }

    private String generateLogin(String email) {
        return email.split("@")[0] + UUID.randomUUID().toString().substring(0, 4);
    }

    private String generatePassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private String generateClientId() {
        String xx = "77";
        String ff = "01";
        int randomNum = (int) (Math.random() * 100000000);
        String nn = String.format("%08d", randomNum);
        return xx + ff + nn;
    }

    private String[] parseFullName(String fullName) {
        return fullName.trim().split("\\s+");
    }

    public ClientInfo getClientInfo(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        return new ClientInfo(client.getFullName(), client.getDocumentId());
    }
}
