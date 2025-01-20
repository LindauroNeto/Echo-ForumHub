package hub.forum.echo.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hub.forum.echo.domain.model.Usuario;
import hub.forum.echo.domain.service.validators.ValidacaoUsuarios;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AtrelarmentoService {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private ValidacaoUsuarios validacaoUsuarios;
	
	public Usuario obterUsuario(HttpServletRequest request) {
		var tokenSubject = receberUsuarioPeloToken(request);
		var usuario = validacaoUsuarios.validacaoUsuarioPorNome(tokenSubject);
		return usuario;
	}

	private String receberUsuarioPeloToken(HttpServletRequest request) {
		var headerToken = request.getHeader("Authorization").replace("Bearer ", "");
		return tokenService.obterSubject(headerToken);
	}
}
