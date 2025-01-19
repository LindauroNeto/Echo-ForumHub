package hub.forum.echo.domain.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import hub.forum.echo.domain.model.Resposta;

public record DetalhamentoResposta(
		Long id,
		String mensagem,
		
		@JsonFormat(pattern = "HH:mm dd/MM/yyyy")
		LocalDateTime data,
		
		String autor,
		String tituloTopico
		) {
	
	public DetalhamentoResposta(Resposta resposta) {
		this(resposta.getId(), resposta.getMensagem(), resposta.getData(), resposta.getAutor().getUsuario(), resposta.getTopico().getTitulo());
	}

}
