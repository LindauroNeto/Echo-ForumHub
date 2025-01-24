package hub.forum.echo.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import hub.forum.echo.domain.dto.AtualizacaoTopico;
import hub.forum.echo.domain.dto.TopicoDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	
	@Column(nullable = false, columnDefinition = "MEDIUMTEXT")
	private String mensagem;
	
	@CreationTimestamp
	@Column(name = "data_de_criacao", nullable = false, updatable = false)
	private LocalDateTime data;
	
	@Column(name = "status_topico", updatable = true)
	@Enumerated(EnumType.STRING)
	private StatusTopicos status;
	
	@JoinColumn(name = "autor", nullable = false)
	@ManyToOne
	private Usuario autor;
	
	@Column(nullable = false)
	private String curso;
	
	@Column(name = "ativo", nullable = false)
	private Boolean topicoAtivo;
	
	@OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Column(name = "respostas")
	private List<Resposta> respostas = new ArrayList<>();
	
	public void atualizar(AtualizacaoTopico dados) {
		if (dados.titulo() != null) {
			this.titulo = dados.titulo();
		}
		
		if (dados.mensagem() != null) {
			this.mensagem = dados.mensagem();
		}
		
	}
	
	public void excluir() {
		this.topicoAtivo = false;
		alterarStatus(StatusTopicos.EXCLUIDO);
	}

	public Topicos(TopicoDTO dados, Usuario usuario) {
		this.titulo = dados.titulo();
		this.mensagem = dados.mensagem();
		this.status = StatusTopicos.SEM_RESPOSTAS;
		this.autor = usuario;
		this.curso = dados.curso();
		this.topicoAtivo = true;
	}

	public void alterarStatus(StatusTopicos status) {
		this.status = status;
	}
	
}
