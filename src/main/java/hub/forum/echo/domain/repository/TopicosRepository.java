package hub.forum.echo.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import hub.forum.echo.domain.model.Topicos;

public interface TopicosRepository extends JpaRepository<Topicos, Long> {

	Page<Topicos> findAllByTopicoAtivoTrue(Pageable paginacao);

	Optional<Topicos> findByIdAndTopicoAtivoTrue(Long id);

}
