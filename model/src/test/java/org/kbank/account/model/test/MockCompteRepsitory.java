package org.kbank.account.model.test;

import org.kbank.account.model.CompteRepository;
import org.kbank.account.model.OperationDepot;

import java.util.*;


public class MockCompteRepsitory implements CompteRepository {

    private List<OperationDepot> operations = new ArrayList<>();

    @Override
    public void save(OperationDepot depot) {
        operations.add(depot);
    }

    public Optional<OperationDepot> findOperation(UUID uiid) {
        return operations.stream()
                .filter(op -> op.getUiid().equals(uiid))
                .findFirst();
    }
}
