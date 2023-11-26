package org.kbank.account.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompteSqlRepository extends CrudRepository<OperationEntity, String> {

    List<OperationEntity> findByIdentifiantCompte(String identifiantCompte);
}
