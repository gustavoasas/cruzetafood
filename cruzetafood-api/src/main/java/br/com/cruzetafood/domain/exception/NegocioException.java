package br.com.cruzetafood.domain.exception;

//@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 4763758158917779029L;
	
	public NegocioException(String mensagem) {
		super(mensagem);
	}
	
	public NegocioException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
