package hub.forum.echo.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import hub.forum.echo.domain.model.Resposta;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {

	Page<Resposta> findAllByTopicoIdAndAtivoTrue(Long id, Pageable paginacao);

	Optional<Resposta> findByIdAndTopicoId(Long idResposta, Long id);

}
