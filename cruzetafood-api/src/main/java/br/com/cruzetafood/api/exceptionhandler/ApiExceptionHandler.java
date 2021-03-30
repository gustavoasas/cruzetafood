package br.com.cruzetafood.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import br.com.cruzetafood.api.enums.ProblemType;
import br.com.cruzetafood.domain.exception.EntidadeEmUsoException;
import br.com.cruzetafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.cruzetafood.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	protected String MSG_PROPRIEDADE_NAO_EXISTE = "A propriedade '%s' não existe. Corrija ou remova essa propriedade e tente novamente";
	protected String MSG_INVALID_FORMAT_EXCEPTION = "A propriedade '%s' recebeu o valor '%s' que é de um tipo incompatível. Corrija e informe um valor compatível como o tipo: %s";
	protected String MSG_ARGUMENT_TYPE_MISMATCH = "O parâmetro de URL '%s' recebeu o valor '%s', que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s";
	protected String MSG_RECURSO_NAO_ENCONTRADO	= "O Recurso '%s' que você tentou acessar é inesistente";
	protected String MSG_ERRO_GENERICO_USUARIO_FINAL = "Ocorreu um erro inesperado no sistema, tente novamente mais tarde. Se o problema persistir, entre em contato com o administrador do sistema";
	protected String MSG_DADOS_INVALIDOS = "Um ou mais campos estão inválidos. Faça o preencimento correto e tente novamente.";
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe";
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if (rootCause instanceof PropertyBindingException)
			return handlePropertyBindException((PropertyBindingException) rootCause, headers, status, request);
		
		if (rootCause instanceof InvalidFormatException)
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
	
		Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		if(ex instanceof MethodArgumentTypeMismatchException)
			return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException)ex, headers, status, request);
		
		return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	/**
	 * Tratando especificamente algum erro de sintax que possa vir no json enviado. Para esse caso
	 * o tratamento irá informar uma mensagem mais detalhada para o consumidor da API de forma a 
	 * ajudar no melhor entendimento da causa do problema.
	 *  
	 * <p>
	 *   Ex: 	No exemplo abaixo o campo "cozinha.id" do tipo "Long" possui um valor "aaa" do tipo String
	 *   		que não é compativel e portanto o java não fará o parse lancando uma excessão do tipo:
	 *   		InvalidFormatException.
	 * </p>
	 * <pre>
	 *   {
	 *		"nome" : "Thai Gourmet Mineira",
	 *	    "taxaFrete" : 13,
	 *	    "cozinha" : {
	 *	        "id" : "aaa"
	 *	    }
	 *	 }
	 * </pre>
	 *   
	 * @Author André Gustavo
	 * @param ex
	 * @param headers
	 * @param status
	 * @param request
	 * @return
	 */
	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String path = joinPathAndFieldNameAsString(ex.getPath());
		String detail = String.format(MSG_INVALID_FORMAT_EXCEPTION, path, ex.getValue(), ex.getTargetType().getSimpleName());
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
				.build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handlePropertyBindException(PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		String path = joinPathAndFieldNameAsString(ex.getPath());
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format(MSG_PROPRIEDADE_NAO_EXISTE, path);
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
				.build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
		String detail = String.format(MSG_PROPRIEDADE_NAO_EXISTE);
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
				.build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = String.format(MSG_RECURSO_NAO_ENCONTRADO, ex.getRequestURL());
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
				.build();
		return handleExceptionInternal(ex, problem, headers, status, request);		
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		String detail = String.format(MSG_DADOS_INVALIDOS);
		BindingResult bindingResult = ex.getBindingResult();
		List<Problem.field> problemFields = bindingResult.getFieldErrors().stream()
				.map(fieldError -> Problem.field.builder()
						.nome(fieldError.getField())
						.userMessage(fieldError.getDefaultMessage())
						.build())
				.collect(Collectors.toList());
						
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_DADOS_INVALIDOS)
				.fields(problemFields)
				.build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request){
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
		String detail = String.format(MSG_ERRO_GENERICO_USUARIO_FINAL);
		ex.printStackTrace();
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
				.build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	private String joinPathAndFieldNameAsString(List<Reference> references) {
		return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		Problem problem = createProblemBuilder(status, ProblemType.RECURSO_NAO_ENCONTRADO, ex.getMessage())
				.userMessage(ex.getMessage())
				.build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Problem problem = createProblemBuilder(status, ProblemType.ERRO_NEGOCIO, ex.getMessage())
				.userMessage(ex.getMessage())
				.build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		Problem problem = createProblemBuilder(status, ProblemType.ENTIDADE_EM_USO, ex.getMessage())
				.userMessage(ex.getMessage())
				.build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		if (null == body)
			body = Problem.builder().title(status.getReasonPhrase()).status(status.value())
			.userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
			.build();
		else if (body instanceof String)
			body = Problem.builder().title((String) body).status(status.value())
			.userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
			.build();
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
		return Problem.builder().status(status.value()).timestamp(LocalDateTime.now()).type(problemType.getUri()).title(problemType.getTitle()).detail(detail);
	}
	
	
}