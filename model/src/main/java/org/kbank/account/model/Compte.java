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

    public void retrait(OperationRetrait retrait) {
        retrait.setIdentifiantCompte(identifiant);
        solde = solde.add(retrait.getMontant().negate());
        compteRepository.save(retrait);
    }

    public void traiter(Operation operation) {
        if (operation instanceof OperationDepot)
            depot((OperationDepot) operation);
        else
            retrait((OperationRetrait) operation);
    }
}
