package hub.echo.EchoForumHub.domain.topicos;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicosRepository extends JpaRepository<Topicos, Long> {

	Page<Topicos> findAllByTopicoAtivoTrue(Pageable paginacao);

	Optional<Topicos> findByIdAndTopicoAtivoTrue(Long id);

}
