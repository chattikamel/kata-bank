package org.kbank.account.persistence.test;


import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.kbank.account.persistence.OperationEntity;
import org.kbank.account.persistence.CompteSqlRepository;
import org.kbank.account.persistence.PersistenceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.math.BigDecimal;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;


@DataJdbcTest
@ContextConfiguration(classes = PersistenceConfiguration.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CompteRepositoryTest {

    @Autowired
    CompteSqlRepository compteRepository;

    @Test
    @Order(1)
    @ExpectedDatabase(value = "classpath:expectedInsertedOperation.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    void saveOperationTest() {
        OperationEntity op1 = OperationEntity.builder()
                .id("123")
                .identifiantCompte("FR562233")
                .montant(new BigDecimal(500))
                .date(of(2023, 11, 26, 19, 49, 05))
                .build();

        OperationEntity op2 = op1.toBuilder()
                .montant(op1.getMontant().negate())
                .id("124")
                .build();

        compteRepository.save(op1);
        compteRepository.save(op2);

    }

    @Test
    @DatabaseSetup("classpath:operations.xml")
    @Order(2)
    void findAllOperationByIdentifiantTest() {
        List<OperationEntity> operations = compteRepository.findByIdentifiantCompte("FR562233");
        assertThat(operations)
                .size()
                .isEqualTo(3);

        assertThat(operations
                .stream().map(OperationEntity::getMontant)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO))
                .isEqualByComparingTo(new BigDecimal(50));
    }
}


