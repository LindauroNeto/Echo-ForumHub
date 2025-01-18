package hub.forum.echo.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hub.forum.echo.domain.dto.DadosCadastroLogin;
import hub.forum.echo.domain.model.Usuario;
import hub.forum.echo.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenService;
	
	private String criptografarSenha(String senha) {
		return passwordEncoder.encode(senha);
	}
	
	public Usuario criacaoUsuario(DadosCadastroLogin dadosCadastro) {
		var usuario = new Usuario(dadosCadastro.usuario(), criptografarSenha(dadosCadastro.senha()));
		repository.save(usuario);
		return usuario;
	}
	
	public String autenticacao(DadosCadastroLogin dadosLogin) {
		var autenticacaoUsuario = new UsernamePasswordAuthenticationToken(dadosLogin.usuario(), dadosLogin.senha());
		var autenticacaoToken = manager.authenticate(autenticacaoUsuario);
		return tokenService.gerarToken((Usuario) autenticacaoToken.getPrincipal());
	}
}
