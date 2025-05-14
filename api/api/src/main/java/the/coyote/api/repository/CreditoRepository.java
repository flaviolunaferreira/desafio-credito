package the.coyote.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import the.coyote.api.model.CreditoEntity;

public interface CreditoRepository extends JpaRepository<CreditoEntity, Long> {
    List<CreditoEntity> findByNumeroNfse(String numeroNfse);
    Optional<CreditoEntity> findByNumeroCredito(String numeroCredito);
}