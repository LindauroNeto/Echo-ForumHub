package hub.forum.echo.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hub.forum.echo.domain.dto.DadosResposta;
import hub.forum.echo.domain.model.Resposta;
import hub.forum.echo.domain.model.Usuario;
import hub.forum.echo.domain.repository.RespostaRepository;
import hub.forum.echo.domain.repository.TopicosRepository;
import hub.forum.echo.domain.repository.UsuarioRepository;
import hub.forum.echo.infra.exception.TopicoNaoEncontradoException;
import hub.forum.echo.infra.exception.UsuarioNaoEncontradoException;

@Service
public class RespostaService {
	
	@Autowired
	private TopicosRepository topicosRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RespostaRepository repository;

	public Resposta criarResposta(DadosResposta dadosResposta, Usuario usuario, Long idTopico) {
		if (!topicosRepository.existsById(idTopico)) {
			throw new TopicoNaoEncontradoException();
		}
		var topicoO = topicosRepository.getReferenceById(idTopico);
		
		var usuarioO = usuarioRepository.encontrarUsuario(usuario.getUsuario());
		if (usuarioO.isEmpty()) {
			throw new UsuarioNaoEncontradoException();
		}
		
		var resposta = new Resposta(dadosResposta, usuarioO.get(), topicoO);
		repository.save(resposta);
		return resposta;
	}
}
