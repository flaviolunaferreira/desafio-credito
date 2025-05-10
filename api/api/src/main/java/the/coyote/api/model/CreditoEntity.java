package the.coyote.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CreditoEntity {

    @Id
    private String numeroCredito;
    private String numeroNfse;
    private BigDecimal valor;
    private LocalDate dataEmissao;
}