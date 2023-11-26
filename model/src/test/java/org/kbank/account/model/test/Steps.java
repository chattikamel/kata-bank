package org.kbank.account.model.test;

import io.cucumber.java8.Fr;
import io.cucumber.java8.LambdaGlue;
import org.kbank.account.model.Compte;
import org.kbank.account.model.OperationDepot;
import org.kbank.account.model.test.MockCompteRepsitory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class Steps implements Fr, LambdaGlue {

    private Compte compte;

    private OperationDepot depot;

    private MockCompteRepsitory compteRepsitory = new MockCompteRepsitory();

    public Steps() {
        Etantdonné("un client avec un compte bancaire {} et un solde à {}",
                (String identifiantCompte, BigDecimal solde) -> {
                    compte = new Compte(identifiantCompte, solde, compteRepsitory);
                });
        Quand("il fait un dépot d'argent de {} le {iso-date}", (BigDecimal montant, LocalDateTime date) -> {

            depot = new OperationDepot(montant, date);
            compte.depot(depot);
        });

        Alors("l'opération doit être ajoutée à l'historique des opérations de compte", () -> {

            Optional<OperationDepot> operation = compteRepsitory.findOperation(depot.getUiid());

            OperationDepot expectedDepotOp = depot.toBuilder()
                    .build();

            assertThat(operation)
                    .isPresent()
                    .hasValue(expectedDepotOp);
        });

        Alors("le solde de compte doit etre mis à jour", () -> {
            assertThat(compte.getSolde()).isEqualTo(new BigDecimal(150));
        });

        ParameterType("iso-date",
                "((\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2}(?:\\.\\d*)?)((-(\\d{2}):(\\d{2})|Z)?))",
                str -> LocalDateTime.parse(str, DateTimeFormatter.ISO_LOCAL_DATE_TIME));

    }


}
