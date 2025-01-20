package hub.forum.echo.infra.exception;

public class TopicoFinalizadoException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public TopicoFinalizadoException(String mensagem) {
		super(mensagem);
	}

}
