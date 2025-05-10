package the.coyote.api.service.impl;


import the.coyote.api.model.CreditoEntity;
import the.coyote.api.repository.CreditoRepository;
import the.coyote.api.service.CreditoService;
import the.coyote.api.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditoServiceImpl implements CreditoService {

        private final CreditoRepository repository;
    private final KafkaProducerService kafkaProducerService;

    @Override
    public List<CreditoEntity> consultarPorNfse(String numeroNfse, String ipCliente) {
        kafkaProducerService.enviarConsultaLog(numeroNfse, null, ipCliente, true);
        kafkaProducerService.enviarConsultaConcorrente("NFSE:" + numeroNfse, ipCliente);
        return repository.findByNumeroNfse(numeroNfse);
    }

    @Override
    public CreditoEntity consultarPorCredito(String numeroCredito, String ipCliente) {
        kafkaProducerService.enviarConsultaLog(null, numeroCredito, ipCliente, true);
        kafkaProducerService.enviarConsultaConcorrente("CRED:" + numeroCredito, ipCliente);
        return repository.findByNumeroCredito(numeroCredito);
    }

    @Override
    public CreditoEntity cadastrarCredito(CreditoEntity credito, String ipCliente) {
        CreditoEntity salvo = repository.save(credito);
        kafkaProducerService.enviarCreditoCriado(salvo, ipCliente);
        return salvo;
    }

}
