package hub.forum.echo.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import hub.forum.echo.domain.dto.DadosAtualizacaoTopico;
import hub.forum.echo.domain.dto.DadosCadastroTopico;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "topicos")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topicos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String titulo;
	
	@Column(nullable = false, unique = true)
	private String mensagem;
	
	@Column(name = "data_de_criacao", nullable = false, updatable = false)
	private LocalDateTime data;
	
	@Column(name = "status_topico")
	@Enumerated(EnumType.STRING)
	private StatusTopicos status;
	
	@Column(nullable = false)
	private String autor;
	
	@Column(nullable = false)
	private String curso;
	
	@Column(name = "ativo", nullable = false)
	private Boolean topicoAtivo;
	
	@OneToMany
	private List<Resposta> respostas = new ArrayList<>();
	
	public void atualizar(DadosAtualizacaoTopico dados) {
		if (dados.titulo() != null) {
			this.titulo = dados.titulo();
		}
		
		if (dados.mensagem() != null) {
			this.mensagem = dados.mensagem();
		}
		
	}
	
	public void excluir() {
		this.topicoAtivo = false;
		this.status = StatusTopicos.ENCERRADO;
	}

	public Topicos(DadosCadastroTopico dados, String usuario) {
		this.titulo = dados.titulo();
		this.mensagem = dados.mensagem();
		this.data = LocalDateTime.now();
		this.status = StatusTopicos.SEM_RESPOSTAS;
		this.autor = usuario;
		this.curso = dados.curso();
		this.topicoAtivo = true;
	}
	
}
