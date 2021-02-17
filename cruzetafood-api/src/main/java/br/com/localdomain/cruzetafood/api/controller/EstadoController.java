package br.com.localdomain.cruzetafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.localdomain.cruzetafood.domain.model.Estado;
import br.com.localdomain.cruzetafood.domain.repository.EstadoRepository;
import br.com.localdomain.cruzetafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository repository;
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	@GetMapping
	public List<Estado> listar(){
		return repository.findAll();  
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado adicionar(@RequestBody Estado estado) {
		return cadastroEstadoService.salvar(estado);
		
//		try {
//			estado = repository.save(estado);
//			return ResponseEntity.status(HttpStatus.CREATED).body(estado);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
	}
	
	@PutMapping("/{estadoId}")
	public Estado atualizar(@PathVariable Long estadoId, @RequestBody Estado estado) {
		Estado estadoAtual = cadastroEstadoService.buscarOuFalhar(estadoId);
		BeanUtils.copyProperties(estado, estadoAtual, "id");
		return cadastroEstadoService.salvar(estadoAtual);
	}
	
	@DeleteMapping("/{estadoId}")
	public ResponseEntity<?> remover(@PathVariable Long estadoId){
		try {
			Optional<Estado> estado = repository.findById(estadoId);
			repository.delete(estado.get());
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
