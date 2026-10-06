package org.sid.bank_service;

import java.util.Date;

import org.sid.bank_service.Repository.BankAccountRepository;
import org.sid.bank_service.entities.BankAccount;
import org.sid.bank_service.enums.AccountType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BankServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(BankAccountRepository bankAccountRepository) {
        return args -> {
            if (bankAccountRepository.count() == 0) {
                for (int i = 0; i < 10; i++) {
                    BankAccount bankAccount = BankAccount.builder()
                            .solde(Math.random() * 10000)
                            .currency(Math.random() > 0.5 ? "USD" : "EUR")
                            .dateCreation(new Date())
                            .type(Math.random() > 0.5 ? AccountType.CURRENT_ACCOUNT : AccountType.SAVINGS_ACCOUNT)
                            .build();
                    bankAccountRepository.save(bankAccount);
                }
            }
        };
    }
}