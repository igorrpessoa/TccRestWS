package br.com.igor.tccrestws.entity;

public class UsuarioAtividade {

	public static final String ID = "id";
	public static final String USUARIO = "usuario_id";
	public static final String ATIVIDADE_COMPLEMENTO = "atividade_complemento_id";
	public static final String FREQUENCIA = "frequencia";
	public static final String SATISFACAO = "satisfacao";
	public static final String PERFIL = "perfil_id";
	public static final String RELACAO = "relacao";
	public static final String RESPOSTA = "resposta";

	public UsuarioAtividade(){}
	
	public UsuarioAtividade(Integer id, Usuario usuario, AtividadeComplemento atividadeComplemento, Double frequencia, Double satisfacao,Double relacao,String resposta) {
		this.id = id;
		this.usuario = usuario;
		this.atividadeComplemento = atividadeComplemento;
		this.frequencia = frequencia;
		this.satisfacao = satisfacao;
	}
	
	private Integer id;
	private Usuario usuario;
	private AtividadeComplemento atividadeComplemento;
	private Double frequencia;
	private Double satisfacao;
	private Perfil perfil;
	private Double relacao;
	private String resposta;

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
	public AtividadeComplemento getAtividadeComplemento() {
		return atividadeComplemento;
	}
	public void setAtividadeComplemento(AtividadeComplemento atividadeComplemento) {
		this.atividadeComplemento = atividadeComplemento;
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

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Double getRelacao() {
		return relacao;
	}

	public void setRelacao(Double relacao) {
		this.relacao = relacao;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
	
	
}
