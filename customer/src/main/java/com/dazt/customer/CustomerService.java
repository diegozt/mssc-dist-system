package com.dazt.customer;

import com.dazt.amqp.RabbitMQMessageProducer;
import com.dazt.clients.fraud.FraudCheckResponse;
import com.dazt.clients.fraud.FraudClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final FraudClient fraudClient;

    private final RabbitMQMessageProducer producer;

    public void registerCustomer(CustomerRequest customerRequest){
        Customer customer = Customer.builder()
                .firstName(customerRequest.firstName()).
                lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .build();
        customerRepository.saveAndFlush(customer);
        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());
        if(fraudCheckResponse != null && fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("Fraudster!!!");
        }
        String notification = "Se ha enviado la notification";
        producer.publish(
                notification,
                "internal.exchange",
                "internal.notification.routing-key"
        );
    }

}
