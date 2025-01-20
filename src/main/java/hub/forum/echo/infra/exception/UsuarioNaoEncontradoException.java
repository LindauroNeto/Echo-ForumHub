package hub.forum.echo.infra.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UsuarioNaoEncontradoException() {
		super();
	}
	
	@Override
	public String getMessage() {
		return "Usuário não encontrado";
	}

}
