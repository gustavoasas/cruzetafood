package br.com.localdomain.cruzetafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.localdomain.cruzetafood.domain.model.Restaurante;

/**
 * 
 * @Author André Gustavo
 *
 */
@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>{}
