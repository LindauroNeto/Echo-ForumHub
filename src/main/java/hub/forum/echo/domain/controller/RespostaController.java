package hub.forum.echo.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import hub.forum.echo.domain.dto.DadosResposta;
import hub.forum.echo.domain.dto.DetalhamentoResposta;
import hub.forum.echo.domain.service.AtrelarUsuarioService;
import hub.forum.echo.domain.service.PathUriService;
import hub.forum.echo.domain.service.RespostaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/topicos/")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Respostas")
public class RespostaController {
	
	@Autowired
	private AtrelarUsuarioService atrelamento;
	
	@Autowired
	private PathUriService pathUriService;
	
	@Autowired
	private RespostaService service;
	
	@PostMapping("{id}/resposta")
	public ResponseEntity<?> responder(@PathVariable Long id, @RequestBody @Valid DadosResposta dadosResposta, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
		var usuarioAtrelado = atrelamento.obterUsuario(request);
		var resposta = service.criarResposta(dadosResposta, usuarioAtrelado, id);
		var uri = pathUriService.criacaoPathUri(uriBuilder, resposta.getId());
		return ResponseEntity.created(uri).body(new DetalhamentoResposta(resposta));
	}

}
