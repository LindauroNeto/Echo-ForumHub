package hub.forum.echo.domain.model;

import lombok.Getter;

@Getter
public enum StatusTopicos {

	SEM_RESPOSTAS("sem respostas"),
	EM_ABERTO("em aberto"),
	RESOLVIDO("resolvido"),
	ENCERRADO("encerrado");
	
	private String mensagem;
	
	StatusTopicos(String mensagem) {
		this.mensagem = mensagem;
	}
	
}
