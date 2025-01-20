package hub.forum.echo.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CadastroDTO(
		@Email
		@NotBlank
		String email,
		
		@NotBlank
		String usuario,
		
		@NotBlank
		String senha
		
		) {

}
