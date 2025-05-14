package the.coyote.auditoria.service.impl;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import the.coyote.auditoria.model.ConsultaConcorrenteMessage;
import the.coyote.auditoria.model.ConsultaLogEntity;
import the.coyote.auditoria.model.ConsultaLogMessage;
import the.coyote.auditoria.repository.ConsultaLogRepository;
import the.coyote.auditoria.service.KafkaConsumerService;

@Service
@RequiredArgsConstructor
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    private final ConsultaLogRepository repository;

    @KafkaListener(topics = "consulta-log", groupId = "auditoria-group")
    public void consumeConsultaLog(ConsultaLogMessage message) {
        ConsultaLogEntity log = new ConsultaLogEntity();
        log.setTimestamp(LocalDateTime.now());
        log.setNumeroNfse(message.getNumeroNfse());
        log.setNumeroCredito(message.getNumeroCredito());
        log.setIpCliente(message.getIpCliente());
        log.setSucesso(message.isSucesso());
        repository.save(log);
        System.out.println("Consulta log recebida: " + message);
    }

    @KafkaListener(topics = "consulta-concorrente", groupId = "auditoria-group")
    public void consumeConsultaConcorrente(ConsultaConcorrenteMessage message) {
        ConsultaLogEntity log = new ConsultaLogEntity();
        log.setTimestamp(LocalDateTime.now());
        log.setNumeroNfse(message.getChave().startsWith("NFSE") ? message.getChave().substring(5) : null);
        log.setNumeroCredito(message.getChave().startsWith("CRED") ? message.getChave().substring(5) : null);
        log.setIpCliente(message.getIpCliente());
        log.setSucesso(true);
        repository.save(log);
        System.out.println("Consulta concorrente recebida: " + message.getChave() + " de " + message.getIpCliente());
    }
}