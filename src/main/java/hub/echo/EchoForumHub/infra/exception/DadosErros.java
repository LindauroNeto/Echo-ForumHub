package hub.echo.EchoForumHub.infra.exception;

import org.springframework.validation.FieldError;

public record DadosErros(String campo, String mensagem) {
	public DadosErros(FieldError erro) {
		this(erro.getField().toUpperCase(), erro.getDefaultMessage());
	}

}
