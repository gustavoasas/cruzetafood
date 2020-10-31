package br.com.localdomain.cruzetafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.localdomain.cruzetafood.domain.model.Restaurante;
import br.com.localdomain.cruzetafood.domain.repository.RestauranteRepository;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Restaurante> todos(){
		return manager.createQuery("from Restaurante", Restaurante.class).getResultList();
	}
	
	@Override
	public Restaurante buscarPorId(Long id) {
		return manager.find(Restaurante.class, id);
	}
	
	@Override
	@Transactional
	public Restaurante adicionar(Restaurante restaurante) {
		return manager.merge(restaurante);
	}
	
	@Override
	@Transactional
	public void remover(Restaurante restaurante) {
		restaurante = buscarPorId(restaurante.getId());
		manager.remove(restaurante);
	}
}
