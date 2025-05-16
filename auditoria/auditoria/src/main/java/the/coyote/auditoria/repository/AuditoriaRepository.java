package the.coyote.auditoria.repository;

import java.util.Optional;

import the.coyote.auditoria.model.AuditoriaEntity;

public interface AuditoriaRepository {

    Optional<AuditoriaEntity> findByChaveConsulta(String chaveConsulta);

    void save(AuditoriaEntity auditoria);

}
