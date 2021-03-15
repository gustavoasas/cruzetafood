package br.com.cruzetafood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 4763758158917779029L;
	public RestauranteNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public RestauranteNaoEncontradoException(Long restauranteId) {
		this(String.format("Não existe um cadastro de restaurante com o código %d", restauranteId));
	}
}
