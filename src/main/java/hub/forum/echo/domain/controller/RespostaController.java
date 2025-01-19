package hub.forum.echo.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import hub.forum.echo.domain.dto.DadosResposta;
import hub.forum.echo.domain.dto.DetalhamentoResposta;
import hub.forum.echo.domain.service.AtrelarmentoService;
import hub.forum.echo.domain.service.PathUriService;
import hub.forum.echo.domain.service.RespostaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/topicos/{idTopico}/resposta")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Respostas")
public class RespostaController {
	
	@Autowired
	private AtrelarmentoService atrelamento;
	
	@Autowired
	private PathUriService pathUriService;
	
	@Autowired
	private RespostaService service;
	
	@PostMapping
	@Operation(summary = "Envio de resposta", description = "Realização de envio de resposta com base no id do tópico")
	public ResponseEntity<?> responder(@PathVariable Long idTopico, @RequestBody @Valid DadosResposta dadosResposta, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
		var usuarioAtrelado = atrelamento.obterUsuario(request);
		var resposta = service.criarResposta(dadosResposta, usuarioAtrelado, idTopico);
		var uri = pathUriService.criacaoPathUri(uriBuilder, resposta.getId());
		return ResponseEntity.created(uri).body(new DetalhamentoResposta(resposta));
	}
	
	@GetMapping
	public ResponseEntity<Page<DetalhamentoResposta>> verRespostas(@PathVariable Long idTopico, @PageableDefault(size = 15) Pageable paginacao) {
		var respostas = service.listarRespostas(idTopico, paginacao);
		return ResponseEntity.status(HttpStatus.OK).body(respostas);
	}
	
	@GetMapping("/{idResposta}")
	public ResponseEntity<?> verRespostaUnica(@PathVariable Long idTopico, @PathVariable Long idResposta){
		var resposta = service.verResposta(idTopico, idResposta);
		return ResponseEntity.status(HttpStatus.OK).body(resposta);
	}

}
