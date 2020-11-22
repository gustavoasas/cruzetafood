package br.com.localdomain.cruzetafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.localdomain.cruzetafood.domain.model.Estado;

/**
 * 
 * @Author André Gustavo
 *
 */
@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long>{}
