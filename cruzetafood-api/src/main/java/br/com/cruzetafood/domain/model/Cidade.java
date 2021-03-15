package br.com.cruzetafood.domain.model;

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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cidade {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "estado_id", nullable = false)
	private Estado estado;
}
