package br.com.cruzetafood.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

/**
 * <p>
 * Para esta implementação foram respeitados as determinações da RFC7807 que regem os padrões
 * de Detalhes de Problemas em APIs HTTP.
 * </p>
 * 
 * <p>
 * Esta norma define um padrão para evitar a necessidade de definição de novos formatos de 
 * respostas de erros para APIs HTTP. 
 * </p>
 * 
 * <p>
 * Os detalhes da RFC7807 podem ser encontrados em: <a href="https://tools.ietf.org/html/rfc7807">RFC7807 - Problem Details for HTTP APIs</a>
 * </p>
 * 
 * @author André Gustavo 
 *
 */
@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public class Problem {
	private Integer status;
	private LocalDateTime timestamp;
	private String type;
	private String title;
	private String detail;
	private String userMessage;
	private List<field> fields;
	
	@Getter
	@Builder
	/**
	 * TODO: extrair essa classe propria 
	 * @Author André Gustavo
	 *
	 */
	public static class field {
		private String nome;
		private String userMessage;
	}
}
