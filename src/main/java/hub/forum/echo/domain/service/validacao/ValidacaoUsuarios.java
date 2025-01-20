package hub.forum.echo.domain.service.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hub.forum.echo.domain.model.Usuario;
import hub.forum.echo.domain.repository.UsuarioRepository;
import hub.forum.echo.domain.service.UsuarioIncompativelException;
import hub.forum.echo.infra.exception.UsuarioNaoEncontradoException;

@Service
public class ValidacaoUsuarios {

	@Autowired
	private UsuarioRepository repository;
	
	public Usuario validacaoUsuarioPorNome(String usuario) {
		var usuarioO = repository.findByUsuarioAndAtivoTrue(usuario);
		if (usuarioO.isEmpty()) {
			throw new UsuarioNaoEncontradoException();
		}
		return repository.getReferenceById(usuarioO.get().getId());
	}
	
	public Usuario validacaoUsuarioPorId(Long id) {
		var usuarioO = repository.findByIdAndAtivoTrue(id);
		if (usuarioO.isEmpty()) {
			throw new UsuarioNaoEncontradoException();
		}
		return repository.getReferenceById(usuarioO.get().getId());
	}
	
	public Usuario validacaoUsuarioEUsuario(Usuario usuario1, Usuario usuario2) {
		if (usuario1 != usuario2) {
			throw new UsuarioIncompativelException();
		}
		
		return usuario1;
	}
}
