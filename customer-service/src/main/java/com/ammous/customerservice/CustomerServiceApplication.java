package com.ammous.customerservice;

import com.ammous.customerservice.entities.Customer;
import com.ammous.customerservice.repo.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }


    @Bean
    CommandLineRunner init(CustomerRepository customerRepository) {
        return args -> {

            customerRepository.saveAll(List.of(
                    Customer.builder().name("Mohamed").email("mohamed@gmail.com").build(),
                    Customer.builder().name("yassine").email("yassine@gmail.com").build(),
                    Customer.builder().name("imen").email("imen@gmail.com").build()
            ));


        };
    }
}
