package hub.forum.echo.domain.service.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hub.forum.echo.domain.model.StatusTopicos;
import hub.forum.echo.domain.model.Topicos;
import hub.forum.echo.domain.repository.TopicosRepository;
import hub.forum.echo.infra.exception.TopicoEncerradoResolvidoExcpetion;
import hub.forum.echo.infra.exception.TopicoNaoEncontradoException;

@Service
public class ValidacaoTopicos {

	@Autowired
	private TopicosRepository repository;
	
	public Topicos validacaoTopicoPorId(Long idTopico) {
		var topicoO = repository.findByIdAndTopicoAtivoTrue(idTopico);
		if (topicoO.isEmpty()) {
			throw new TopicoNaoEncontradoException();
		}
		return repository.getReferenceById(idTopico);
	}
	
	public void validacaoTopicoFinalizado(Topicos topico) {
		if (topico.getStatus() == StatusTopicos.RESOLVIDO || topico.getStatus() == StatusTopicos.ENCERRADO) {
			throw new TopicoEncerradoResolvidoExcpetion();
		}
	}
}
