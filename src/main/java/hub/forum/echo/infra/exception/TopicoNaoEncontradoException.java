package hub.forum.echo.infra.exception;

public class TopicoNaoEncontradoException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public TopicoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

}
