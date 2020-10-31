package br.com.localdomain.cruzetafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.localdomain.cruzetafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.localdomain.cruzetafood.domain.model.Restaurante;
import br.com.localdomain.cruzetafood.domain.repository.CozinhaRepository;
import br.com.localdomain.cruzetafood.domain.repository.RestauranteRepository;
import br.com.localdomain.cruzetafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository repository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private CozinhaRepository cozinhas;

	/**
	 * Fixando a resposta do conteúdo como XML apenas. Usando a biblioteca
	 * do Jackson configurada no arquivo POM.xml.
	 * @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	 * 
	 * @Author André Gustavo
	 * @return
	 */
	@GetMapping
	public List<Restaurante> listar(){
		return repository.todos();
	}
	
	@GetMapping("/{restauranteId}")
	public Restaurante buscar(@PathVariable Long restauranteId) {
		return repository.buscarPorId(restauranteId);
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = cadastroRestaurante.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{restauranteId}")	
	public ResponseEntity<Restaurante> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
		Restaurante r = repository.buscarPorId(restauranteId);
		
		if (r != null) {
			//Copiando todas as propriedades de um objeto para o outro, ignorando o primeiro atribulto "id"
			BeanUtils.copyProperties(restaurante, r, "id");
			cadastroRestaurante.salvar(r);
			return ResponseEntity.ok(r);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> remover(@PathVariable Long restauranteId) {
		try {
			cadastroRestaurante.remover(restauranteId);
			return ResponseEntity.noContent().build();
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> aualizarParcial(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
		System.out.println(restaurante);
		
		return null;		
	}
}
