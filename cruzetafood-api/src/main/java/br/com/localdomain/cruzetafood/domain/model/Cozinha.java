package br.com.localdomain.cruzetafood.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

//@JsonRootName("gastronomia")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cozinha {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@JsonIgnore
	//@JsonProperty("titulo") // representação formal do nome deste campo para sera exibido quando o recurso for enviado 
	@NotNull
	private String nome;
	
}
