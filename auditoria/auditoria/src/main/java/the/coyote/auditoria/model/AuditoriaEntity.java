package the.coyote.auditoria.model;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "auditoria")
@Data
public class AuditoriaEntity {
    private String id;
    private String chaveConsulta;
    private String numeroNfse;
    private String numeroCredito;
    private String ipCliente;
    private boolean sucesso;
    private LocalDateTime timestamp;
}