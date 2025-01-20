package hub.forum.echo.infra.exception;

public class UsuarioIncompativelException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UsuarioIncompativelException() {
		super();
	}
	
	@Override
	public String getMessage() {
		return "Usuário incompatível para realizar a operação";
	}

}
