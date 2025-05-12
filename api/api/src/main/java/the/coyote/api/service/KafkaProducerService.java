package the.coyote.api.service;

import org.springframework.stereotype.Service;

import the.coyote.api.model.CreditoEntity;

@Service
public interface KafkaProducerService {
    
    void enviarConsultaLog(String numeroNfse, String numeroCredito, boolean sucesso);
    void enviarConsultaConcorrente(String chave);
    void enviarCreditoCriado(CreditoEntity credito);

}
