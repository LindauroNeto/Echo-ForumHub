package hub.forum.echo.infra.exception;

public class TopicoNaoEncontradoException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public TopicoNaoEncontradoException() {
		super();
	}

	@Override
	public String getMessage() {
		return "Tópico não encontrado";
	}
}
