package hub.forum.echo.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import hub.forum.echo.domain.dto.DadosCadastroTopico;
import hub.forum.echo.domain.dto.DetalhamentoTopicos;
import hub.forum.echo.domain.model.Topicos;
import hub.forum.echo.domain.repository.TopicosRepository;

@Service
public class TopicosService {

	@Autowired
	private TopicosRepository repository;
	
	public Topicos criacaoTopico(DadosCadastroTopico dadosCadastroTopico) {
		var topico = new Topicos(dadosCadastroTopico);
		repository.save(topico);
		return topico;
	}
	
	public Page<DetalhamentoTopicos> listarPaginas(Pageable paginacao) {
		return repository.findAllByTopicoAtivoTrue(paginacao).map(DetalhamentoTopicos::new);
	}
	
	public Optional<Topicos> verTopicoAtivo(Long id) {
		return repository.findByIdAndTopicoAtivoTrue(id);
	}
	
	public Optional<Topicos> verTopico(Long id) {
		return repository.findById(id);
	}
}
