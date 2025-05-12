package the.coyote.notificacoes.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class NotificacaoEntity {

    @Id
    private String id;
    private String numeroCredito;
    private String ipCliente;
    private LocalDateTime timestamp;
}
