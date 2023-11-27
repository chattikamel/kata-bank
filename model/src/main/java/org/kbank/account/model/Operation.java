package org.kbank.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class Operation {

    protected UUID uiid;
    protected BigDecimal montant;
    protected LocalDateTime date;
    protected String identifiantCompte;

    public Operation(BigDecimal montant, LocalDateTime date) {
        this.uiid = UUID.randomUUID();
        this.montant = montant;
        this.date = date;
    }

    public boolean isForCompte(String identifiant) {
        return Objects.equals(identifiantCompte, identifiant);
    }

    @Override
    public Operation clone() {
        return toBuilder()
                .build();
    }
}

