package br.com.cruzetafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.cruzetafood.domain.exception.CidadeNaoEncontradaException;
import br.com.cruzetafood.domain.exception.EntidadeEmUsoException;
import br.com.cruzetafood.domain.model.Cidade;
import br.com.cruzetafood.domain.model.Estado;
import br.com.cruzetafood.domain.repository.CidadeRepository;
import br.com.cruzetafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CadastroEstadoService cadastroEstadoService;

//	private static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe um cadastro de cidade com o código %d";
	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida pois está em uso.";

	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Estado estado = cadastroEstadoService.buscarOuFalhar(estadoId);
		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}

	public ResponseEntity<Cidade> remover(Long cidadeId) {
		try {
			cidadeRepository.delete(cidadeRepository.findById(cidadeId).get());
			return ResponseEntity.noContent().build();

		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(cidadeId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, cidadeId));
		}
	}

	public Cidade buscarOuFalhar(Long cidadeId) {
		return cidadeRepository.findById(cidadeId).orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
	}
}
