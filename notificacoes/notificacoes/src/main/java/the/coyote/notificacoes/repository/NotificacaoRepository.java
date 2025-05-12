package the.coyote.notificacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import the.coyote.notificacoes.model.NotificacaoEntity;

public interface NotificacaoRepository extends JpaRepository<NotificacaoEntity, String> {

}
