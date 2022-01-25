package com.dazt.customer;

import com.dazt.clients.fraud.FraudCheckResponse;
import com.dazt.clients.fraud.FraudClient;
import com.dazt.clients.notification.NotificationClient;
import com.dazt.clients.notification.NotificationResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final RestTemplate restTemplate;

    private final FraudClient fraudClient;

    private final NotificationClient notificationClient;

    public void registerCustomer(CustomerRequest customerRequest){
        Customer customer = Customer.builder()
                .firstName(customerRequest.firstName()).
                lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .build();
        customerRepository.saveAndFlush(customer);
        //TODO check if email is valid
        //TODO check if email is not taken

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());
        if(fraudCheckResponse != null && fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("Fraudster!!!");
        }
        NotificationResponse notificationResponse = notificationClient.sendNotification("Se ha enviado la notification");
        if(notificationResponse == null || notificationResponse.id() == null) {
            throw new IllegalStateException("It was not possible to send the notification");
        }
    }

}
