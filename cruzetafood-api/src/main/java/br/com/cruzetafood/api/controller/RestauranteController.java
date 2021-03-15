package br.com.cruzetafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cruzetafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.cruzetafood.domain.exception.NegocioException;
import br.com.cruzetafood.domain.model.Restaurante;
import br.com.cruzetafood.domain.repository.CozinhaRepository;
import br.com.cruzetafood.domain.repository.RestauranteRepository;
import br.com.cruzetafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	private final String[] CAMPOS_IGNORADOS_PARA_COPIA = {"id", "formasPagamento", "endereco", "dataCadastro"};
	
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
	public Restaurante adicionar(@RequestBody Restaurante restaurante) {
		try {
			return cadastroRestauranteService.salvar(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public Restaurante atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
		Restaurante restauranteRetornado = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		BeanUtils.copyProperties(restaurante, restauranteRetornado, this.CAMPOS_IGNORADOS_PARA_COPIA);
		try {
			return cadastroRestauranteService.salvar(restauranteRetornado);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
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

	@PatchMapping("/{restauranteId}")
	@Deprecated
	public ResponseEntity<?> aualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos) {
		Optional<Restaurante> restauranteAtual = repository.findById(restauranteId);
		if (!restauranteAtual.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		merge(campos, restauranteAtual.get());
		//return atualizar(restauranteId, restauranteAtual.get());
		return null;
	}

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
	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);

			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			System.out.println(nomePropriedade + " - " + valorPropriedade);

			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}
}
