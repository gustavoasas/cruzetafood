package br.com.localdomain.cruzetafood.domain.repository;

import java.util.List;

import br.com.localdomain.cruzetafood.domain.model.Estado;

/**
 * 
 * @Author André Gustavo
 *
 */
public interface EstadoRepository {

	List<Estado> listar();
    Estado buscar(Long id);
    Estado salvar(Estado estado);
    void remover(Estado estado);
	
}
