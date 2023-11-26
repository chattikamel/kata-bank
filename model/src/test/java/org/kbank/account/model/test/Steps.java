package org.kbank.account.model.test;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.Fr;
import io.cucumber.java8.LambdaGlue;
import org.kbank.account.model.Compte;
import org.kbank.account.model.Operation;
import org.kbank.account.model.OperationDepot;
import org.kbank.account.model.OperationRetrait;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static org.assertj.core.api.Assertions.assertThat;


public class Steps implements Fr, LambdaGlue {

    private MockCompteRepsitory compteRepsitory = new MockCompteRepsitory();

    private List<Compte> comptes = new ArrayList<>();
    private Compte compte;
    private Operation operationEnCours;


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

            operationEnCours = new OperationDepot(montant, date);
            compte.depot((OperationDepot) operationEnCours);
        });

        Quand("il fait un retrait de {} le {iso-date}", (BigDecimal montant, LocalDateTime date) -> {
            operationEnCours = new OperationRetrait(montant, date);
            compte.retrait((OperationRetrait) operationEnCours);
        });


        Alors("l'opération doit être ajoutée à l'historique des opérations de compte", () -> {

            Operation expectedOp = operationEnCours instanceof OperationDepot ?
                    ((OperationDepot) operationEnCours).toBuilder().build() :
                    ((OperationRetrait) operationEnCours).toBuilder().build();

            Optional<Operation> operation = compteRepsitory.findOperation(operationEnCours.getUiid());

            assertThat(operation)
                    .isPresent()
                    .hasValue(expectedOp);
        });


        Alors("le solde de compte doit egal à {}", (BigDecimal solde) -> {
            assertThat(compte.getSolde()).isEqualTo(solde);
        });


        Etantdonné("des anciennes opérations bancaires:", (DataTable data) -> {
            data.cells().forEach(l -> {
                BigDecimal montant = new BigDecimal(l.get(0));
                LocalDateTime date = ISO_LOCAL_DATE_TIME.parse(l.get(1),LocalDateTime::from);
                if(montant.signum() > 0){
                    compte.depot(new OperationDepot(montant, date));
                }else {
                    compte.retrait(new OperationRetrait(montant.abs(), date));
                }

            });
        });

        Alors("l'historique doit etre restitué", () -> {
            compteRepsitory.findAllOperationForCompte(compte.getIdentifiant());
            assertThat(compteRepsitory.findAllOperationForCompte(compte.getIdentifiant()))
                    .filteredOn(op -> Objects.equals(op.getIdentifiantCompte(), compte.getIdentifiant()))
                    .size()
                    .isEqualTo(3);
        });


        ParameterType("iso-date",
                "((\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2}(?:\\.\\d*)?)((-(\\d{2}):(\\d{2})|Z)?))",
                str -> LocalDateTime.parse(str, ISO_LOCAL_DATE_TIME));

    }


}
