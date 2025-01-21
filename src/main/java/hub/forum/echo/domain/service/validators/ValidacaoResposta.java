package hub.forum.echo.domain.service.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hub.forum.echo.domain.model.Resposta;
import hub.forum.echo.domain.repository.RespostaRepository;
import hub.forum.echo.infra.exception.RespostaNaoEncontradaException;

@Component
public class ValidacaoResposta {
	
	@Autowired
	private RespostaRepository repository;
	
	public Resposta validacaoRespostaTopicoPorIds(Long idTopico, Long idResposta) {
		var respostaO = repository.findByIdAndTopicoIdAndAtivoTrue(idResposta, idTopico);
		if (respostaO.isEmpty()) {
			throw new RespostaNaoEncontradaException();
		}
		return respostaO.get();
	}

}
