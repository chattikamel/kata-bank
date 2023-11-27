package org.kbank.account.api;

import org.kbank.account.model.CompteRepository;
import org.kbank.account.model.Operation;
import org.kbank.account.persistence.CompteSqlRepository;
import org.kbank.account.persistence.OperationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Stream;

@Service
@Transactional
class ApiCompteRepository implements CompteRepository {

    @Autowired
    private CompteSqlRepository compteSqlRepository;

    @Override
    public void save(org.kbank.account.model.Operation operation) {
        compteSqlRepository.save(getOperationEntity(operation));
    }

    @Override
    public Stream<Operation> findAllOperationForCompte(String identifiant) {
        return compteSqlRepository.findByIdentifiantCompte(identifiant)
                .stream()
                .map(ApiCompteRepository::toOperation);
    }

    private static Operation toOperation(OperationEntity op) {
        return Operation.builder()
                .uiid(UUID.fromString(op.getId()))
                .identifiantCompte(op.getIdentifiantCompte())
                .montant(op.getMontant())
                .date(op.getDate())
                .build();
    }

    private static OperationEntity getOperationEntity(Operation operation) {
        return OperationEntity.builder()
                .id(operation.getUiid().toString())
                .identifiantCompte(operation.getIdentifiantCompte())
                .date(operation.getDate())
                .montant(operation.getMontant()).build();
    }
}
