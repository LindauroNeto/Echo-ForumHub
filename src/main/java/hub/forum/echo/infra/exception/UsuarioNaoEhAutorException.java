package hub.forum.echo.infra.exception;

public class UsuarioNaoEhAutorException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UsuarioNaoEhAutorException() {
		super();
	}

	@Override
	public String getMessage() {
		return "O usuário não é o autor do tópico";
	}
}
