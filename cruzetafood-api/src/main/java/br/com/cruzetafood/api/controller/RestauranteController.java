package br.com.cruzetafood.api.controller;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingErrorProcessor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cruzetafood.core.validation.ValidacaoException;
import br.com.cruzetafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.cruzetafood.domain.exception.NegocioException;
import br.com.cruzetafood.domain.model.Restaurante;
import br.com.cruzetafood.domain.repository.CozinhaRepository;
import br.com.cruzetafood.domain.repository.RestauranteRepository;
import br.com.cruzetafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	private final String[] CAMPOS_IGNORADOS_PARA_COPIA = {"id", "formasPagamento", "endereco", "dataCadastro", "dataAtualizacao"};
	
	@Autowired
	private RestauranteRepository repository;

	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;

	@Autowired
	private CozinhaRepository cozinhas;
	

	/**
	 * Fixando a resposta do conteúdo como XML apenas. Usando a biblioteca do
	 * Jackson configurada no arquivo POM.xml.
	 * 
	 * @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	 * 
	 * @Author André Gustavo
	 * @return
	 */
	@GetMapping
	public List<Restaurante> listar() {
		return repository.findAll();
	}

	@GetMapping("/{restauranteId}")
	public Restaurante buscar(@PathVariable Long restauranteId) {
		return repository.findById(restauranteId).get();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante adicionar(@RequestBody @Valid Restaurante restaurante) {
		try {
			return cadastroRestauranteService.salvar(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public Restaurante atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
		Restaurante restauranteRetornado = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		restauranteRetornado = atribuiDadosDeAtualizacao(restauranteRetornado);
		BeanUtils.copyProperties(restaurante, restauranteRetornado, this.CAMPOS_IGNORADOS_PARA_COPIA);
		try {
			return cadastroRestauranteService.salvar(restauranteRetornado);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PatchMapping("/{restauranteId}")
	public Restaurante atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos, HttpServletRequest request) {
		Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		merge(campos, restauranteAtual, request);
		validate(restauranteAtual, "restaurante");
		return atualizar(restauranteId, restauranteAtual);
	}
	
	private Restaurante atribuiDadosDeAtualizacao(Restaurante restaurante) {
		restaurante.setDataAtualizacao(LocalDateTime.now());
		return restaurante;
	}

	@DeleteMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> remover(@PathVariable Long restauranteId) {
		try {
			cadastroRestauranteService.remover(restauranteId);
			return ResponseEntity.noContent().build();
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.notFound().build();
		}
	}

//	@PatchMapping("/{restauranteId}")
//	@Deprecated
//	public ResponseEntity<?> aualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos, HttpServletRequest request) {
//		Optional<Restaurante> restauranteAtual = repository.findById(restauranteId);
//		if (!restauranteAtual.isPresent()) {
//			return ResponseEntity.notFound().build();
//		}
//		merge(campos, restauranteAtual.get(), request);
//		//return atualizar(restauranteId, restauranteAtual.get());
//		return null;
//	}

	/**
	 * Classe responsável por fazer o merge dos dados do objeto recebido via
	 * parâmetro com o objeto do tipo {@link: Restaurante} que será atualizado
	 * usando reflection.
	 * 
	 * Para Evitar problemas de conversão de dados os campos (dadosOrigem) que foram
	 * enviados estão sendo convertidos para o tipo do objeto pertencente na classe
	 * através do utilitário {@link: ObjectMapper} do Jackson.
	 * 
	 * @Author André Gustavo
	 */
	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
		ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(request);
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				field.setAccessible(true);
				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
				System.out.println(nomePropriedade + " - " + valorPropriedade);
	
				ReflectionUtils.setField(field, restauranteDestino, novoValor);				
			});
		} catch (IllegalArgumentException ex) {
			Throwable rootCause = ExceptionUtils.getRootCause(ex);
			throw new HttpMessageNotReadableException(ex.getMessage(), rootCause, servletServerHttpRequest);
		}
	}

	private void validate(Restaurante restauranteOrigem, String objectName) {
		BeanPropertyBindingResult beanPropertyBindResult = new BeanPropertyBindingResult(restauranteOrigem, objectName);
		if(beanPropertyBindResult.hasErrors())
			throw new ValidacaoException(beanPropertyBindResult);
	}
}
