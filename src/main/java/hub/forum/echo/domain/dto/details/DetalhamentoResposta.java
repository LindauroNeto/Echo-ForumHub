package hub.forum.echo.domain.dto.details;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import hub.forum.echo.domain.model.Resposta;

public record DetalhamentoResposta(
		String tituloTopico,
		Long idResposta,
		String usuario,
		String mensagem,
		
		@JsonFormat(pattern = "HH:mm dd/MM/yyyy")
		LocalDateTime data
		
		) {
	
	public DetalhamentoResposta(Resposta resposta) {
		this(resposta.getTopico().getTitulo(), resposta.getId(), resposta.getAutor().getUsuario(), resposta.getMensagem(), resposta.getData());
	}

}
