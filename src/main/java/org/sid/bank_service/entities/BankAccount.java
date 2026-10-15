package org.sid.bank_service.entities;

import java.util.Date;

import org.sid.bank_service.enums.AccountType;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateCreation;
    private String currency;

    @JsonAlias("solde")
    private double balance;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    @JsonIgnore
    public double getSolde() {
        return balance;
    }

    @JsonIgnore
    public void setSolde(double solde) {
        this.balance = solde;
    }
}