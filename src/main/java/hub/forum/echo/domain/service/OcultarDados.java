package hub.forum.echo.domain.service;

import org.springframework.stereotype.Component;

@Component
public class OcultarDados {
	
	public String censurarEmail(String email) {
		int localArroba = email.indexOf('@');
		String nomeEmail = email.substring(0, localArroba);
		String dominio = email.substring(localArroba);
		
		int caracteresVisiveis = Math.min(nomeEmail.length() / 4, nomeEmail.length());
		String nome = nomeEmail.substring(0, caracteresVisiveis);
		String asteriscos = "*".repeat(nomeEmail.substring(caracteresVisiveis).length());
		
		return nome + asteriscos + dominio; 
	}
	
	public String censuraSenha(String senha) {
		return "*".repeat(senha.length() / 4);
	}

}
