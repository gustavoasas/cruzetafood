package br.com.localdomain.cruzetafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.localdomain.cruzetafood.domain.model.Cidade;

/**
 * 
 * @Author André Gustavo
 *
 */
@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

}
