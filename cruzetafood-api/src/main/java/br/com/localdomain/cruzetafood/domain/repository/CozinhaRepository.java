package br.com.localdomain.cruzetafood.domain.repository;

import java.util.List;

import br.com.localdomain.cruzetafood.domain.model.Cozinha;

/**
 * 
 * @Author André Gustavo
 *
 */
public interface CozinhaRepository {

	List<Cozinha> listar();
	Cozinha buscarPorId(Long id);
	Cozinha salvar(Cozinha cozinha);
	void remover(Long id);
	
}
