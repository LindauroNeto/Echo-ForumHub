package hub.forum.echo.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import hub.forum.echo.domain.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	UserDetails findByUsuario(String usuario);
	
	Optional<Usuario> findByUsuarioAndAtivoTrue(String usuario);

	Optional<Usuario> findByIdAndAtivoTrue(Long id);

	Page<Usuario> findAllByAtivoTrue(Pageable paginacao);

}
