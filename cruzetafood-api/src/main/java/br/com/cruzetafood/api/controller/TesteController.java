package br.com.cruzetafood.api.controller;

import static br.com.cruzetafood.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static br.com.cruzetafood.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cruzetafood.domain.model.Cozinha;
import br.com.cruzetafood.domain.model.Restaurante;
import br.com.cruzetafood.domain.repository.CozinhaRepository;
import br.com.cruzetafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@GetMapping("/restaurantes/por-nome")
	public List<Restaurante> restaurantesPorTaxaFrete(
			String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
		return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
	}
	
	@GetMapping("/restaurantes/com-frete-gratis")
	public List<Restaurante> restaurantesComFreteGratis(String nome){
		// Importação estatica. Se usando eclipse Adicione a fabrica "RestauranteSpec" em favoritos nas Preferências.
		return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
	}
	
	@GetMapping("/restaurantes/primeiro")
	public Optional<Restaurante> restaurantePrimeiro(){
		return restauranteRepository.buscarPrimeiro();
	}
	
	@GetMapping("/cozinhas/primeira")
	public Optional<Cozinha> cozinhaPrimeira(){
		return cozinhaRepository.buscarPrimeiro();
	}
}
