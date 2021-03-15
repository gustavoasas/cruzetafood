package br.com.cruzetafood.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 4763758158917779029L;
	public EstadoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public EstadoNaoEncontradoException(Long estadoId) {
		this(String.format("Não existe um cadastro de estado com o código %d", estadoId));
	}
}
