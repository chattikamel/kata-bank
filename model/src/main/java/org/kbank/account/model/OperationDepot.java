package org.kbank.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class OperationDepot {

    private UUID uiid;
    private BigDecimal montant;
    private LocalDateTime date;
    private String identifiantCompte;

    public OperationDepot(BigDecimal montant, LocalDateTime date) {
        this.montant = montant;
        this.date = date;
        this.uiid = UUID.randomUUID();
    }
}
