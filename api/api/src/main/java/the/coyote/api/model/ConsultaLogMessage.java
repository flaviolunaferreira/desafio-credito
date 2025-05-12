package the.coyote.api.model;

import lombok.Data;

@Data
public class ConsultaLogMessage {

    private String numeroNfse;
    private String numeroCredito;
    private String ipCliente;
    private boolean sucesso;

    @Override
    public String toString() {
        return "ConsultaLogMessage{" +
            "numeroNfse='" + numeroNfse + '\'' +
            ", numeroCredito='" + numeroCredito + '\'' +
            ", ipCliente='" + ipCliente + '\'' +
            ", sucesso=" + sucesso +
        '}';
    }

}
