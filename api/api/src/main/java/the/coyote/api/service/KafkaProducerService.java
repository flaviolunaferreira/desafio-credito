package the.coyote.api.service;

import org.springframework.stereotype.Service;

import the.coyote.api.model.CreditoEntity;

@Service
public interface KafkaProducerService {
    
    void enviarConsultaLog(String numeroNfse, String numeroCredito, String ipCliente, boolean sucesso);
    void enviarConsultaConcorrente(String chave, String ipCliente);
    void enviarCreditoCriado(CreditoEntity credito, String ipCliente);

}
