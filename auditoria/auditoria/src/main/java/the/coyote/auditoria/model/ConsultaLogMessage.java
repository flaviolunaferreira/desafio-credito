package the.coyote.auditoria.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
    public class ConsultaLogMessage {
        private LocalDateTime timestamp;
        private String numeroNfse;
        private String numeroCredito;
        private String ipCliente;
        private boolean sucesso;
    }
