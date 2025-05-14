package the.coyote.api.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import the.coyote.api.model.ConsultaConcorrenteMessage;
import the.coyote.api.model.ConsultaLogMessage;
import the.coyote.api.model.CreditoCriadoMessage;
import the.coyote.api.model.CreditoEntity;
import the.coyote.api.service.KafkaProducerService;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerServiceImpl.class);
    private static final String CONSULTA_LOG_TOPIC = "consulta-log";
    private static final String CONSULTA_CONCORRENTE_TOPIC = "consulta-concorrente";

    private static final String CREDITO_CRIADO_TOPIC = "credito-criado";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerServiceImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void enviarConsultaLog(String numeroNfse, String numeroCredito, boolean sucesso, String ipCliente) {
        ConsultaLogMessage message = new ConsultaLogMessage();
        message.setNumeroNfse(numeroNfse);
        message.setNumeroCredito(numeroCredito);
        message.setIpCliente(ipCliente);
        message.setSucesso(sucesso);
        try {
            kafkaTemplate.send(CONSULTA_LOG_TOPIC, message);
            logger.info("Mensagem de log enviada para o tópico {}: {}", CONSULTA_LOG_TOPIC, message);
        } catch (Exception e) {
            logger.error("Erro ao enviar mensagem de log para o tópico {}: {}", CONSULTA_LOG_TOPIC, e.getMessage());
            throw new RuntimeException("Falha ao enviar mensagem de log para o Kafka", e);
        }
    }

    @Override
    public void enviarConsultaConcorrente(String chave, String ipCliente) {
        ConsultaConcorrenteMessage message = new ConsultaConcorrenteMessage();
        message.setChave(chave);
        message.setIpCliente(ipCliente);
        try {
            kafkaTemplate.send(CONSULTA_CONCORRENTE_TOPIC, message);
            logger.info("Mensagem de consulta concorrente enviada para o tópico {}: {}", CONSULTA_CONCORRENTE_TOPIC, message);
        } catch (Exception e) {
            logger.error("Erro ao enviar mensagem de consulta concorrente para o tópico {}: {}", CONSULTA_CONCORRENTE_TOPIC, e.getMessage());
            throw new RuntimeException("Falha ao enviar mensagem de consulta concorrente para o Kafka", e);
        }
    }

    @Override
    public void enviarCreditoCriado(CreditoEntity credito, String ipCliente) {
        CreditoCriadoMessage message = new CreditoCriadoMessage();
        message.setNumeroCredito(credito.getNumeroCredito());
        message.setIpCliente(ipCliente);
        try {
            kafkaTemplate.send(CREDITO_CRIADO_TOPIC, message);
            logger.info("Mensagem de crédito criado enviada para o tópico {}: {}", CREDITO_CRIADO_TOPIC, message);
        } catch (Exception e) {
            logger.error("Erro ao enviar mensagem de crédito criado para o tópico {}: {}", CREDITO_CRIADO_TOPIC, e.getMessage());
            throw new RuntimeException("Falha ao enviar mensagem de crédito criado para o Kafka", e);
        }
    }
}