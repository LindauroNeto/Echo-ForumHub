package hub.echo.EchoForumHub.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import hub.echo.EchoForumHub.domain.dto.DadosAtualizacaoTopico;
import hub.echo.EchoForumHub.domain.dto.DadosCadastroTopico;
import hub.echo.EchoForumHub.domain.dto.DetalhamentoTopicos;
import hub.echo.EchoForumHub.domain.model.Topicos;
import hub.echo.EchoForumHub.domain.repository.TopicosRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Tópicos")
public class TopicosController {
	
	@Autowired
	private TopicosRepository topicosRepository;

	@PostMapping
	@Transactional
	@Operation(summary = "Criação de tópico", description = "Cadastro de novo tópico")
	public ResponseEntity<?> cadastro(@RequestBody @Valid DadosCadastroTopico dadosCadastroTopico, UriComponentsBuilder uriBuilder) {
		var topico = new Topicos(dadosCadastroTopico);
		topicosRepository.save(topico);
		
		var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new DetalhamentoTopicos(topico));
	}
	
	@GetMapping
	@Operation(summary = "Listagem de tópicos", description = "Retorno de todos os tópicos cadastrados de forma paginada")
	public ResponseEntity<Page<DetalhamentoTopicos>> listarTopicos(@PageableDefault(size = 15, sort = "data", direction = Direction.DESC) Pageable paginacao) {
		var pagina = topicosRepository.findAllByTopicoAtivoTrue(paginacao).map(DetalhamentoTopicos::new);
		return ResponseEntity.status(HttpStatus.OK).body(pagina);
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Obtenção tópico", description = "Obtenção de tópico único, por meio do id")
	public ResponseEntity<?> listarUm(@PathVariable Long id) {
		var topico = topicosRepository.findByIdAndTopicoAtivoTrue(id);
		if (topico.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tópico não encontrado");
		}
		return ResponseEntity.status(HttpStatus.OK).body(new DetalhamentoTopicos(topico.get()));
	}
	
	@PutMapping("/{id}")
	@Transactional
	@Operation(summary = "Atualização de tópico", description = "Atualização de tópico cadastrado")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoTopico dadosAtualizacaoTopico) {
		var topico = topicosRepository.findById(id);
		if (topico.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tópico não encontrado");
		}
		topico.get().atualizar(dadosAtualizacaoTopico);
		return ResponseEntity.status(HttpStatus.OK).body(new DetalhamentoTopicos(topico.get()));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@Operation(summary = "Excluir tópico", description = "Exclusão lógica de tópico")
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		var topico = topicosRepository.findById(id);
		if (topico.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tópico não encontrado");
		}
		topico.get().excluir();
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
