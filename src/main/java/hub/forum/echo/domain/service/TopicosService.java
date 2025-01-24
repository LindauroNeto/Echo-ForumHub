package hub.forum.echo.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import hub.forum.echo.domain.dto.AtualizacaoTopico;
import hub.forum.echo.domain.dto.RespostaDTO;
import hub.forum.echo.domain.dto.TopicoDTO;
import hub.forum.echo.domain.dto.details.DetalhamentoTopicos;
import hub.forum.echo.domain.model.Resposta;
import hub.forum.echo.domain.model.StatusTopicos;
import hub.forum.echo.domain.model.Topicos;
import hub.forum.echo.domain.model.Usuario;
import hub.forum.echo.domain.repository.RespostaRepository;
import hub.forum.echo.domain.repository.TopicosRepository;
import hub.forum.echo.domain.service.validators.ValidacaoTopicos;
import hub.forum.echo.domain.service.validators.ValidacaoUsuarios;

@Service
public class TopicosService {

	@Autowired
	private TopicosRepository repository;
	
	@Autowired
	private RespostaRepository respostaRepository;
	
	@Autowired
	private ValidacaoTopicos validacaoTopicos;
	
	@Autowired
	private ValidacaoUsuarios validacaoUsuarios;
	
	public Topicos criacaoTopico(TopicoDTO dadosCadastroTopico, Usuario usuario) {
		var topico = new Topicos(dadosCadastroTopico, usuario);
		repository.save(topico);
		return topico;
	}
	
	public Page<DetalhamentoTopicos> listarPaginas(Pageable paginacao) {
		return repository.findAllByTopicoAtivoTrue(paginacao).map(DetalhamentoTopicos::new);
	}
	
	public Topicos verTopico(Long id) {
		var topico = validacaoTopicos.validacaoTopico(id);
		return topico;
	}
	
	public Topicos verTopicoAtivo(Long id) {
		var topico = validacaoTopicos.validacaoTopicoPorId(id);
		return topico;
	}

	public Topicos atualizarTopico(Long idTopico, AtualizacaoTopico dadosAtualizacaoTopico, Usuario usuario) {
		var topico = confirmacaoUsuarioRequisicao(idTopico, usuario);
		
		topico.atualizar(dadosAtualizacaoTopico);
		repository.save(topico);
		return topico;
	}

	public void excluirTopico(Long idTopico, Usuario usuario) {
		var topico = confirmacaoUsuarioRequisicao(idTopico, usuario);
		
		topico.excluir();
		repository.save(topico);
	}
	
	public Resposta finalizarTopico(RespostaDTO dadosResposta, Usuario usuario, Long idTopico) {
		var topico = confirmacaoUsuarioRequisicao(idTopico, usuario);
		
		topico.alterarStatus(StatusTopicos.RESOLVIDO);
		
		var resposta = new Resposta(dadosResposta, topico.getAutor(), topico);
		respostaRepository.save(resposta);
		return resposta;
	}
	
	private Topicos confirmacaoUsuarioRequisicao(Long idTopico, Usuario usuario) {
		var topico = verTopicoAtivo(idTopico);
		var usuarioO = validacaoUsuarios.validacaoUsuarioPorNome(usuario.getUsuario());
		
		validacaoTopicos.validacaoTopicoFinalizado(topico);
		validacaoUsuarios.validacaoUsuarioUsuario(topico.getAutor(), usuarioO);
		return topico;
	}
	
}
