package hub.forum.echo.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hub.forum.echo.domain.model.StatusTopicos;
import hub.forum.echo.domain.model.Topicos;
import hub.forum.echo.domain.model.Usuario;
import hub.forum.echo.domain.repository.TopicosRepository;
import hub.forum.echo.domain.repository.UsuarioRepository;
import hub.forum.echo.infra.exception.TopicoEncerradoResolvidoExcpetion;
import hub.forum.echo.infra.exception.TopicoNaoEncontradoException;
import hub.forum.echo.infra.exception.UsuarioNaoEncontradoException;

@Service
public class ValidacaoTopicosUsuariosService {
	
	@Autowired
	private TopicosRepository topicosRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Topicos validacaoTopicoPorId(Long idTopico) {
		var topicoO = topicosRepository.findByIdAndTopicoAtivoTrue(idTopico);
		if (topicoO.isEmpty()) {
			throw new TopicoNaoEncontradoException();
		}
		return topicosRepository.getReferenceById(idTopico);
	}
	
	public Usuario validacaoUsuarioPorNome(String usuario) {
		var usuarioO = usuarioRepository.findByUsuarioAndAtivoTrue(usuario);
		if (usuarioO.isEmpty()) {
			throw new UsuarioNaoEncontradoException();
		}
		return usuarioRepository.getReferenceById(usuarioO.get().getId());
	}
	
	public Usuario validacaoUsuarioPorId(Long id) {
		var usuarioO = usuarioRepository.findByIdAndAtivoTrue(id);
		if (usuarioO.isEmpty()) {
			throw new UsuarioNaoEncontradoException();
		}
		return usuarioRepository.getReferenceById(usuarioO.get().getId());
	}
	
	public void validacaoTopicoFinalizado(Topicos topico) {
		if (topico.getStatus() == StatusTopicos.RESOLVIDO || topico.getStatus() == StatusTopicos.ENCERRADO) {
			throw new TopicoEncerradoResolvidoExcpetion();
		}
	}

	public Usuario validacaoUsuarioEUsuario(Usuario usuario1, Usuario usuario2) {
		if (usuario1 != usuario2) {
			throw new UsuarioIncompativelException();
		}
		
		return usuario1;
	}

}
