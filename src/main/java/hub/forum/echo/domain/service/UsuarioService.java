package hub.forum.echo.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hub.forum.echo.domain.dto.CadastroDTO;
import hub.forum.echo.domain.dto.LoginDTO;
import hub.forum.echo.domain.dto.details.DetalhamentoUsuarios;
import hub.forum.echo.domain.model.Usuario;
import hub.forum.echo.domain.repository.UsuarioRepository;
import hub.forum.echo.domain.service.validators.ValidacaoUsuarios;
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
	
	@Autowired
	private OcultarDados ocultador;
	
	private String criptografarSenha(String senha) {
		return passwordEncoder.encode(senha);
	}
	
	private Usuario verUsuario(Long id) {
		return validacao.validacaoUsuarioPorId(id);
	}
	
	public Usuario criacaoUsuario(CadastroDTO dadosCadastro) {
		var usuario = new Usuario(dadosCadastro.usuario(), dadosCadastro.email(), criptografarSenha(dadosCadastro.senha()));
		repository.save(usuario);
		return usuario;
	}
	
	public String autenticacao(LoginDTO dadosLogin) {
		var autenticacaoUsuario = new UsernamePasswordAuthenticationToken(dadosLogin.usuario(), dadosLogin.senha());
		var autenticacaoToken = manager.authenticate(autenticacaoUsuario);
		return tokenService.gerarToken((Usuario) autenticacaoToken.getPrincipal());
	}
	
	public DetalhamentoUsuarios detalharUsuario(Long id) {
		var usuario = verUsuario(id);
		return new DetalhamentoUsuarios(usuario.getId(), usuario.getUsuario(), ocultador.censurarEmail(usuario.getEmail()), ocultador.censuraSenha(usuario.getSenha()));
	}
	
	public Page<DetalhamentoUsuarios> listarUsuarios(Pageable paginacao) {
		return repository.findAllByAtivoTrue(paginacao)
				.map(u -> new DetalhamentoUsuarios(
						u.getId(),
						u.getUsuario(),
						ocultador.censurarEmail(u.getEmail()),
						ocultador.censuraSenha(u.getSenha())
						));
	}
	
	public void excluirUsuario(Long id, HttpServletRequest request) {
		var usuarioId = verUsuario(id);
		var usuarioToken = atrelamento.obterUsuario(request);
		
		validacao.validacaoUsuarioUsuario(usuarioToken, usuarioId);
		usuarioId.excluir();
		repository.save(usuarioId);
	}
}
