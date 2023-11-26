package org.kbank.account.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Compte {
    private final CompteRepository compteRepository;
    private BigDecimal solde;
    private final String identifiant;

    public Compte(String identifiantCompte, BigDecimal solde, CompteRepository compteRepository) {
        this.identifiant = identifiantCompte;
        this.solde = solde;
        this.compteRepository = compteRepository;
    }

    public void depot(OperationDepot depot) {
        depot.setIdentifiantCompte(identifiant);
        solde = solde.add(depot.getMontant());
        compteRepository.save(depot);
    }
}
