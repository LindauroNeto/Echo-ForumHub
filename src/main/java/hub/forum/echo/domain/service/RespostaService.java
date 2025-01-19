package hub.forum.echo.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import hub.forum.echo.domain.dto.DadosAtualizacaoResposta;
import hub.forum.echo.domain.dto.DadosResposta;
import hub.forum.echo.domain.dto.DetalhamentoResposta;
import hub.forum.echo.domain.model.Resposta;
import hub.forum.echo.domain.model.StatusTopicos;
import hub.forum.echo.domain.model.Topicos;
import hub.forum.echo.domain.model.Usuario;
import hub.forum.echo.domain.repository.RespostaRepository;
import hub.forum.echo.domain.repository.TopicosRepository;
import hub.forum.echo.domain.repository.UsuarioRepository;
import hub.forum.echo.infra.exception.RespostaNaoEncontradaException;
import hub.forum.echo.infra.exception.TopicoNaoEncontradoException;
import hub.forum.echo.infra.exception.UsuarioNaoEncontradoException;

@Service
public class RespostaService {
	
	@Autowired
	private TopicosRepository topicosRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RespostaRepository repository;

	public Resposta criarResposta(DadosResposta dadosResposta, Usuario usuario, Long idTopico) {
		var topicoO = validacaoTopico(idTopico);
		
		var usuarioO = usuarioRepository.encontrarUsuario(usuario.getUsuario());
		if (usuarioO.isEmpty()) {
			throw new UsuarioNaoEncontradoException();
		}
		
		topicoO.alterarStatus(StatusTopicos.EM_ABERTO);
		
		var resposta = new Resposta(dadosResposta, usuarioO.get(), topicoO);
		repository.save(resposta);
		return resposta;
	}

	public Page<DetalhamentoResposta> listarRespostas(Long id, Pageable paginacao) {
		var topicoId = validacaoTopico(id);
		return repository.findAllByTopicoIdAndAtivoTrue(topicoId.getId(), paginacao).map(DetalhamentoResposta::new);
	}

	public Resposta verResposta(Long id, Long idResposta) {
		var topicoId = validacaoTopico(id);
		
		var respostaO = repository.findByIdAndTopicoIdAndAtivoTrue(idResposta, topicoId.getId());
		if (respostaO.isEmpty()) {
			throw new RespostaNaoEncontradaException();
		}
		return respostaO.get();
	}

	public Resposta atualizarResposta(Long idTopico, Long idResposta, DadosAtualizacaoResposta dadosAtualizacao) {
		var resposta = verResposta(idTopico, idResposta);
		resposta.atualizar(dadosAtualizacao);
		repository.save(resposta);
		return resposta;
	}

	public void excluirResposta(Long idTopico, Long idResposta) {
		var resposta = verResposta(idTopico, idResposta);
		resposta.excluir();
		repository.save(resposta);
		System.out.println(resposta.getAtivo());
	}
	
	private Topicos validacaoTopico(Long idTopico) {
		if (!topicosRepository.existsById(idTopico)) {
			throw new TopicoNaoEncontradoException();
		}
		return topicosRepository.getReferenceById(idTopico);
	}
}
