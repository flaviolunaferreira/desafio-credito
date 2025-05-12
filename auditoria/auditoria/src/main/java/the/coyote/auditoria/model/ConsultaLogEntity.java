package the.coyote.auditoria.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "consulta_logs")
public class ConsultaLogEntity {

    @Id
    private String id;

    private LocalDateTime timestamp;
    private String numeroNfse;
    private String numeroCredito;
    private String ipCliente;
    private boolean sucesso;

}
