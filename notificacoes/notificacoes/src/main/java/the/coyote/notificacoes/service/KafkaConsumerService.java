package the.coyote.notificacoes.service;

import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import the.coyote.notificacoes.model.CreditoCriadoMessage;


@Service
public interface KafkaConsumerService {
    void consumeCreditoCriado(CreditoCriadoMessage message, Acknowledgment ack);
}
