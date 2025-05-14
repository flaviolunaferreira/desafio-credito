package the.coyote.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import the.coyote.api.model.CreditoEntity;
import the.coyote.api.repository.CreditoRepository;
import the.coyote.api.service.CreditoService;
import the.coyote.api.service.KafkaProducerService;

@Service
@RequiredArgsConstructor
public class CreditoServiceImpl implements CreditoService {

    private final CreditoRepository repository;
    private final KafkaProducerService kafkaProducerService;

    @Override
    public List<CreditoEntity> consultarPorNfse(String numeroNfse, String ipCliente) {
        kafkaProducerService.enviarConsultaLog(numeroNfse, null, true, ipCliente);
        kafkaProducerService.enviarConsultaConcorrente("NFSE:" + numeroNfse, ipCliente);
        return repository.findByNumeroNfse(numeroNfse);
    }

    @Override
    public CreditoEntity consultarPorCredito(String numeroCredito, String ipCliente) {
        kafkaProducerService.enviarConsultaLog(null, numeroCredito, true, ipCliente);
        kafkaProducerService.enviarConsultaConcorrente("CRED:" + numeroCredito, ipCliente);
        return repository.findByNumeroCredito(numeroCredito)
                .orElse(null);
    }

    @Override
    public CreditoEntity cadastrarCredito(CreditoEntity credito, String ipCliente) {
        if (credito.getNumeroCredito() == null || credito.getNumeroNfse() == null) {
            throw new IllegalArgumentException("Número do crédito e NFS-e são obrigatórios");
        }
        if (repository.findByNumeroCredito(credito.getNumeroCredito()).isPresent()) {
            throw new IllegalArgumentException("Crédito com número " + credito.getNumeroCredito() + " já existe");
        }
        CreditoEntity saved = repository.save(credito);
        kafkaProducerService.enviarCreditoCriado(saved, ipCliente);
        return saved;
    }

    @Override
    public CreditoEntity atualizarCredito(String numeroCredito, CreditoEntity credito, String ipCliente) {
        Optional<CreditoEntity> existing = repository.findByNumeroCredito(numeroCredito);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Crédito com número " + numeroCredito + " não encontrado");
        }
        CreditoEntity entity = existing.get();
        entity.setNumeroNfse(credito.getNumeroNfse());
        entity.setDataConstituicao(credito.getDataConstituicao());
        entity.setValorIssqn(credito.getValorIssqn());
        entity.setTipoCredito(credito.getTipoCredito());
        entity.setSimplesNacional(credito.isSimplesNacional());
        entity.setAliquota(credito.getAliquota());
        entity.setValorFaturado(credito.getValorFaturado());
        entity.setValorDeducao(credito.getValorDeducao());
        entity.setBaseCalculo(credito.getBaseCalculo());
        CreditoEntity updated = repository.save(entity);
        kafkaProducerService.enviarCreditoCriado(updated, ipCliente);
        return updated;
    }

    @Override
    public void deletarCredito(String numeroCredito, String ipCliente) {
        Optional<CreditoEntity> existing = repository.findByNumeroCredito(numeroCredito);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Crédito com número " + numeroCredito + " não encontrado");
        }
        repository.delete(existing.get());
        kafkaProducerService.enviarConsultaLog(null, numeroCredito, false, ipCliente);
    }
}