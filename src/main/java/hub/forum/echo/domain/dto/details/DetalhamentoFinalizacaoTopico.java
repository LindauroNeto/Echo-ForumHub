package hub.forum.echo.domain.dto.details;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import hub.forum.echo.domain.model.Topicos;

public record DetalhamentoFinalizacaoTopico(
		Long idTopico,
		String tituloDoTopico,
		String status,
		String mensagemTopico,
		@JsonFormat(pattern = "HH:mm dd/MM/yyyy")
		LocalDateTime dataDeCriacao,
		String autor,
		String curso,
		DetalhamentoRespostaSimples mensagemFinal
		
		) {
	
	public DetalhamentoFinalizacaoTopico(Topicos topicos, DetalhamentoRespostaSimples resposta) {
			this(
				topicos.getId(),
				topicos.getTitulo(),
				topicos.getStatus().getMensagem().toUpperCase(),
				topicos.getMensagem(),
				topicos.getData(),
				topicos.getAutor().getUsuario(),
				topicos.getCurso(),
				resposta
			);
	}


}
