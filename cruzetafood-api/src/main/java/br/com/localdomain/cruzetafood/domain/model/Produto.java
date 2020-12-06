package br.com.localdomain.cruzetafood.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "O campo Descrição não pode ser vazio.")
	@Column(name = "descricao", nullable = false)
	private String descricao;
	
	@NotNull(message = "O campo Preço não pode ser vazio.")
	@Column(name = "preco")
	private BigDecimal preco;
	
	@Column(name = "alvo")
	private Boolean alvo;
	
	@ManyToOne
	@JoinColumn(name = "restaurante_id", nullable = true)
	private Restaurante restaurante;
	
}
