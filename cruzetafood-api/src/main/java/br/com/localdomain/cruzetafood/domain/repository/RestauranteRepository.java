package br.com.localdomain.cruzetafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.localdomain.cruzetafood.domain.model.Restaurante;

/**
 * 
 * @Author André Gustavo
 *
 */
@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>{
	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
}
