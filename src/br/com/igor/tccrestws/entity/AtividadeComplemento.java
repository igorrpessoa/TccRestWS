package br.com.igor.tccrestws.entity;

public class AtividadeComplemento {

	public static final String ID = "id";
	public static final String NOME = "nome";
	public static final String COMPLEMENTO = "complemento";
	public static final String ATIVIDADE = "atividade";

	public AtividadeComplemento(){}
	public AtividadeComplemento(Atividade atividade, Complemento complemento) {
		super();
		this.atividade = atividade;
		this.complemento = complemento;
	}
	private Integer id;
	private String nome;
	private Atividade atividade;
	private Complemento complemento;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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
