package the.coyote.notificacoes.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import the.coyote.notificacoes.model.NotificacaoEntity;
import the.coyote.notificacoes.repository.NotificacaoRepository;
import the.coyote.notificacoes.service.KafkaConsumerService;

@Service
@RequiredArgsConstructor
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    private final NotificacaoRepository repository;

    @Override
    @KafkaListener(topics = "credito-criado", groupId = "notificacoes-grupo")
    public void consumeCreditoCriado(CreditoCriadoMessage message) {
        NotificacaoEntity notificacao = new NotificacaoEntity();
        notificacao.setId(UUID.randomUUID().toString());
        notificacao.setNumeroCredito(message.getNumeroCredito());
        notificacao.setIpCliente(message.getIpCliente());
        notificacao.setTimestamp(LocalDateTime.now());
        repository.save(notificacao);
        System.out.println("Notificação salva para crédito: " + message.getNumeroCredito());
    }

    @Data
    public static class CreditoCriadoMessage {
        private String numeroCredito;
        private String ipCliente;
    }

}
