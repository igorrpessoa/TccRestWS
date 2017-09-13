package br.com.igor.tccrestws.entity;

public class UsuarioAtividade {

	public static final String ID = "id";
	public static final String USUARIO = "usuario_id";
	public static final String ATIVIDADE = "atividade_id";
	public static final String FREQUENCIA = "frequencia";
	public static final String SATISFACAO = "satisfacao";
	public static final String DESCRICAO = "descricao";
	
	public UsuarioAtividade(){}
	
	public UsuarioAtividade(Integer id, Usuario usuario, Atividade atividade, Double frequencia, Double satisfacao,String descricao) {
		this.id = id;
		this.usuario = usuario;
		this.atividade = atividade;
		this.frequencia = frequencia;
		this.satisfacao = satisfacao;
		this.descricao = descricao;
	}
	
	private Integer id;
	private Usuario usuario;
	private Atividade atividade;
	private Double frequencia;
	private Double satisfacao;
	private String descricao;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Atividade getAtividade() {
		return atividade;
	}
	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}
	public Double getFrequencia() {
		return frequencia;
	}
	public void setFrequencia(Double frequencia) {
		this.frequencia = frequencia;
	}
	public Double getSatisfacao() {
		return satisfacao;
	}
	public void setSatisfacao(Double satisfacao) {
		this.satisfacao = satisfacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
}
