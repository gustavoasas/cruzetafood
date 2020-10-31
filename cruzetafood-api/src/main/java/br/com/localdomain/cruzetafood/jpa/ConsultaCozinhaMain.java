package br.com.localdomain.cruzetafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.localdomain.cruzetafood.CruzetafoodApiApplication;
import br.com.localdomain.cruzetafood.domain.model.Cozinha;
import br.com.localdomain.cruzetafood.domain.repository.CozinhaRepository;

public class ConsultaCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(CruzetafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		CozinhaRepository cozinhas = applicationContext.getBean(CozinhaRepository.class);
		
		List<Cozinha> todasCozinhas = cozinhas.listar();
		for (Cozinha c : todasCozinhas) {
			System.out.println(c.getNome());
		}
		
//		Cozinha cozinha1 = new Cozinha();
//		cozinha1.setNome("Brasileira");
//		
//		Cozinha cozinha2 = new Cozinha();
//		cozinha2.setNome("Japonesa");
//		
//		cozinha1 = cozinhas.adicionar(cozinha1);
//		cozinha2 = cozinhas.adicionar(cozinha2);
//		
//		System.out.printf("%d - %s\n",cozinha1.getId(), cozinha1.getNome());
//		System.out.printf("%d - %s\n", cozinha2.getId(), cozinha2.getNome());
		
		//Cozinha cozinha = cadastroCozinha.buscar(1L);	
		
//		//System.out.printf("%d - %s\n", cozinha.getId(), cozinha.getNome());
	}

}
