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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ItemPedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	
	@NotNull(message = "O campo quantidade não foi informado e é obrigatório.")
	@Column
	private Integer quantidade;
	
	@NotNull(message = "O campo Preço Unitário não foi informado e é obrigatório.")
	@Column(name = "preco_unitario")
	private BigDecimal precoUnitario;
	
	@NotNull(message = "O campo Preço Total não foi informado e é obrigatório.")
	@Column(name = "preco_total")
	private BigDecimal precoTotal;
	
	private String observacao;
	
	@NotNull(message = "O campo Produto não foi informado e é obrigatório.")
	@ManyToOne
	@JoinColumn
	private Produto produto;
	
	@NotNull(message = "O campo Pedido não foi informado e é obrigatório.")
	@ManyToOne
	@JoinColumn
	private Pedido pedido;
	
}
