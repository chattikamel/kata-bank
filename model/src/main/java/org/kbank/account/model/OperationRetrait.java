package org.kbank.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
public class OperationRetrait extends Operation {

    public OperationRetrait(BigDecimal montant, LocalDateTime date) {
        super(UUID.randomUUID(), montant, date, "");
    }
}
