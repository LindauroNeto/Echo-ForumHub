package hub.forum.echo.domain.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import hub.forum.echo.domain.dto.DadosResposta;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "respostas")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Resposta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private Long id;
	
	@Column(nullable = false)
	private String mensagem;
	
	@CreationTimestamp
	@Column(name = "data_de_resposta", nullable = false)
	private LocalDateTime data;
	
	@ManyToOne
	private Usuario autor;
	
	@ManyToOne
	private Topicos topico;
	
	public Resposta(DadosResposta dados) {
		this.mensagem = dados.mensagem();
	}

}
