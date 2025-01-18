package hub.forum.echo.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import hub.forum.echo.domain.dto.DadosCadastroLogin;
import hub.forum.echo.domain.dto.DadosDetalhamentoUsuario;
import hub.forum.echo.domain.dto.DadosTokenJwt;
import hub.forum.echo.domain.service.PathUriService;
import hub.forum.echo.domain.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuários")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	@Autowired
	private PathUriService pathUriService;
	
	@PostMapping("/login")
	@Operation(summary = "Login de usuário", description = "Login de usuário para acesso do fórum")
	public ResponseEntity<?> login(@RequestBody @Valid DadosCadastroLogin dadosLogin){
		var tokenJWT = service.autenticacao(dadosLogin);
		return ResponseEntity.status(HttpStatus.OK).body(new DadosTokenJwt(tokenJWT));
	}
	
	@PostMapping("/cadastro")
	@Transactional
	@Operation(summary = "Cadastro de usuário", description = "Cadastro de novo usuário do fórum")
	public ResponseEntity<?> cadastro(@RequestBody @Valid DadosCadastroLogin dadosCadastro, UriComponentsBuilder uriBuilder){
		var usuario = service.criacaoUsuario(dadosCadastro);
		var uri = pathUriService.criacaoPathUri(uriBuilder, usuario.getId());
		return ResponseEntity.created(uri).body(new DadosDetalhamentoUsuario(usuario));
	}

}
