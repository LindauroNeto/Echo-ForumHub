package hub.forum.echo.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import hub.forum.echo.domain.dto.details.DetalhamentoErros;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorDeExceptions {
	
	@ExceptionHandler(TopicoNaoEncontradoException.class)
	public ResponseEntity<?> tratarTopicoNaoEncontrado(TopicoNaoEncontradoException tnee) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(tnee.getMessage());
	}
	
	@ExceptionHandler(UsuarioNaoEncontradoException.class)
	public ResponseEntity<?> tratarUsuarioNaoEncontrado(UsuarioNaoEncontradoException usnee) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(usnee.getMessage());
	}
	
	@ExceptionHandler(RespostaNaoEncontradaException.class)
	public ResponseEntity<?> tratarRespostaNaoEncontrada(RespostaNaoEncontradaException rnee) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnee.getMessage());
	}
	
	@ExceptionHandler(TopicoFinalizadoException.class)
	public ResponseEntity<?> tratarTopicoResolvido(TopicoFinalizadoException tere) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(tere.getMessage());
	}
	
	@ExceptionHandler(UsuarioIncompativelException.class)
	public ResponseEntity<?> tratarTopicoResolvido(UsuarioIncompativelException uie) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(uie.getMessage());
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> tratarErro404() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> tratarErro400(MethodArgumentNotValidException manv) {
		var erros = manv.getFieldErrors();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros.stream().map(DetalhamentoErros::new));
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> tratarErro400(HttpMessageNotReadableException hmnre) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hmnre.getMessage());
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> tratarBadCredentials() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<?> tratarBadAuthentication() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro de autenticação");
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> tratarAcessDenied() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> tratarErro500(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + ex.getLocalizedMessage());
	}
	
	
	

	
	

}
