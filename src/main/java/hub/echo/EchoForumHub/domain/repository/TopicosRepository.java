package hub.echo.EchoForumHub.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import hub.echo.EchoForumHub.domain.model.Topicos;

public interface TopicosRepository extends JpaRepository<Topicos, Long> {

	Page<Topicos> findAllByTopicoAtivoTrue(Pageable paginacao);

	Optional<Topicos> findByIdAndTopicoAtivoTrue(Long id);

}
