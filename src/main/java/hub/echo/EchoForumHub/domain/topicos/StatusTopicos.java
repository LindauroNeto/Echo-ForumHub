package hub.echo.EchoForumHub.domain.topicos;

public enum StatusTopicos {

	SEM_RESPOSTAS("sem respostas"),
	EM_ABERTO("em aberto"),
	RESOLVIDO("resolvido"),
	ENCERRADO("encerrado");
	
	private String mensagem;
	
	StatusTopicos(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public static StatusTopicos deString(String texto) {
		for (StatusTopicos topicos : StatusTopicos.values()) {
			if (topicos.mensagem.equalsIgnoreCase(texto)) {
				return topicos;
			}
		}
		throw new IllegalArgumentException("Não foi possível encontrar o status do tópico");
	}
}
