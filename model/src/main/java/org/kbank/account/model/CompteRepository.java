package org.kbank.account.model;

import java.util.List;

public interface CompteRepository {

    void save(Operation depot);

    List<Operation> findAllOperationForCompte(String identifiant);
}
