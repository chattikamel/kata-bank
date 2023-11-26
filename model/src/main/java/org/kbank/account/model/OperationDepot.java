package org.kbank.account.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
public class OperationDepot extends Operation{

    public OperationDepot(BigDecimal montant, LocalDateTime date) {
        super(UUID.randomUUID(), montant, date, "");
    }
}
