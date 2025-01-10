package hub.echo.EchoForumHub.domain.dto;

import hub.echo.EchoForumHub.domain.model.Usuario;

public record DadosDetalhamentoUsuario(String usuario, String senha) {

	public DadosDetalhamentoUsuario(Usuario usuario) {
		this(usuario.getUsuario(), usuario.getSenha());
	}

}
