package hub.forum.echo.domain.dto;

import jakarta.validation.constraints.NotEmpty;

public record TopicoDTO(
		@NotEmpty
		String titulo,
		
		@NotEmpty
		String mensagem,
		
		@NotEmpty
		String curso
		) {

}
