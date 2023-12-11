package com.codemountain.customermanagementapplication;

import com.codemountain.customermanagementapplication.customer.Customer;
import com.codemountain.customermanagementapplication.customer.CustomerJDBCDataAccessService;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    @Bean
    CommandLineRunner runner(CustomerJDBCDataAccessService repository) {

        return args -> {
            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();

            Customer customer = new Customer(
                    firstName,
                    lastName,
                    firstName.toLowerCase() + "." + lastName.toLowerCase() + "@codemountain.com",
                    faker.number().numberBetween(16, 75)
            );

            repository.insertCustomer(customer);
        };
    }
}
