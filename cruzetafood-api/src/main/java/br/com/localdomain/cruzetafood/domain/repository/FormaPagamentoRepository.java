package br.com.localdomain.cruzetafood.domain.repository;

import java.util.List;

import br.com.localdomain.cruzetafood.domain.model.Cidade;

/**
 * 
 * @Author André Gustavo
 *
 */
public interface FormaPagamentoRepository {

	List<Cidade> listar();
    Cidade buscar(Long id);
    Cidade salvar(Cidade cidade);
    void remover(Cidade cidade);
	
}
