package hub.forum.echo.domain.dto;

import jakarta.validation.constraints.NotEmpty;

public record DadosCadastroTopico(
		@NotEmpty
		String titulo,
		
		@NotEmpty
		String mensagem,
		
		@NotEmpty
		String autor,
		
		@NotEmpty
		String curso
		) {

}
