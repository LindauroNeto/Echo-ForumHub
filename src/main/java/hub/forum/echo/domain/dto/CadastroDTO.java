package hub.forum.echo.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CadastroDTO(
		@NotBlank
		String usuario,
		
		@Email
		@NotBlank
		String email,
		
		
		@NotBlank
		@Pattern(regexp = ".{8,}", message = "A senha deve ter no mínimo 8 caracteres")
		String senha
		
		) {

}
