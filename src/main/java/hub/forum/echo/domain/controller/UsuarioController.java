package hub.forum.echo.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import hub.forum.echo.domain.dto.DadosCadastroLogin;
import hub.forum.echo.domain.dto.DadosDetalhamentoUsuario;
import hub.forum.echo.domain.dto.DadosTokenJwt;
import hub.forum.echo.domain.model.Usuario;
import hub.forum.echo.domain.repository.UsuarioRepository;
import hub.forum.echo.domain.service.TokenService;
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
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioService service;
	
	@Autowired
	private UsuarioRepository repository;
	
	@PostMapping("/login")
	@Operation(summary = "Login de usuário", description = "Login de usuário para acesso do fórum")
	public ResponseEntity<?> login(@RequestBody @Valid DadosCadastroLogin dadosLogin){
		var autenticacao = new UsernamePasswordAuthenticationToken(dadosLogin.usuario(), dadosLogin.senha());
		var autenticacaoToken = manager.authenticate(autenticacao);
		
		var tokenJWT = tokenService.gerarToken((Usuario) autenticacaoToken.getPrincipal());
		return ResponseEntity.status(HttpStatus.OK).body(new DadosTokenJwt(tokenJWT));
	}
	
	@PostMapping("/cadastro")
	@Transactional
	@Operation(summary = "Cadastro de usuário", description = "Cadastro de novo usuário do fórum")
	public ResponseEntity<?> cadastro(@RequestBody @Valid DadosCadastroLogin dadosCadastro, UriComponentsBuilder uriBuilder){
		var usuario = new Usuario(dadosCadastro.usuario(), service.criptografarSenha(dadosCadastro.senha()));
		var uri = uriBuilder.path("/cadastro/{id}").buildAndExpand(usuario.getId()).toUri();
		
		repository.save(usuario);
		
		return ResponseEntity.created(uri).body(new DadosDetalhamentoUsuario(usuario));
	}

}
