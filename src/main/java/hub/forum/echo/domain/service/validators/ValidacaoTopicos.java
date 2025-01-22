package hub.forum.echo.domain.service.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hub.forum.echo.domain.model.StatusTopicos;
import hub.forum.echo.domain.model.Topicos;
import hub.forum.echo.domain.repository.TopicosRepository;
import hub.forum.echo.infra.exception.TopicoFinalizadoException;
import hub.forum.echo.infra.exception.TopicoNaoEncontradoException;

@Component
public class ValidacaoTopicos {

	@Autowired
	private TopicosRepository repository;
	
	public Topicos validacaoTopicoPorId(Long idTopico) {
		var topicoO = repository.findByIdAndTopicoAtivoTrue(idTopico);
		if (topicoO.isEmpty()) {
			throw new TopicoNaoEncontradoException();
		}
		return repository.getReferenceById(topicoO.get().getId());
	}
	
	public void validacaoTopicoFinalizado(Topicos topico) {
		if (topico.getStatus() == StatusTopicos.RESOLVIDO) {
			throw new TopicoFinalizadoException("O tópico já foi resolvido e não aceita mais respostas");
		}
		if (topico.getStatus() == StatusTopicos.ENCERRADO) {
			throw new TopicoFinalizadoException("O tópico foi encerrado e não recebe mais respotas");
		}
		
	}
}