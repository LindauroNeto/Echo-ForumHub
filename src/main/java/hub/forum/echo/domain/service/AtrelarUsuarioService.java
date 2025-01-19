package hub.forum.echo.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hub.forum.echo.domain.repository.UsuarioRepository;
import hub.forum.echo.infra.exception.UsuarioNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AtrelarUsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TokenService tokenService;

	public String obterUsuario(HttpServletRequest request) {
		var tokenSubject = receberUsuarioPeloToken(request);
		var usuarioO = usuarioRepository.encontrarUsuario(tokenSubject);
		if (usuarioO.isEmpty()) {
			throw new UsuarioNaoEncontradoException("Usuário não encontrado");
		}
		return usuarioO.get().getUsuario();
	}

	private String receberUsuarioPeloToken(HttpServletRequest request) {
		var headerToken = request.getHeader("Authorization").replace("Bearer ", "");
		return tokenService.obterSubject(headerToken);
	}
}
