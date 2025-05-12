package the.coyote.auditoria.service;

import org.springframework.stereotype.Service;

import the.coyote.auditoria.model.ConsultaConcorrenteMessage;
import the.coyote.auditoria.model.ConsultaLogMessage;


@Service
public interface KafkaConsumerService {

    void consumeConsultaLog(ConsultaLogMessage mesage);
    void consumeConsultaConcorrente(ConsultaConcorrenteMessage message);

    

}
