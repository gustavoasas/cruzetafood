package br.com.cruzetafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.cruzetafood.CruzetafoodApiApplication;
import br.com.cruzetafood.domain.model.Cozinha;
import br.com.cruzetafood.domain.repository.CozinhaRepository;

public class AlteracaoCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(CruzetafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		CozinhaRepository repository = applicationContext.getBean(CozinhaRepository.class);
		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(1L);
		
		repository.deleteById(cozinha.getId());
	}
}
