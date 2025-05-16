package the.coyote.auditoria.service.impl;

import java.time.LocalDateTime;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import the.coyote.api.model.ConsultaConcorrenteMessage;
import the.coyote.api.model.ConsultaLogMessage;
import the.coyote.auditoria.model.AuditoriaEntity;
import the.coyote.auditoria.repository.AuditoriaRepository;
import the.coyote.auditoria.service.KafkaConsumerService;

@Service
@RequiredArgsConstructor
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    private final AuditoriaRepository repository;

    @Override
    @KafkaListener(topics = "consulta-log", groupId = "auditoria-grupo")
    public void consumeConsultaLog(ConsultaLogMessage message, Acknowledgment ack) {
        String key = (message.getNumeroNfse() != null ? message.getNumeroNfse() : message.getNumeroCredito()) + "-" + message.getIpCliente();
        if (repository.findByChaveConsulta(key).isEmpty()) {
            AuditoriaEntity auditoria = new AuditoriaEntity();
            auditoria.setId(java.util.UUID.randomUUID().toString());
            auditoria.setChaveConsulta(key);
            auditoria.setNumeroNfse(message.getNumeroNfse());
            auditoria.setNumeroCredito(message.getNumeroCredito());
            auditoria.setIpCliente(message.getIpCliente());
            auditoria.setSucesso(message.isSucesso());
            auditoria.setTimestamp(LocalDateTime.now());
            repository.save(auditoria);
            System.out.println("Log de consulta salvo: " + key);
        } else {
            System.out.println("Log duplicado ignorado: " + key);
        }
        ack.acknowledge();
    }

    @Override
    @KafkaListener(topics = "consulta-concorrente", groupId = "auditoria-grupo")
    public void consumeConsultaConcorrente(ConsultaConcorrenteMessage message, Acknowledgment ack) {
        String key = message.getChave() + "-" + message.getIpCliente();
        if (repository.findByChaveConsulta(key).isEmpty()) {
            AuditoriaEntity auditoria = new AuditoriaEntity();
            auditoria.setId(java.util.UUID.randomUUID().toString());
            auditoria.setChaveConsulta(key);
            auditoria.setIpCliente(message.getIpCliente());
            auditoria.setTimestamp(LocalDateTime.now());
            repository.save(auditoria);
            System.out.println("Log concorrente salvo: " + message.getChave());
        } else {
            System.out.println("Log concorrente duplicado ignorado: " + message.getChave());
        }
        ack.acknowledge();
    }
}