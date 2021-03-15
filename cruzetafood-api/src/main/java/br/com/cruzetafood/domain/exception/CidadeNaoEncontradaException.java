package br.com.cruzetafood.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 4763758158917779029L;
	public CidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public CidadeNaoEncontradaException(Long cidadeId) {
		this(String.format("Não existe um cadastro de Cidade com o código %d", cidadeId));
	}
}
