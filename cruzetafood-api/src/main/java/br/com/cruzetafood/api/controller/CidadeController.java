package br.com.cruzetafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cruzetafood.domain.exception.EstadoNaoEncontradoException;
import br.com.cruzetafood.domain.exception.NegocioException;
import br.com.cruzetafood.domain.model.Cidade;
import br.com.cruzetafood.domain.repository.CidadeRepository;
import br.com.cruzetafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository repository;
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@GetMapping
	public List<Cidade> listar(){
		return repository.findAll();  
	}
	
	@GetMapping("/{cidadeId}")
	public Cidade buscar(@PathVariable Long cidadeId) {
		return cadastroCidadeService.buscarOuFalhar(cidadeId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade adicionar(@RequestBody @Valid Cidade cidade){
		try {
			return cadastroCidadeService.salvar(cidade);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{cidadeId}")
	public Cidade atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {
		Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(cidadeId);
		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		try {
			return cadastroCidadeService.salvar(cidadeAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		cadastroCidadeService.remover(cidadeId);
	}
}
