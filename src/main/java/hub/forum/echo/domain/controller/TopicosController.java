package hub.forum.echo.domain.controller;

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

import hub.forum.echo.domain.dto.AtualizacaoTopicoDTO;
import hub.forum.echo.domain.dto.DetalhamentoFinalizacaoTopico;
import hub.forum.echo.domain.dto.DetalhamentoRespostaSimples;
import hub.forum.echo.domain.dto.TopicoDTO;
import hub.forum.echo.domain.dto.DetalhamentoTopicos;
import hub.forum.echo.domain.dto.RespostaDTO;
import hub.forum.echo.domain.service.AtrelarmentoService;
import hub.forum.echo.domain.service.PathUriService;
import hub.forum.echo.domain.service.RespostaService;
import hub.forum.echo.domain.service.TopicosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Tópicos")
public class TopicosController {
	
	@Autowired
	private TopicosService service;
	
	@Autowired
	private PathUriService pathUriService;
	
	@Autowired
	private AtrelarmentoService atrelamento;
	
	@Autowired
	private RespostaService respostaService;

	@PostMapping
	@Operation(summary = "Criação de tópico", description = "Cadastro de novo tópico")
	public ResponseEntity<?> cadastro(@RequestBody @Valid TopicoDTO dadosCadastroTopico, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
		var usuario = atrelamento.obterUsuario(request);
		var topico = service.criacaoTopico(dadosCadastroTopico, usuario);
		var uri = pathUriService.criacaoPathUri(uriBuilder, topico.getId());
		return ResponseEntity.created(uri).body(new DetalhamentoTopicos(topico));
	}
	
	@GetMapping
	@Operation(summary = "Listagem de tópicos", description = "Retorno de todos os tópicos cadastrados de forma paginada")
	public ResponseEntity<Page<DetalhamentoTopicos>> listarTodos(@PageableDefault(size = 15, sort = "data", direction = Direction.DESC) Pageable paginacao) {
		var pagina = service.listarPaginas(paginacao);
		return ResponseEntity.status(HttpStatus.OK).body(pagina);
	}
	
	@GetMapping("/{idTopico}")
	@Operation(summary = "Obtenção tópico", description = "Obtenção de tópico único, por meio do id")
	public ResponseEntity<?> listarUm(@PathVariable Long idTopico) {
		var topico = service.verTopicoAtivo(idTopico);
		return ResponseEntity.status(HttpStatus.OK).body(new DetalhamentoTopicos(topico));
	}
	
	@PutMapping("/{idTopico}")
	@Operation(summary = "Atualização de tópico", description = "Atualização de tópico cadastrado")
	public ResponseEntity<?> atualizar(@PathVariable Long idTopico, @RequestBody @Valid AtualizacaoTopicoDTO dadosAtualizacaoTopico) {
		var topico = service.atualizarTopico(idTopico, dadosAtualizacaoTopico);
		return ResponseEntity.status(HttpStatus.OK).body(new DetalhamentoTopicos(topico));
	}
	
	@DeleteMapping("/{idTopico}")
	@Operation(summary = "Excluir tópico", description = "Exclusão lógica de tópico")
	public ResponseEntity<?> deletar(@PathVariable Long idTopico) {
		service.excluirTopico(idTopico);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PutMapping("/{idTopico}/finalizar")
	@Operation(summary = "Finalizar tópico", description = "Envio de resposta final do tópico")
	public ResponseEntity<?> finalizar(@PathVariable Long idTopico, @RequestBody @Valid RespostaDTO dadosResposta, HttpServletRequest request) {
		var usuarioAtrelado = atrelamento.obterUsuario(request);
		var resposta = respostaService.finalizarTopico(dadosResposta, usuarioAtrelado, idTopico);
		return ResponseEntity.status(HttpStatus.OK).body(new DetalhamentoFinalizacaoTopico(resposta.getTopico(), new DetalhamentoRespostaSimples(resposta)));
	}
}
