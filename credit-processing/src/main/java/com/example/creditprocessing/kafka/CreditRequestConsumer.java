package com.example.creditprocessing.kafka;

import com.example.creditprocessing.client.ClientInfoApiClient;
import com.example.creditprocessing.dto.ClientProductMessage; // Нужно скопировать
import com.example.creditprocessing.dto.CreditDecisionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CreditRequestConsumer {

    @Autowired
    private ClientInfoApiClient clientApi;
    @Autowired
    private KafkaTemplate<String, CreditDecisionMessage> kafkaTemplate;
    @Value("${credit.limit}")
    private BigDecimal creditLimitN;

    public record ProductRegistry(String productCode, BigDecimal outstandingDebt, boolean hasDelinquency, Integer monthCount) {}
    
    @KafkaListener(topics = "client_credit_products", groupId = "credit-scoring-group")
    public void listenForCreditRequest(ClientProductMessage request) {
        System.out.printf("MS-3: Received CREDIT request for client %d, amount: %s%n",
                          request.clientId(), request.amount());

        ClientInfoApiClient.ClientInfo clientInfo = clientApi.getClientInfo(request.clientId());
        if (clientInfo == null) {
            rejectCredit(request, "Failed to retrieve client info from MS-1.");
            return;
        }

        String decision = performCreditScoring(request);

        if ("APPROVED".equals(decision)) {
            System.out.println("MS-3: Credit APPROVED. Opening product...");
            createPaymentSchedule(request.amount(), 0.22, request.monthCount()); 
            approveCredit(request);
        } else {
            rejectCredit(request, decision);
        }
    }

    private String performCreditScoring(ClientProductMessage request) {
        List<ProductRegistry> existingProducts = getExistingCredits(request.clientId());

        BigDecimal totalExistingDebt = existingProducts.stream()
                .map(ProductRegistry::outstandingDebt)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal newTotalDebt = totalExistingDebt.add(request.amount());

        if (newTotalDebt.compareTo(creditLimitN) > 0) {
            return "REJECTED_LIMIT_EXCEEDED";
        }

        boolean hasPastDelinquencies = existingProducts.stream()
                .anyMatch(ProductRegistry::hasDelinquency);

        if (hasPastDelinquencies) {
            return "REJECTED_PAST_DELINQUENCY";
        }
        
        return "APPROVED";
    }

    private void createPaymentSchedule(BigDecimal principal, double annualRate, int months) {
        // А = S × [i × (1 + i)^n] / [(1 + i)^n - 1]
        
        BigDecimal monthlyRate = BigDecimal.valueOf(annualRate).divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);
        double i = monthlyRate.doubleValue();
        int n = months;
        double S = principal.doubleValue();
        
        double monthlyPaymentValue = S * (i * Math.pow(1 + i, n)) / (Math.pow(1 + i, n) - 1);
        
        BigDecimal monthlyPayment = BigDecimal.valueOf(monthlyPaymentValue).setScale(2, RoundingMode.HALF_UP);
        
        System.out.printf("MS-3: Created payment schedule. Monthly payment (A): %s%n", monthlyPayment);

    }
        
    private void approveCredit(ClientProductMessage request) {
        CreditDecisionMessage decision = new CreditDecisionMessage(
            request.clientId(), request.productCode(), "APPROVED", null
        );
        kafkaTemplate.send("credit_decision", String.valueOf(request.clientId()), decision);
        System.out.println("MS-3: Decision APPROVED sent to credit_decision topic.");
    }

    private void rejectCredit(ClientProductMessage request, String reason) {
        CreditDecisionMessage decision = new CreditDecisionMessage(
            request.clientId(), request.productCode(), "REJECTED", reason
        );
        kafkaTemplate.send("credit_decision", String.valueOf(request.clientId()), decision);
        System.out.printf("MS-3: Decision REJECTED (%s) sent to credit_decision topic.%n", reason);
    }
    
    private List<ProductRegistry> getExistingCredits(Long clientId) {
        if (clientId.equals(999L)) { 
            return List.of(new ProductRegistry("CreditA", new BigDecimal("950000"), false, 36));
        }
        if (clientId.equals(888L)) { 
            return List.of(new ProductRegistry("CreditB", new BigDecimal("100000"), true, 12));
        }
        return List.of(); 
    }
}