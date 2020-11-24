package br.com.localdomain.cruzetafood.api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.localdomain.cruzetafood.domain.model.Restaurante;
import br.com.localdomain.cruzetafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	
	@GetMapping("/restaurantes/por-nome")
	public List<Restaurante> restaurantesPorTaxaFrete(
			String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
		return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
	}
}
