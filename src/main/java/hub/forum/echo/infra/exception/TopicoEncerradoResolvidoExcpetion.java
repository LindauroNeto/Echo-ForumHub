package hub.forum.echo.infra.exception;

public class TopicoEncerradoResolvidoExcpetion extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public TopicoEncerradoResolvidoExcpetion() {
		super();
	}
	
	@Override
	public String getMessage() {
		return "O tópico já foi resolvido ou foi encerrado";
	}

}
