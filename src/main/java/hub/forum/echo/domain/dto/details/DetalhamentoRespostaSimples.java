package hub.forum.echo.domain.dto.details;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import hub.forum.echo.domain.model.Resposta;

public record DetalhamentoRespostaSimples(
		String usuario,
		String mensagem,
		
		@JsonFormat(pattern = "HH:mm dd/MM/yyyy")
		LocalDateTime data) {
	
	public DetalhamentoRespostaSimples(Resposta resposta) {
		this(resposta.getAutor().getUsuario(), resposta.getMensagem(), resposta.getData());
	}

}
