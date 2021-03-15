package br.com.cruzetafood.domain.exception;

public abstract class EntidadeNaoEncontradaException extends NegocioException {

	private static final long serialVersionUID = 4763758158917779029L;
	
	public EntidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

}
