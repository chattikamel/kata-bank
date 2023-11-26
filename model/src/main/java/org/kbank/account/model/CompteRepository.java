package org.kbank.account.model;

import java.util.List;

public interface CompteRepository {

    void save(Operation operation);

    List<Operation> findAllOperationForCompte(String identifiant);
}
