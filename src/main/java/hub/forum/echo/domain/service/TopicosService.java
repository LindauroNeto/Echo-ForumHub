package hub.forum.echo.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import hub.forum.echo.domain.dto.AtualizacaoTopicoDTO;
import hub.forum.echo.domain.dto.DetalhamentoTopicos;
import hub.forum.echo.domain.dto.RespostaDTO;
import hub.forum.echo.domain.dto.TopicoDTO;
import hub.forum.echo.domain.model.Resposta;
import hub.forum.echo.domain.model.StatusTopicos;
import hub.forum.echo.domain.model.Topicos;
import hub.forum.echo.domain.model.Usuario;
import hub.forum.echo.domain.repository.RespostaRepository;
import hub.forum.echo.domain.repository.TopicosRepository;
import hub.forum.echo.infra.exception.UsuarioNaoEhAutorException;

@Service
public class TopicosService {

	@Autowired
	private TopicosRepository repository;
	
	@Autowired
	private RespostaRepository respostaRepository;
	
	@Autowired
	private ValidacaoTopicosUsuariosService validacao;
	
	public Topicos criacaoTopico(TopicoDTO dadosCadastroTopico, Usuario usuario) {
		var topico = new Topicos(dadosCadastroTopico, usuario);
		repository.save(topico);
		return topico;
	}
	
	public Page<DetalhamentoTopicos> listarPaginas(Pageable paginacao) {
		return repository.findAllByTopicoAtivoTrue(paginacao).map(DetalhamentoTopicos::new);
	}
	
	public Topicos verTopicoAtivo(Long id) {
		var topico = validacao.validacaoTopicoPorId(id);
		return topico;
	}

	public Topicos atualizarTopico(Long idTopico, AtualizacaoTopicoDTO dadosAtualizacaoTopico) {
		var topico = verTopicoAtivo(idTopico);
		topico.atualizar(dadosAtualizacaoTopico);
		repository.save(topico);
		return topico;
	}

	public void excluirTopico(Long idTopico) {
		var topico = verTopicoAtivo(idTopico);
		topico.excluir();
		repository.save(topico);
	}
	
	public Resposta finalizarTopico(RespostaDTO dadosResposta, Usuario usuario, Long idTopico) {
		var topico = validacao.validacaoTopicoPorId(idTopico);
		var usuarioO = validacao.validacaoUsuarioPorNome(usuario.getUsuario());
		
		validacao.validacaoTopicoFinalizado(topico);
		
		if (!(topico.getAutor().getUsuario() == usuarioO.getUsuario())) {
			throw new UsuarioNaoEhAutorException();
		}
		
		topico.alterarStatus(StatusTopicos.RESOLVIDO);
		
		var resposta = new Resposta(dadosResposta, usuarioO, topico);
		respostaRepository.save(resposta);
		return resposta;
	}
	
}
