package br.com.localdomain.cruzetafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.localdomain.cruzetafood.domain.exception.EntidadeEmUsoException;
import br.com.localdomain.cruzetafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.localdomain.cruzetafood.domain.model.Cozinha;
import br.com.localdomain.cruzetafood.domain.repository.CozinhaRepository;
import br.com.localdomain.cruzetafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@GetMapping
	public List<Cozinha> litar(){
	
		return cozinhaRepository.listar();
	}
	
	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) {
		Cozinha cozinha = cozinhaRepository.buscarPorId(cozinhaId);
		// return ResponseEntity.status(HttpStatus.OK).body(cozinha);
		//return ResponseEntity.ok(cozinha);
		if (cozinha != null) {
			return ResponseEntity.ok(cozinha);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {
		return cadastroCozinha.salvar(cozinha);
	}
	
	@PutMapping("/{cozinhaId}")	
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
		Cozinha c = cozinhaRepository.buscarPorId(cozinhaId);
		
		if (c != null) {
			//Copiando todas as propriedades de um objeto para o outro, ignorando o primeiro atribulto "id"
			BeanUtils.copyProperties(cozinha, c, "id");
			cadastroCozinha.salvar(c);
			return ResponseEntity.ok(c);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId) {
		try {
			cadastroCozinha.excluir(cozinhaId);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
