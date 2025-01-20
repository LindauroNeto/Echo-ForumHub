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
import hub.forum.echo.infra.exception.UsuarioNaoEhAutorException;

@Service
public class RespostaService {
	
	@Autowired
	private RespostaRepository repository;
	
	@Autowired
	private ValidacaoTopicosUsuariosService validador;

	public Resposta criarResposta(RespostaDTO dadosResposta, Usuario usuario, Long idTopico) {
		var topicoO = validador.validacaoTopico(idTopico);
		var usuarioO = validador.validacaoUsuario(usuario);
		
		validador.validarTopicoFinalizado(topicoO);
		
		topicoO.alterarStatus(StatusTopicos.EM_ABERTO);
		
		var resposta = new Resposta(dadosResposta, usuarioO, topicoO);
		repository.save(resposta);
		return resposta;
	}

	public Page<DetalhamentoResposta> listarRespostas(Long id, Pageable paginacao) {
		var topicoId = validador.validacaoTopico(id);
		return repository.findAllByTopicoIdAndAtivoTrue(topicoId.getId(), paginacao).map(DetalhamentoResposta::new);
	}

	public Resposta verResposta(Long id, Long idResposta) {
		var topicoId = validador.validacaoTopico(id);
		
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
	
	public Resposta finalizarTopico(RespostaDTO dadosResposta, Usuario usuario, Long idTopico) {
		var topico = validador.validacaoTopico(idTopico);
		var usuarioO = validador.validacaoUsuario(usuario);
		
		validador.validarTopicoFinalizado(topico);
		
		if (!(topico.getAutor().getUsuario() == usuarioO.getUsuario())) {
			throw new UsuarioNaoEhAutorException();
		}
		
		topico.alterarStatus(StatusTopicos.RESOLVIDO);
		
		var resposta = new Resposta(dadosResposta, usuarioO, topico);
		repository.save(resposta);
		return resposta;
	}
}
