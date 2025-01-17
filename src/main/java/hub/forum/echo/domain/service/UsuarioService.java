package hub.forum.echo.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public String criptografarSenha(String senha) {
		return passwordEncoder.encode(senha);
	}
}
