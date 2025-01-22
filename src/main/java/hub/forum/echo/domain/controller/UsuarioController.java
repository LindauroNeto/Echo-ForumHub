package hub.forum.echo.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import hub.forum.echo.domain.dto.CadastroDTO;
import hub.forum.echo.domain.dto.LoginDTO;
import hub.forum.echo.domain.dto.TokenJwtDTO;
import hub.forum.echo.domain.service.PathUriService;
import hub.forum.echo.domain.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuários")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	@Autowired
	private PathUriService pathUriService;
	
	@PostMapping("/login")
	@Operation(summary = "Login de usuário", description = "Login de usuário para acesso do fórum")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Preenchimento inválido dos dados"),
			@ApiResponse(responseCode = "401", description = "Usuário incorreto ou não cadastado no sistema"),
			@ApiResponse(responseCode = "500", description = "Problema interno no servidor"),
	})
	public ResponseEntity<?> login(@RequestBody @Valid LoginDTO dadosLogin) {
		var tokenJWT = service.autenticacao(dadosLogin);
		return ResponseEntity.status(HttpStatus.OK).body(new TokenJwtDTO(tokenJWT));
	}
	
	@PostMapping("/cadastro")
	@Operation(summary = "Cadastro de usuário", description = "Cadastro de novo usuário do fórum")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Cadastro realizado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Preenchimento inválido dos dados"),
			@ApiResponse(responseCode = "500", description = "Problema interno no servidor"),
	})
	public ResponseEntity<?> cadastro(@RequestBody @Valid CadastroDTO dadosCadastro, UriComponentsBuilder uriBuilder){
		var usuario = service.criacaoUsuario(dadosCadastro);
		var uri = pathUriService.criacaoPathUri(uriBuilder, usuario.getId(), "cadastro");
		return ResponseEntity.created(uri).body("Usuário criado com sucesso!");
	}
	
	@DeleteMapping("/excluir/{id}")
	@SecurityRequirement(name = "bearer-key")
	@Operation(summary = "Exclusão de usuário", description = "Excluir usuário do banco de contas, a operação só pode ser realizada somente pelo próprio usuário")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Exclusão realizada com sucesso"),
			@ApiResponse(responseCode = "401", description = "Usuário não autorizado"),
			@ApiResponse(responseCode = "403", description = "Sem token para a requisição ou expirado"),
			@ApiResponse(responseCode = "404", description = "Usuário não existe ou já foi excluído"),
			@ApiResponse(responseCode = "500", description = "Problema interno no servidor"),
	})
	public ResponseEntity<?> excluir(@PathVariable Long id, HttpServletRequest request){
		service.excluirUsuario(id, request);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
