package br.com.localdomain.cruzetafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.localdomain.cruzetafood.domain.enums.StatusPedido;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private BigDecimal subtotal;
	
	@Column(name = "taxa_frete")
	private BigDecimal taxaFrete;
	
	@NotNull(message = "O campo Valor Total não foi informado e é obrigatório.")
	@Column(columnDefinition = "datetime", name = "valor_total")
	private BigDecimal valorTotal;
	
	@NotNull(message = "Data de Criação não foi informada e é obrigatório.")
	@Column(name = "data_criacao")
	@CreationTimestamp
	private LocalDateTime dataCriacao;
	
	@NotNull(message = "O campo Data de Confirmação não foi informado e é obrigatório")
	@Column(name = "data_confirmacao")
	private LocalDateTime dataConfirmacao;
	
	@Column(nullable = true, name = "data_cancelamento")
	private LocalDateTime dataCancelamento;
	
	@NotNull(message = "O campo Data Entrega não foi informado e é obrigatório.")
	@Column(name = "data_entrega", columnDefinition = "datetime")
	private LocalDateTime dataEntrega;
	
	@Column(name = "status")
	private StatusPedido statusPedido;
	
	@JsonIgnore
	@Embedded
	private Endereco endereco;
	
	@ManyToOne
	@NotNull(message = "O campo Cliente não foi informado e é obrigatório.")
	@JoinColumn(name = "usuario_cliente_id")
	private Usuario cliente;
	
	@ManyToOne
	@NotNull(message = "O campo Retaurante não foi informado e é obrigatório.")
	@JoinColumn(name = "restaurante_id")
	private Restaurante restaurante;
	
	@OneToMany(mappedBy = "pedido")
	@NotNull(message = "O campo Item é obrigatório.")
	private List<ItemPedido> itens = new ArrayList<>();
	
	@ManyToOne
	@NotNull(message = "O campo Forma de Pagamento é obrigatório")
	@JoinColumn(name = "forma_pagamento_id")
	private FormaPagamento formaPagamento;
}
