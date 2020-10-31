package br.com.localdomain.cruzetafood.domain.repository;

import java.util.List;

import br.com.localdomain.cruzetafood.domain.model.Restaurante;

/**
 * 
 * @Author André Gustavo
 *
 */
public interface RestauranteRepository {

	List<Restaurante> todos();
	Restaurante buscarPorId(Long id);
	Restaurante adicionar(Restaurante restaurante);
	void remover(Restaurante restaurante);
	
}
