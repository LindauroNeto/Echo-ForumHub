package hub.forum.echo.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import hub.forum.echo.domain.dto.AtualizacaoResposta;
import hub.forum.echo.domain.dto.RespostaDTO;
import hub.forum.echo.domain.dto.details.DetalhamentoResposta;
import hub.forum.echo.domain.model.Resposta;
import hub.forum.echo.domain.model.StatusTopicos;
import hub.forum.echo.domain.model.Usuario;
import hub.forum.echo.domain.repository.RespostaRepository;
import hub.forum.echo.domain.service.validators.ValidacaoResposta;
import hub.forum.echo.domain.service.validators.ValidacaoTopicos;
import hub.forum.echo.domain.service.validators.ValidacaoUsuarios;

@Service
public class RespostaService {
	
	@Autowired
	private RespostaRepository repository;
	
	@Autowired
	private ValidacaoTopicos validadorTopicos;
	
	@Autowired
	private ValidacaoUsuarios validadorUsuarios;
	
	@Autowired
	private ValidacaoResposta validadorResposta;

	public Resposta criarResposta(RespostaDTO dadosResposta, Usuario usuario, Long idTopico) {
		var topico = validadorTopicos.validacaoTopicoPorId(idTopico);
		var usuarioO = validadorUsuarios.validacaoUsuarioPorNome(usuario.getUsuario());
		
		validadorTopicos.validacaoTopicoFinalizado(topico);
		
		topico.alterarStatus(StatusTopicos.EM_ABERTO);
		
		var resposta = new Resposta(dadosResposta, usuarioO, topico);
		repository.save(resposta);
		return resposta;
	}

	public Page<DetalhamentoResposta> listarRespostas(Long id, Pageable paginacao) {
		var topicoId = validadorTopicos.validacaoTopicoPorId(id);
		return repository.findAllByTopicoIdAndAtivoTrue(topicoId.getId(), paginacao).map(DetalhamentoResposta::new);
	}

	public Resposta verResposta(Long id, Long idResposta) {
		var topicoId = validadorTopicos.validacaoTopicoPorId(id);
		return validadorResposta.validacaoRespostaTopicoPorIds(topicoId.getId(), idResposta);
	}

	public Resposta atualizarResposta(Long idTopico, Long idResposta, AtualizacaoResposta dadosAtualizacao) {
		var resposta = verResposta(idTopico, idResposta);
		resposta.atualizar(dadosAtualizacao);
		repository.save(resposta);
		return resposta;
	}

	public void excluirResposta(Long idTopico, Long idResposta) {
		var resposta = verResposta(idTopico, idResposta);
		resposta.excluir();
		repository.save(resposta);
	}
	
}
	
