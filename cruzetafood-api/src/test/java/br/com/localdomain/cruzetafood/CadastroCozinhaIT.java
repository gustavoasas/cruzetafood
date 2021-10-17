package br.com.localdomain.cruzetafood;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.cruzetafood.CruzetafoodApiApplication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CruzetafoodApiApplication.class)
@SpringBootTest(properties = "spring.main.lazy-initialization=true", classes = CadastroCozinhaIntegrationTests.class)
public class CadastroCozinhaIT {
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		RestAssured.given()
			.basePath("/cozinhas")
			.port(8080)
			.accept(ContentType.JSON)
			.when().get().then()
			.statusCode(HttpStatus.OK.value());
	}
}
