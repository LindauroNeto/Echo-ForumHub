package hub.echo.EchoForumHub.domain.topicos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;

public record DadosCadastroTopico(
		@NotEmpty
		String titulo,
		
		@NotEmpty
		String mensagem,
		
		LocalDateTime data,
		
		@NotEmpty
		String autor,
		
		@NotEmpty
		String curso
		) {

}
