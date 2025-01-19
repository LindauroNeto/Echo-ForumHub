package hub.forum.echo.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import hub.forum.echo.domain.dto.DadosCadastroTopico;
import hub.forum.echo.domain.dto.DetalhamentoTopicos;
import hub.forum.echo.domain.model.Topicos;
import hub.forum.echo.domain.repository.TopicosRepository;
import hub.forum.echo.infra.exception.TopicoNaoEncontradoException;

@Service
public class TopicosService {

	@Autowired
	private TopicosRepository repository;
	
	public Topicos criacaoTopico(DadosCadastroTopico dadosCadastroTopico, String usuario) {
		var topico = new Topicos(dadosCadastroTopico, usuario);
		repository.save(topico);
		return topico;
	}
	
	public Page<DetalhamentoTopicos> listarPaginas(Pageable paginacao) {
		return repository.findAllByTopicoAtivoTrue(paginacao).map(DetalhamentoTopicos::new);
	}
	
	public Topicos verTopicoAtivo(Long id) {
		var topicoO = repository.findByIdAndTopicoAtivoTrue(id);
		if (topicoO.isEmpty()) {
			throw new TopicoNaoEncontradoException("Tópcio não encontrado");
		}
		return topicoO.get();
	}

}
