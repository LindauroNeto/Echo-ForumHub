package hub.forum.echo.domain.dto;

import org.springframework.validation.FieldError;

public record DetalhamentoErros(String campo, String mensagem) {
	public DetalhamentoErros(FieldError erro) {
		this(erro.getField().toUpperCase(), erro.getDefaultMessage());
	}

}
