package br.com.localdomain.cruzetafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.localdomain.cruzetafood.domain.model.Permissao;
import br.com.localdomain.cruzetafood.domain.repository.PermissaoRepository;

@Repository
public class PermissaoRepositoryImpl implements PermissaoRepository {

	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Permissao> listar() {
		return manager.createQuery("from Permissao", Permissao.class).getResultList();
	}

	@Override
	public Permissao buscar(Long id) {
		return manager.find(Permissao.class, id);
	}

	@Override
	public Permissao salvar(Permissao permissao) {
		return manager.merge(permissao);
	}

	@Override
	public void remover(Permissao permissao) {
		manager.remove(permissao);
	}
}
