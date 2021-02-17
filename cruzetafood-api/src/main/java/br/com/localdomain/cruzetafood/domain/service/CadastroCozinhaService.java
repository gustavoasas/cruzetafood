package br.com.localdomain.cruzetafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.localdomain.cruzetafood.domain.exception.EntidadeEmUsoException;
import br.com.localdomain.cruzetafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.localdomain.cruzetafood.domain.model.Cozinha;
import br.com.localdomain.cruzetafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	private static final String MENSAGEM_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";
	private static final String MENSAGEM_COZINHA_NAO_ENCONTRADA = "Não existe um cadastro de cozinha com o código: %d";
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	public void excluir(Long cozinhaId) {
		try {
			cozinhaRepository.deleteById(cozinhaId);
		} catch (EmptyResultDataAccessException e) {
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(MENSAGEM_COZINHA_NAO_ENCONTRADA, cozinhaId));
			
//			throw new EntidadeNaoEncontradaException(
//					String.format("Não existe um cadastro de cozinha com o código: %d", cozinhaId));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MENSAGEM_COZINHA_EM_USO, cozinhaId));
		}	
	}
	
	public Cozinha buscarOuFalhar(Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId).orElseThrow( () -> new EntidadeNaoEncontradaException(String.format(MENSAGEM_COZINHA_NAO_ENCONTRADA, cozinhaId)));
	}
}
