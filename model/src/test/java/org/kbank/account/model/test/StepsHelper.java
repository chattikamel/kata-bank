package org.kbank.account.model.test;

import org.kbank.account.model.Operation;
import org.kbank.account.model.OperationDepot;
import org.kbank.account.model.OperationRetrait;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StepsHelper {

    public static Operation clone(Operation operationEnCours) {
        return operationEnCours instanceof OperationDepot ?
                ((OperationDepot) operationEnCours).toBuilder().build() :
                ((OperationRetrait) operationEnCours).toBuilder().build();
    }

    public static Operation toOperation(List<String> l) {
        BigDecimal montant = new BigDecimal(l.get(0));
        LocalDateTime date = DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(l.get(1), LocalDateTime::from);
        if (montant.signum() > 0) {
            return new OperationDepot(montant, date);
        } else {
            return new OperationRetrait(montant.abs(), date);
        }
    }
}
