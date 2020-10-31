package br.com.localdomain.cruzetafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.localdomain.cruzetafood.domain.model.Estado;
import br.com.localdomain.cruzetafood.domain.repository.EstadoRepository;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository repository;
	
	@GetMapping
	public List<Estado> listar(){
		System.out.println("\n\n LISTOU ESTADOS \n\n");
		return repository.listar();  
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Estado estado) {
		try {
			estado = repository.salvar(estado);
			return ResponseEntity.status(HttpStatus.CREATED).body(estado);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{estadoId}")
	public ResponseEntity<?> remover(@PathVariable Long estadoId){
		try {
			Estado estado = repository.buscar(estadoId);
			repository.remover(estado);
			return ResponseEntity.noContent().build();
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.notFound().build();
		} catch (NullPointerException e) {
			return ResponseEntity.badRequest().build();
		}
		
	}
}
