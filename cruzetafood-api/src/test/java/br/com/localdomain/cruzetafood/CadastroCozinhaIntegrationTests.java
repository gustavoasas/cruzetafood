package br.com.localdomain.cruzetafood;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.cruzetafood.CruzetafoodApiApplication;
import br.com.cruzetafood.domain.exception.CozinhaNaoEncontradaException;
import br.com.cruzetafood.domain.exception.EntidadeEmUsoException;
import br.com.cruzetafood.domain.model.Cozinha;
import br.com.cruzetafood.domain.service.CadastroCozinhaService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CruzetafoodApiApplication.class)
@SpringBootTest(properties = "spring.main.lazy-initialization=true", classes = CadastroCozinhaIntegrationTests.class)
public class CadastroCozinhaIntegrationTests {
	
	@Autowired	
	CadastroCozinhaService cadastroCozinhaService;
	
	@Test
	public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chineza");
		
		novaCozinha = cadastroCozinhaService.salvar(novaCozinha);
		
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
		
	}
	
	@Test
	public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);
		ConstraintViolationException erroEsperado =	Assertions.assertThrows(ConstraintViolationException.class, () -> {
			cadastroCozinhaService.salvar(novaCozinha);
		});
	   assertThat(erroEsperado).isNotNull();
	}
	
	@Test
	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setId(1L);
		EntidadeEmUsoException erroEsperado =	Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
			cadastroCozinhaService.excluir(novaCozinha.getId());
		});
	   assertThat(erroEsperado).isNotNull();
	}
	
	@Test
	public void deveFalhar_QuandoExcluirCozinhaInexistente() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setId(9L);
		CozinhaNaoEncontradaException erroEsperado =	Assertions.assertThrows(CozinhaNaoEncontradaException.class, () -> {
			cadastroCozinhaService.excluir(novaCozinha.getId());
		});
	   assertThat(erroEsperado).isNotNull();
	}
}
