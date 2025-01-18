package hub.forum.echo.domain.dto;

import jakarta.validation.constraints.NotEmpty;

public record DadosResposta(@NotEmpty String mensagem) {

}
