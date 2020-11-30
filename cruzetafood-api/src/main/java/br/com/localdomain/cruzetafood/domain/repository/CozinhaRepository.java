package br.com.localdomain.cruzetafood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.localdomain.cruzetafood.domain.model.Cozinha;

/**
 * 
 * @Author André Gustavo
 *
 */
@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long>{

	List<Cozinha> nome(String nome);
	
}
