package the.coyote.api.model;

import lombok.Data;

@Data
public class CreditoCriadoMessage {

    private String numeroCredito;
    private String ipCliente;

    @Override
    public String toString() {
        return "CreditoCriadoMessage{" +
            "numeroCredito='" + numeroCredito + '\'' +
            ", ipCliente='" + ipCliente + '\'' +
        '}';
    }

}
