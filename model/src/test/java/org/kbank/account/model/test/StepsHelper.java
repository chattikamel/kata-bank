package org.kbank.account.model.test;

import org.kbank.account.model.Operation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

public class StepsHelper {

    public static Operation toOperation(List<String> strings, String identifiant) {
        BigDecimal montant = new BigDecimal(strings.get(0));
        LocalDateTime date = ISO_LOCAL_DATE_TIME.parse(strings.get(1), LocalDateTime::from);
        return new Operation(identifiant, montant, date);
    }

}
