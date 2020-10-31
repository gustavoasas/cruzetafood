package br.com.localdomain.cruzetafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.localdomain.cruzetafood.domain.exception.EntidadeEmUsoException;
import br.com.localdomain.cruzetafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.localdomain.cruzetafood.domain.model.Cozinha;
import br.com.localdomain.cruzetafood.domain.model.Restaurante;
import br.com.localdomain.cruzetafood.domain.repository.CozinhaRepository;
import br.com.localdomain.cruzetafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;

	/**
	 * Salvando um restaurante e verificando se a cozinha que foi informada existe para inserção,
	 * caso não exista será lançada uma exceção de {@code EntidadeNaoEncontradaException.CLASS}
	 * 
	 * @Author André Gustavo
	 * @param restaurante
	 * @return
	 */
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.buscarPorId(cozinhaId);
		if (cozinha == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de cozinha com o código %d", cozinhaId));
		}
		return restauranteRepository.adicionar(restaurante);			
	}
	
	public ResponseEntity<Restaurante> remover(Long restauranteId) {
		try {
			restauranteRepository.remover(restauranteRepository.buscarPorId(restauranteId));
			return ResponseEntity.noContent().build();

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de restaurante com o código %d", restauranteId));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Restaurante de código %d não pode ser removida pois está em uso.", restauranteId));
		}
	}
}
