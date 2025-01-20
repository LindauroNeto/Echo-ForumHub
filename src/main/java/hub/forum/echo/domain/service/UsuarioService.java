package hub.forum.echo.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hub.forum.echo.domain.dto.CadastroDTO;
import hub.forum.echo.domain.dto.LoginDTO;
import hub.forum.echo.domain.model.Usuario;
import hub.forum.echo.domain.repository.UsuarioRepository;
import hub.forum.echo.domain.service.validacao.ValidacaoUsuarios;
import jakarta.servlet.http.HttpServletRequest;

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
	
	@Autowired
	private ValidacaoUsuarios validacao;
	
	@Autowired
	private AtrelarmentoService atrelamento;
	
	private String criptografarSenha(String senha) {
		return passwordEncoder.encode(senha);
	}
	
	public Usuario criacaoUsuario(CadastroDTO dadosCadastro) {
		var usuario = new Usuario(dadosCadastro.email(), dadosCadastro.usuario(), criptografarSenha(dadosCadastro.senha()));
		repository.save(usuario);
		return usuario;
	}
	
	public String autenticacao(LoginDTO dadosLogin) {
		var autenticacaoUsuario = new UsernamePasswordAuthenticationToken(dadosLogin.usuario(), dadosLogin.senha());
		var autenticacaoToken = manager.authenticate(autenticacaoUsuario);
		return tokenService.gerarToken((Usuario) autenticacaoToken.getPrincipal());
	}
	
	public void excluirUsuario(Long id, HttpServletRequest request) {
		var usuarioToken = atrelamento.obterUsuario(request);
		var usuarioId = validacao.validacaoUsuarioPorId(id);
		
		var usuario = validacao.validacaoUsuarioEUsuario(usuarioToken, usuarioId);
		usuario.excluir();
		repository.save(usuario);
	}
}
