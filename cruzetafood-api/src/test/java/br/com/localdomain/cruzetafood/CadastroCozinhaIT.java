package br.com.localdomain.cruzetafood;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.cruzetafood.CruzetafoodApiApplication;
import br.com.cruzetafood.domain.model.Cozinha;
import br.com.cruzetafood.domain.repository.CozinhaRepository;
import br.com.localdomain.cruzetafood.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;


/**
 * Para poder usar a classe DatabaseCleaner injetado eu precisei adicionar ela para usar o contexto, mesmo ela estando anotada com @context
 * isso não é suficiente.
 * 
 * A partir do Spring 3.1 M2 foi adicionado um movo AnnotationConfigContextLoader, e a anotação @ContextConfiguration
 * também foi atualizada para suportar as declarações de classes para orientar o TestContext quais arquivos de configuração
 * usar para carregar o ApplicationContext para o teste.
 * 
 * Fonte: https://spring.io/blog/2011/06/21/spring-3-1-m2-testing-with-configuration-classes-and-profiles
 * 
 * @Author André Gustavo
 *
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CruzetafoodApiApplication.class, DatabaseCleaner.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {
	
	@LocalServerPort
	public int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	private Cozinha cozinhaAmericana;
	@SuppressWarnings("unused")
	private Cozinha cozinhaTailandesa;
	private int quantidadeCozinhasCadastradas;
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		//flyway.migrate();
		databaseCleaner.clearTables();
		this.prepare();
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		System.out.println("Método: deveRetornarStatus200_QuandoConsultarCozinhas");
		given()
			.accept(ContentType.JSON)
			.when().get().then()
			.statusCode(HttpStatus.OK.value());
	}
	
	
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
		System.out.println("Método: deveRetornarStatus201_QuandoCadastrarCozinha");
		  given()
			.body(" {\"nome\": \"Chinesa\" } ")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void testarQuantidadeDeCozinhasCadastradas() {
		System.out.println("Método: testarQuantidadeDeCozinhasCadastradas");
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(quantidadeCozinhasCadastradas));
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
		System.out.println("Método: deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente");
		given()
			.pathParam("cozinhaId", cozinhaAmericana.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(cozinhaAmericana.getNome().toString()));
	}
	
	@Test
	public void deveRetornarRespostaEStatusQuatrocentosEQuatro_QuandoConsultarCozinhaInexistente() {
		System.out.println("Método: deveRetornarRespostaEStatusQuatrocentosEQuatro_QuandoConsultarCozinhaInexistente");
		given()
			.pathParam("cozinhaId", 100)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private void prepare() {
		cozinhaTailandesa = cadastrarCozinha("Tailandesa");
		cozinhaAmericana = cadastrarCozinha("Americana");
		quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
	}
	
	private Cozinha cadastrarCozinha(String nome) {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome(nome);
		return cozinhaRepository.save(cozinha);
	}
}
