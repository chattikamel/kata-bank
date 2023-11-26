package org.kbank.account.model.test;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.Fr;
import io.cucumber.java8.LambdaGlue;
import org.kbank.account.model.Compte;
import org.kbank.account.model.OperationDepot;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class Steps implements Fr, LambdaGlue {

    private MockCompteRepsitory compteRepsitory = new MockCompteRepsitory();

    private List<Compte> comptes = new ArrayList<>();
    private Compte compte;
    private OperationDepot depot;


    public Steps() {

        Etantdonné("les comptes bancaires suivants \\(identifiant, solde initial):", (DataTable data) ->
                data.asMap(String.class, BigDecimal.class)
                        .forEach((idt, solde) ->
                                comptes.add(new Compte(idt, solde, compteRepsitory))
                        )
        );

        Etantdonné("un client avec un compte bancaire {}",
                (String identifiantCompte) ->
                        compte = comptes
                                .stream()
                                .filter(c -> c.getIdentifiant().equals(identifiantCompte))
                                .findAny()
                                .get()
        );


        Quand("il fait un dépot d'argent de {} le {iso-date}", (BigDecimal montant, LocalDateTime date) -> {

            depot = new OperationDepot(montant, date);
            compte.depot(depot);
        });

        Alors("l'opération doit être ajoutée à l'historique des opérations de compte", () -> {

            Optional<OperationDepot> operation = compteRepsitory.findOperation(depot.getUiid());

            assertThat(operation)
                    .isPresent()
                    .hasValue(depot.toBuilder()
                            .build());
        });

        Alors("le solde de compte doit etre mis à jour", () -> {
            assertThat(compte.getSolde()).isEqualTo(new BigDecimal(150));
        });


        Alors("le solde de compte doit egal à {}", (BigDecimal solde) -> {
            assertThat(compte.getSolde()).isEqualTo(solde);
        });


        ParameterType("iso-date",
                "((\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2}(?:\\.\\d*)?)((-(\\d{2}):(\\d{2})|Z)?))",
                str -> LocalDateTime.parse(str, DateTimeFormatter.ISO_LOCAL_DATE_TIME));

    }


}
