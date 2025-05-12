package the.coyote.api.service.impl;

import java.util.List;

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
        kafkaProducerService.enviarConsultaLog(numeroNfse, null, true);
        kafkaProducerService.enviarConsultaConcorrente("NFSE:" + numeroNfse);
        return repository.findByNumeroNfse(numeroNfse);
    }

    @Override
    public CreditoEntity consultarPorCredito(String numeroCredito, String ipCliente) {
        kafkaProducerService.enviarConsultaLog(null, numeroCredito, true);
        kafkaProducerService.enviarConsultaConcorrente("CRED:" + numeroCredito);
        return repository.findByNumeroCredito(numeroCredito);
    }

    @Override
    public CreditoEntity cadastrarCredito(CreditoEntity credito, String ipCliente) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cadastrarCredito'");
    }
    
}