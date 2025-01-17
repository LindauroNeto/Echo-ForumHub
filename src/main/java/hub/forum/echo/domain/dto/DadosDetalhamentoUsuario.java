package hub.forum.echo.domain.dto;

import hub.forum.echo.domain.model.Usuario;

public record DadosDetalhamentoUsuario(String usuario, String senha) {

	public DadosDetalhamentoUsuario(Usuario usuario) {
		this(usuario.getUsuario(), usuario.getSenha());
	}

}
