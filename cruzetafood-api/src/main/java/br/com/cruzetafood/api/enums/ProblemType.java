package br.com.cruzetafood.api.enums;

import lombok.Getter;

@Getter
public enum ProblemType {

	MENSAGEM_INCOMPREENSIVEL("/mensagem-imcompreensivel", "Mensagem incompreensílvel"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
	RECURSO_NAO_ENCONTRADO("/recurso_nao_encontrado", "Recurso não encontrado"),
	ERRO_DE_SISTEMA("/erro_de_sistema", "Erro de sistema"),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
	DADOS_INVALIDOS("/parametro-invalido", "Dados inválidos");
	
	private String title;
	private String uri;
	
	private ProblemType(String path, String title) {
		this.uri = "http://cruzetafood.com.br" + path;
		this.title = title;
	}
}
