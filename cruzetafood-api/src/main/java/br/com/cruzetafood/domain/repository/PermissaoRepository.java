package br.com.cruzetafood.domain.repository;

import java.util.List;

import br.com.cruzetafood.domain.model.Permissao;

/**
 * 
 * @Author André Gustavo
 *
 */
public interface PermissaoRepository {

	List<Permissao> listar();
    Permissao buscar(Long id);
    Permissao salvar(Permissao permissao);
    void remover(Permissao permissao);
	
}
