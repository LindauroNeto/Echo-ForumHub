package hub.forum.echo.domain.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import hub.forum.echo.domain.model.Topicos;

public record DetalhamentoTopicos(
		Long id,
		String titulo,
		String mensagem,
		
		@JsonFormat(pattern = "HH:mm dd/MM/yyyy")
		LocalDateTime data,
		
		String autor,
		String curso
		
		) {
	
	public DetalhamentoTopicos(Topicos topicos) {
		this(topicos.getId(), topicos.getTitulo(), topicos.getMensagem(), topicos.getData(), topicos.getAutor().getUsuario(), topicos.getCurso());
	}

}
