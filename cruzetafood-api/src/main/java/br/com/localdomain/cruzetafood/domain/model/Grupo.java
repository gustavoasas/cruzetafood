package br.com.localdomain.cruzetafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Entity
public class Grupo {

	@Id
	@GeneratedValue
	@NotNull
	private Long id;
	
	@NotNull(message = "O campo Nome não pode ser vazio.")
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@NotNull(message = "O campo Usuário não pode ser vazio.")
	@JoinColumn(name = "usuario", nullable = false)
	private Usuario usuario;	
	
	@NotNull(message = "O campo Permissao não pode ser vazio.")
	@OneToMany
	@JoinColumn(name = "permissao_id")
	private Permissao permissao;
}
