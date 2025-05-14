package the.coyote.auditoria.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConsultaLogMessage {
    private String numeroNfse;
    private String numeroCredito;
    private String ipCliente;
    private boolean sucesso;
}