package hub.forum.echo.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import hub.forum.echo.domain.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	UserDetails findByUsuario(String usuario);
	
	@Query("SELECT u FROM Usuario u WHERE u.usuario = :usuario")
	Optional<Usuario> encontrarUsuario (String usuario);

}
