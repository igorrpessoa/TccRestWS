package br.com.igor.tccrestws.entity;

public class UsuarioResposta {

	public static final String ID = "id";
	public static final String USUARIO_ATIVIDADE = "usuario_atividade_id";
	public static final String RESPOSTA = "resposta";

	public UsuarioResposta(Integer id,UsuarioAtividade usuarioAtividade,String resposta){
		this.id = id;
		this.usuarioAtividade = usuarioAtividade;
		this.resposta = resposta;
	}
		
	private Integer id;
	private UsuarioAtividade usuarioAtividade;
	private String resposta;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public UsuarioAtividade getUsuarioAtividade() {
		return usuarioAtividade;
	}
	public void setUsuarioAtividade(UsuarioAtividade usuarioAtividade) {
		this.usuarioAtividade = usuarioAtividade;
	}
	public String getResposta() {
		return resposta;
	}
	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
	
	
}
