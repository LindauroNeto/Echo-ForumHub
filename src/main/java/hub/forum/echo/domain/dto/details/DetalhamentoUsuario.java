package hub.forum.echo.domain.dto.details;

import hub.forum.echo.domain.model.Usuario;

public record DetalhamentoUsuario(String usuario, String senha) {

	public DetalhamentoUsuario(Usuario usuario) {
		this(usuario.getUsuario(), usuario.getSenha());
	}

}
