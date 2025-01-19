package hub.forum.echo.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hub.forum.echo.domain.model.Topicos;
import hub.forum.echo.domain.model.Usuario;
import hub.forum.echo.domain.repository.TopicosRepository;
import hub.forum.echo.domain.repository.UsuarioRepository;
import hub.forum.echo.infra.exception.TopicoNaoEncontradoException;
import hub.forum.echo.infra.exception.UsuarioNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AtrelarmentoService {
	
	@Autowired
	private TopicosRepository topicosRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TokenService tokenService;
	
	public Topicos obterTopico(Long id) {
		var topicoO = topicosRepository.findByIdAndTopicoAtivoTrue(id);
		if (topicoO.isEmpty()) {
			throw new TopicoNaoEncontradoException();
		}
		return topicoO.get();
	}

	public Usuario obterUsuario(HttpServletRequest request) {
		var tokenSubject = receberUsuarioPeloToken(request);
		var usuarioO = usuarioRepository.encontrarUsuario(tokenSubject);
		if (usuarioO.isEmpty()) {
			throw new UsuarioNaoEncontradoException();
		}
		return usuarioO.get();
	}

	private String receberUsuarioPeloToken(HttpServletRequest request) {
		var headerToken = request.getHeader("Authorization").replace("Bearer ", "");
		return tokenService.obterSubject(headerToken);
	}
}
