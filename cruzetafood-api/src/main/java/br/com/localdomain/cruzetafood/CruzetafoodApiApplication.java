package br.com.localdomain.cruzetafood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.com.localdomain.cruzetafood.domain.repository.CustomJpaRepository;
import br.com.localdomain.cruzetafood.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class CruzetafoodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruzetafoodApiApplication.class, args);
	}

}
