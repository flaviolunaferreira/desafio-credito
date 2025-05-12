package the.coyote.auditoria.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import the.coyote.auditoria.model.ConsultaLogEntity;

public interface ConsultaLogRepository extends MongoRepository<ConsultaLogEntity, String>{

}
