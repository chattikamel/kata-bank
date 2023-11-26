package org.kbank.account.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("OPERATIONS")
public class OperationEntity implements Persistable<String> {

    @Id
    protected String id;
    protected BigDecimal montant;
    protected LocalDateTime date;
    protected String identifiantCompte;

    @Override
    public boolean isNew() {
        return true;
    }
}

