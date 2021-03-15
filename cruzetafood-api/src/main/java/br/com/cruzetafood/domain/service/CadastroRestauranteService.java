package br.com.cruzetafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.cruzetafood.domain.exception.EntidadeEmUsoException;
import br.com.cruzetafood.domain.exception.RestauranteNaoEncontradoException;
import br.com.cruzetafood.domain.model.Cozinha;
import br.com.cruzetafood.domain.model.Restaurante;
import br.com.cruzetafood.domain.repository.CozinhaRepository;
import br.com.cruzetafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removida pois está em uso.";
	private static final String MSG_COZINHA_NAO_CADASTRADA = "Não existe cadastro de cozinha com o código %d";

	@Autowired
	private RestauranteRepository restauranteRepository;
	@Autowired
	private CozinhaRepository cozinhaRepository;
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

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
		Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
		restaurante.setCozinha(cozinha);
		return restauranteRepository.save(restaurante);
	}
	
	public ResponseEntity<Restaurante> remover(Long restauranteId) {
		try {
			restauranteRepository.delete(restauranteRepository.findById(restauranteId).get());
			return ResponseEntity.noContent().build();

		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradoException(restauranteId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_RESTAURANTE_EM_USO, restauranteId));
		}
	}
	
	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId).orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}
}
