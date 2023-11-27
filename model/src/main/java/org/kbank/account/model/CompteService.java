package org.kbank.account.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
public class CompteService {

    private final CompteRepository compteRepository;

    public void ouverture(String identifiant, BigDecimal soldeInitial) {
        traiter(new Operation(identifiant, soldeInitial, LocalDateTime.now()));
    }


    public void traiter(Operation operation) {
        compteRepository.save(operation);
    }

    public Stream<Operation> listerOperations(String identifiantCompte) {
        return compteRepository.findAllOperationForCompte(identifiantCompte);
    }

    public BigDecimal solde(String identifiantCompte) {
        return listerOperations(identifiantCompte)
                .map(Operation::getMontant)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

    }
}
