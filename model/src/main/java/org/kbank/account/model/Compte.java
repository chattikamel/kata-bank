package org.kbank.account.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.stream.Stream;

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

    public Compte(String identifiantCompte, CompteRepository compteRepository) {
        this(identifiantCompte,BigDecimal.ZERO, compteRepository);
    }

    public void traiter(Operation depot) {
        depot.setIdentifiantCompte(identifiant);
        solde = solde.add(depot.getMontant());
        compteRepository.save(depot);
    }

    public Stream<Operation> listerOperations() {
        return compteRepository.findAllOperationForCompte(identifiant);
    }
}
