package the.coyote.notificacoes.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import the.coyote.notificacoes.model.CreditoCriadoMessage;
import the.coyote.notificacoes.model.NotificacaoEntity;
import the.coyote.notificacoes.repository.NotificacaoRepository;
import the.coyote.notificacoes.service.KafkaConsumerService;

@Service
@RequiredArgsConstructor
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    private final NotificacaoRepository repository;

    @Override
    @KafkaListener(topics = "credito-criado", groupId = "notificacoes-grupo")
    public void consumeCreditoCriado(CreditoCriadoMessage message, Acknowledgment ack) {
        // Verificar se a notificação já existe
        if (repository.findByNumeroCredito(message.getNumeroCredito()).isEmpty()) {
            NotificacaoEntity notificacao = new NotificacaoEntity();
            notificacao.setId(UUID.randomUUID().toString());
            notificacao.setNumeroCredito(message.getNumeroCredito());
            notificacao.setIpCliente(message.getIpCliente());
            notificacao.setTimestamp(LocalDateTime.now());
            repository.save(notificacao);
            System.out.println("Notificação salva para crédito: " + message.getNumeroCredito());
        } else {
            System.out.println("Notificação duplicada ignorada para crédito: " + message.getNumeroCredito());
        }
        ack.acknowledge(); // Commit manual do offset
    }
}