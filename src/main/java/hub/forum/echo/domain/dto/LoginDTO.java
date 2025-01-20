package hub.forum.echo.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(@NotBlank String usuario, @NotBlank String senha) {

}
