package hub.echo.EchoForumHub.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hub.echo.EchoForumHub.domain.dto.DadosCadastroLogin;
import hub.echo.EchoForumHub.domain.dto.DadosTokenJwt;
import hub.echo.EchoForumHub.domain.model.Usuario;
import hub.echo.EchoForumHub.domain.repository.UsuarioRepository;
import hub.echo.EchoForumHub.domain.service.TokenService;
import hub.echo.EchoForumHub.domain.service.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
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
	public ResponseEntity<?> login(@RequestBody @Valid DadosCadastroLogin dadosLogin){
		var autenticacao = new UsernamePasswordAuthenticationToken(dadosLogin.usuario(), dadosLogin.senha());
		var autenticacaoToken = manager.authenticate(autenticacao);
		
		var tokenJWT = tokenService.gerarToken((Usuario) autenticacaoToken.getPrincipal());
		return ResponseEntity.status(HttpStatus.OK).body(new DadosTokenJwt(tokenJWT));
	}
	
	@PostMapping("/cadastro")
	@Transactional
	public ResponseEntity<?> cadastro(@RequestBody @Valid DadosCadastroLogin dadosCadastro){
		var usuario = new Usuario(dadosCadastro.usuario(), service.criptografarSenha(dadosCadastro.senha()));
		repository.save(usuario);
		return ResponseEntity.status(HttpStatus.OK).body("Cadastro concluído!");
	}

}
