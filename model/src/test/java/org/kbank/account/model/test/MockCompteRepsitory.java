package org.kbank.account.model.test;

import org.kbank.account.model.CompteRepository;
import org.kbank.account.model.Operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class MockCompteRepsitory implements CompteRepository {

    private List<Operation> operations = new ArrayList<>();

    @Override
    public void save(Operation depot) {
        operations.add(depot);
    }


    public Optional<Operation> findOperation(UUID uiid) {
        return operations.stream()
                .filter(op -> op.getUiid().equals(uiid))
                .findFirst();
    }
}
