package the.coyote.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import the.coyote.api.model.CreditoEntity;

@Service
public interface CreditoService {

    List<CreditoEntity> consultarPorNfse(String numeroNfse, String ipCliente);
    CreditoEntity consultarPorCredito(String numeroCredito, String ipCliente);
    CreditoEntity cadastrarCredito(CreditoEntity credito, String ipCliente);

}
