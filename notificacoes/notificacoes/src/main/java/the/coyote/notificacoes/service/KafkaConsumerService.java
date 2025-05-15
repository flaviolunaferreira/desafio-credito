package the.coyote.notificacoes.service;

import org.springframework.stereotype.Service;

import the.coyote.notificacoes.model.CreditoCriadoMessage;


@Service
public interface KafkaConsumerService {
    void consumeCreditoCriado(CreditoCriadoMessage message);
}
