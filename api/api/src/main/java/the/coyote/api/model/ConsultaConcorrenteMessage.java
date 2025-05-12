package the.coyote.api.model;

import lombok.Data;

@Data
public class ConsultaConcorrenteMessage {

    private String chave;
    private String ipCliente;

    @Override
    public String toString() {
        return "ConsultaConcorrenteMessage{" +
            "chave='" + chave + '\'' +
            ", ipCliente='" + ipCliente + '\'' +
        '}';
    }

}
