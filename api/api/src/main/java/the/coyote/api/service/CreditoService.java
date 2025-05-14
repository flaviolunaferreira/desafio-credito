package the.coyote.api.service;

import java.util.List;

import the.coyote.api.model.CreditoEntity;

public interface CreditoService {
    List<CreditoEntity> consultarPorNfse(String numeroNfse, String ipCliente);
    CreditoEntity consultarPorCredito(String numeroCredito, String ipCliente);
    CreditoEntity cadastrarCredito(CreditoEntity credito, String ipCliente);
    CreditoEntity atualizarCredito(String numeroCredito, CreditoEntity credito, String ipCliente);
    void deletarCredito(String numeroCredito, String ipCliente);
}