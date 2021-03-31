package br.com.cruzetafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.cruzetafood.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@Id
	@NotNull
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@NotNull
	//@NotEmpty
	@NotBlank
	private String nome;
	
	@Column(name = "taxa_frete", nullable = false)
	@PositiveOrZero
	private BigDecimal taxaFrete;
	
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
	@NotNull
	@ManyToOne //(fetch = FetchType.EAGER) // Nem precisava
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;
	
	@JsonIgnore
	@Embedded	
	private Endereco endereco;
	
	@JsonIgnore
	@CreationTimestamp
	//@NotNull(message = "O campo Data Cadastro não pode ser vazio.")
	@Column(nullable = false, columnDefinition = "datetime")	
	private LocalDateTime dataCadastro;
	
	@JsonIgnore
	@UpdateTimestamp
	//@NotNull(message = "O campo Data Atualização não pode ser vazio.")
	@Column(nullable = true, columnDefinition = "datetime")
	private LocalDateTime dataAtualizacao;
	
//	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento",
			joinColumns = @JoinColumn(name = "restaurante_id"),
			inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<>();
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "restaurante_produto",
			joinColumns = @JoinColumn(name = "restaurante_id"),
			inverseJoinColumns = @JoinColumn(name = "produto_id"))
	private List<Produto> produtos = new ArrayList<>();
}
