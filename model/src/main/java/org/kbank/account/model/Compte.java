package org.kbank.account.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
public class Compte {
    private final CompteRepository compteRepository;
    private final String identifiant;

    public static Compte ouverture(String identifiant, BigDecimal soldeInitial, CompteRepository compteRepsitory) {
        Compte compte = new Compte(compteRepsitory, identifiant);
        compte.traiter(new Operation(soldeInitial, LocalDateTime.now()));
        return compte;
    }


    public void traiter(Operation operation) {
        operation.setIdentifiantCompte(identifiant);
        compteRepository.save(operation);
    }

    public Stream<Operation> listerOperations() {
        return compteRepository.findAllOperationForCompte(identifiant);
    }

    public BigDecimal solde() {
        return listerOperations()
                .map(Operation::getMontant)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

    }
}
