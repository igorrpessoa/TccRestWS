package br.com.igor.tccrestws.entity;

import java.util.List;

public class AtividadeComplemento {

	public static final String ID = "id";
	public static final String COMPLEMENTO = "complemento_id";
	public static final String ATIVIDADE = "atividade_id";

	public AtividadeComplemento(){}
	public AtividadeComplemento(Atividade atividade, Complemento complemento) {
		super();
		this.atividade = atividade;
		this.complemento = complemento;
	}
	private Integer id;
	private Atividade atividade;
	private Complemento complemento;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Atividade getAtividade() {
		return atividade;
	}
	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}
	public Complemento getComplemento() {
		return complemento;
	}
	public void setComplemento(Complemento complemento) {
		this.complemento = complemento;
	}
}
