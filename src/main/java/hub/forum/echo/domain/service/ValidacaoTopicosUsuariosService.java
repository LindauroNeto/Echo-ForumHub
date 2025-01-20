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
	
	public Topicos validacaoTopico(Long idTopico) {
		if (!topicosRepository.existsById(idTopico)) {
			throw new TopicoNaoEncontradoException();
		}
		return topicosRepository.getReferenceById(idTopico);
	}
	
	public Usuario validacaoUsuario(Usuario usuario) {
		var usuarioO = usuarioRepository.encontrarUsuario(usuario.getUsuario());
		if (usuarioO.isEmpty()) {
			throw new UsuarioNaoEncontradoException();
		}
		return usuarioO.get();
	}
	
	public void validarTopicoFinalizado(Topicos topico) {
		if (topico.getStatus() == StatusTopicos.RESOLVIDO || topico.getStatus() == StatusTopicos.ENCERRADO) {
			throw new TopicoEncerradoResolvidoExcpetion();
		}
	}

}
