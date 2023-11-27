package org.kbank.account.model;

import java.util.stream.Stream;

public interface CompteRepository {

    void save(Operation operation);

    Stream<Operation> findAllOperationForCompte(String identifiant);
}
