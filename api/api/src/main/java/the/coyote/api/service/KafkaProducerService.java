package the.coyote.api.service;

import the.coyote.api.model.CreditoEntity;

public interface KafkaProducerService {
    void enviarConsultaLog(String numeroNfse, String numeroCredito, boolean sucesso, String ipCliente);
    void enviarConsultaConcorrente(String chave, String ipCliente);
    void enviarCreditoCriado(CreditoEntity credito, String ipCliente);
}