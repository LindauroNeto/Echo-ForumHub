package hub.forum.echo.domain.dto;

import jakarta.validation.constraints.NotEmpty;

public record RespostaDTO(@NotEmpty String mensagem) {

}
