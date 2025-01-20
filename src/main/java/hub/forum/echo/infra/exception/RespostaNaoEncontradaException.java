package hub.forum.echo.infra.exception;

public class RespostaNaoEncontradaException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public RespostaNaoEncontradaException() {
		super();
	}
	
	@Override
	public String getMessage() {
		return "Resposta não encontrada";
	}

}
