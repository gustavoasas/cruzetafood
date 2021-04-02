package br.com.cruzetafood.core.validation;

import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidacaoException extends RuntimeException {
	private static final long serialVersionUID = 2591215520541418126L;
	private BindingResult bindResult;
}
