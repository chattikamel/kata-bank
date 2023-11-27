package org.kbank.account.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Jacksonized
public class Operation {
    private String id;
    private BigDecimal montant;
    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();
    private String identifiantCompte;
}
