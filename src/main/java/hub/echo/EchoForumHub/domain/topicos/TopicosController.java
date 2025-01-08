package hub.echo.EchoForumHub.domain.topicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	private TopicosRepository topicosRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastro(@RequestBody @Valid DadosCadastroTopico dadosCadastroTopico, UriComponentsBuilder uriBuilder) {
		var topico = new Topicos(dadosCadastroTopico);
		topicosRepository.save(topico);
		
		var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new DetalhamentoTopicos(topico));
	}
	
	@GetMapping
	public ResponseEntity<Page<DetalhamentoTopicos>> listarTopicos(@PageableDefault(size = 15, sort = "data", direction = Direction.DESC) Pageable paginacao) {
		var pagina = topicosRepository.findAllByTopicoAtivoTrue(paginacao).map(DetalhamentoTopicos::new);
		return ResponseEntity.ok(pagina);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> listarUm(@PathVariable Long id) {
		var topico = topicosRepository.getReferenceById(id);
		return ResponseEntity.ok(new DetalhamentoTopicos(topico));
	}
}
