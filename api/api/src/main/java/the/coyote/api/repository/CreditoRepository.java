package the.coyote.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import the.coyote.api.model.CreditoEntity;

public interface CreditoRepository extends JpaRepository<CreditoEntity, String> {
    List<CreditoEntity> findByNumeroNfse(String numeroNfse);
    CreditoEntity findByNumeroCredito(String numeroCredito);
}
