package br.com.localdomain.cruzetafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.localdomain.cruzetafood.domain.exception.EntidadeEmUsoException;
import br.com.localdomain.cruzetafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.localdomain.cruzetafood.domain.model.Cidade;
import br.com.localdomain.cruzetafood.domain.model.Estado;
import br.com.localdomain.cruzetafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	@Autowired
	private EstadoRepository estadoRepository;
	
	private static final String MSG_ESTADO_NAO_ENCONTRADO = "Não existe um cadastro de estado com o código %d";
	private static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removida pois está em uso.";
	
	
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);			
	}
	
	public ResponseEntity<Cidade> remover(Long estadoId) {
		try {
			estadoRepository.delete(estadoRepository.findById(estadoId).get());
			return ResponseEntity.noContent().build();

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADO_EM_USO, estadoId));
		}
	}
	
	public Estado buscarOuFalhar(Long estadoId) {
		return estadoRepository.findById(estadoId).orElseThrow(() -> new EntidadeNaoEncontradaException(MSG_ESTADO_NAO_ENCONTRADO));
	}
}
