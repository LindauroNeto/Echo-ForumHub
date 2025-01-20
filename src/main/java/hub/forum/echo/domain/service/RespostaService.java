package hub.forum.echo.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import hub.forum.echo.domain.dto.AtualizacaoRespostaDTO;
import hub.forum.echo.domain.dto.DetalhamentoResposta;
import hub.forum.echo.domain.dto.RespostaDTO;
import hub.forum.echo.domain.model.Resposta;
import hub.forum.echo.domain.model.StatusTopicos;
import hub.forum.echo.domain.model.Usuario;
import hub.forum.echo.domain.repository.RespostaRepository;
import hub.forum.echo.infra.exception.RespostaNaoEncontradaException;

@Service
public class RespostaService {
	
	@Autowired
	private RespostaRepository repository;
	
	@Autowired
	private ValidacaoTopicosUsuariosService validador;

	public Resposta criarResposta(RespostaDTO dadosResposta, Usuario usuario, Long idTopico) {
		var topico = validador.validacaoTopicoPorId(idTopico);
		var usuarioO = validador.validacaoUsuarioPorNome(usuario.getUsuario());
		
		validador.validacaoTopicoFinalizado(topico);
		
		topico.alterarStatus(StatusTopicos.EM_ABERTO);
		
		var resposta = new Resposta(dadosResposta, usuarioO, topico);
		repository.save(resposta);
		return resposta;
	}

	public Page<DetalhamentoResposta> listarRespostas(Long id, Pageable paginacao) {
		var topicoId = validador.validacaoTopicoPorId(id);
		return repository.findAllByTopicoIdAndAtivoTrue(topicoId.getId(), paginacao).map(DetalhamentoResposta::new);
	}

	public Resposta verResposta(Long id, Long idResposta) {
		var topicoId = validador.validacaoTopicoPorId(id);
		
		var respostaO = repository.findByIdAndTopicoIdAndAtivoTrue(idResposta, topicoId.getId());
		if (respostaO.isEmpty()) {
			throw new RespostaNaoEncontradaException();
		}
		return respostaO.get();
	}

	public Resposta atualizarResposta(Long idTopico, Long idResposta, AtualizacaoRespostaDTO dadosAtualizacao) {
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
	
