package br.com.localdomain.cruzetafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.localdomain.cruzetafood.CruzetafoodApiApplication;
import br.com.localdomain.cruzetafood.domain.model.Restaurante;
import br.com.localdomain.cruzetafood.domain.repository.RestauranteRepository;

public class ConsultaRestauranteMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(CruzetafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		RestauranteRepository restaurantes = applicationContext.getBean(RestauranteRepository.class);
		
		List<Restaurante> todosRestaurantes = restaurantes.findAll();
		for (Restaurante r : todosRestaurantes) {
			System.out.printf("%s - %f - %s \n", r.getNome(), r.getTaxaFrete(), r.getCozinha().getNome());
		}
		
	}

}
