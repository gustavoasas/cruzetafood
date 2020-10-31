package br.com.localdomain.cruzetafood.domain.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 4763758158917779029L;
	
	public EntidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

}
