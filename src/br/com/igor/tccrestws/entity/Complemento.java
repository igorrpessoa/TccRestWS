package br.com.igor.tccrestws.entity;

public class Complemento {
	
	public static final String ID = "id";
	public static final String NOME = "nome";
	public static final String VALIDO = "valido";

	public Complemento(){}
	public Complemento(Integer id, String nome,Integer valido) {
		super();
		this.id = id;
		this.nome = nome;
		this.valido = valido;
	}
	public Complemento(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	private Integer id;
	private String nome;
	private Integer valido;

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
	public Integer getValido() {
		return valido;
	}
	public void setValido(Integer valido) {
		this.valido = valido;
	}
	
}
