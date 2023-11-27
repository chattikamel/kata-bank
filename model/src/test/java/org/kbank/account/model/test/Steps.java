package org.kbank.account.model.test;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.Fr;
import io.cucumber.java8.LambdaGlue;
import org.kbank.account.model.Compte;
import org.kbank.account.model.Operation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.kbank.account.model.Compte.ouverture;


public class Steps implements Fr, LambdaGlue {

    private MockCompteRepsitory compteRepsitory = new MockCompteRepsitory();

    private List<Compte> comptes = new ArrayList<>();
    private Compte compte;
    private Operation operationEnCours;


    public Steps() {

        Etantdonné("les comptes bancaires suivants \\(identifiant, solde initial):", (DataTable data) ->
                data.asMap(String.class, BigDecimal.class)
                        .forEach((idt, solde) ->
                                comptes.add(ouverture(idt, solde, compteRepsitory))
                        )
        );

        Etantdonné("un client avec un compte bancaire {}",
                (String identifiantCompte) ->
                        compte = comptes
                                .stream()
                                .filter(c -> c.getIdentifiant().equals(identifiantCompte))
                                .findAny()
                                .orElse(new Compte(compteRepsitory, identifiantCompte))
        );

        Quand("il fait un dépot d'argent de {} le {iso-date}", (BigDecimal montant, LocalDateTime date) -> {
            operationEnCours = new Operation(montant, date);
            compte.traiter(operationEnCours);
        });

        Quand("il fait un retrait de {} le {iso-date}", (BigDecimal montant, LocalDateTime date) -> {
            operationEnCours = new Operation(montant, date);
            compte.traiter(operationEnCours);
        });


        Alors("l'opération doit être ajoutée à l'historique des opérations de compte", () -> assertThat(
                compteRepsitory.findOperation(operationEnCours.getUiid()))
                .isPresent()
                .hasValue(operationEnCours.clone()));


        Alors("le solde de compte doit égale à {}", (BigDecimal solde) -> assertThat(compte.solde()).isEqualTo(solde));


        Etantdonné("des anciennes opérations bancaires:", (DataTable data) ->
                data.cells()
                        .stream().map(StepsHelper::toOperation)
                        .forEach(compte::traiter)

        );

        Alors("l'historique doit etre restitué", () -> {
            assertThat(compte.listerOperations().collect(Collectors.toList()))
                    .isEqualTo(compteRepsitory.findAllOperationForCompte(
                            compte.getIdentifiant()).collect(Collectors.toList()));

        });


        ParameterType("iso-date",
                "((\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2}(?:\\.\\d*)?)((-(\\d{2}):(\\d{2})|Z)?))",
                str -> LocalDateTime.parse(str, ISO_LOCAL_DATE_TIME));

    }


}
