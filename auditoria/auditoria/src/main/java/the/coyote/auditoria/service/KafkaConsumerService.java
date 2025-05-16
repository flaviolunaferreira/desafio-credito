package the.coyote.auditoria.service;

import org.springframework.kafka.support.Acknowledgment;
import the.coyote.api.model.ConsultaConcorrenteMessage;
import the.coyote.api.model.ConsultaLogMessage;

public interface KafkaConsumerService {
    void consumeConsultaLog(ConsultaLogMessage message, Acknowledgment ack);
    void consumeConsultaConcorrente(ConsultaConcorrenteMessage message, Acknowledgment ack);
}