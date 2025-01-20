package hub.forum.echo.domain.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import hub.forum.echo.domain.model.Topicos;

public record DetalhamentoFinalizacaoTopico(
		Long idTopico,
		String statusTopico,
		String tituloDoTopico,
		String mensagemTopico,
		@JsonFormat(pattern = "HH:mm dd/MM/yyyy")
		LocalDateTime dataDeCriacao,
		String autor,
		String curso,
		
		DetalhamentoRespostaSimples respostaFinal
		
		) {
	
	public DetalhamentoFinalizacaoTopico(Topicos topicos, DetalhamentoRespostaSimples resposta) {
			this(
				topicos.getId(),
				topicos.getStatus().getMensagem().toUpperCase(),
				topicos.getTitulo(),
				topicos.getMensagem(),
				topicos.getData(),
				topicos.getAutor().getUsuario(),
				topicos.getCurso(),
				resposta
			);
	}


}
