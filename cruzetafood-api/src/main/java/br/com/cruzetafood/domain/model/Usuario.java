package br.com.cruzetafood.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Entity
public class Usuario {

	@Id
	@GeneratedValue
	@NotNull
	private Long id;

	@NotNull(message = "O campo Nome não pode ser vazio.")
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@NotNull(message = "O campo E-Mail não pode ser vazio.")
	@Column(name = "email", nullable = false)
	private String email;
	
	@NotNull(message = "O campo Senha não pode ser vazio.")
	@Column(name = "senha", nullable = false)
	private String senha;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "usuario_grupo",
			joinColumns = @JoinColumn(name = "usuario_id"),
			inverseJoinColumns = @JoinColumn(name = "grupo_id"))
	private List<Grupo> grupos = new ArrayList<>();	
	
	@JsonIgnore
	@CreationTimestamp
	@Column(name = "dataCadastro", nullable = false)
	private LocalDateTime dataCadastro;
	

}
