package br.com.localdomain.cruzetafood.domain.exception;

public class EntidadeEmUsoException extends RuntimeException {

	private static final long serialVersionUID = 270969845812901186L;

	public EntidadeEmUsoException(String mensagem) {
		super(mensagem);
	}
}
