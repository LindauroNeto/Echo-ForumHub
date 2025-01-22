package hub.forum.echo.domain.dto.details;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import hub.forum.echo.domain.model.Resposta;

public record DetalhamentoRespostaSimples(
		String mensagem,
		@JsonFormat(pattern = "HH:mm dd/MM/yyyy")
		LocalDateTime dataFinalizacao) {
	
	public DetalhamentoRespostaSimples(Resposta resposta) {
		this(resposta.getMensagem(), resposta.getData());
	}

}
