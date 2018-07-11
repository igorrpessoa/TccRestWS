package br.com.igor.tccrestws.vo;

import java.util.List;

import br.com.igor.tccrestws.entity.Atividade;
import br.com.igor.tccrestws.entity.AtividadeComplemento;
import br.com.igor.tccrestws.entity.Complemento;
import br.com.igor.tccrestws.entity.Perfil;
import br.com.igor.tccrestws.entity.Usuario;
import br.com.igor.tccrestws.entity.UsuarioAtividade;

/**
 * Created by Igor on 10/09/2017.
 */

public class UsuarioAtividadeVo {
	private Integer id;
	private Usuario usuario;
	private List<AtividadeComplemento> atividadeComplementos;
	private List<Complemento> complementos;
	private Atividade atividade;
	private Double frequencia;
	private Double satisfacao;
	private Perfil perfil;
	private Double popularidade;
	private Double relacao;
	private String resposta;
	
	public UsuarioAtividadeVo(){
	}
	public UsuarioAtividadeVo(UsuarioAtividadeVo aux){
		id = aux.getId();
	  	usuario =aux.getUsuario();
		atividadeComplementos=aux.getAtividadeComplemento();
		complementos=aux.getComplementos();
		atividade=aux.getAtividade();
		frequencia=aux.getFrequencia();
		satisfacao=aux.getSatisfacao();
		perfil=aux.getPerfil();
		popularidade=aux.getPopularidade();
		relacao=aux.getRelacao();
		resposta=aux.getResposta();
	}
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
	public List<Complemento> getComplementos() {
		return complementos;
	}
	public void setComplementos(List<Complemento> complementos) {
		this.complementos = complementos;
	}
	public Atividade getAtividade() {
		return atividade;
	}
	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}
	public List<AtividadeComplemento> getAtividadeComplemento() {
		return atividadeComplementos;
	}
	public void setAtividadeComplemento(List<AtividadeComplemento> atividadeComplementos) {
		this.atividadeComplementos = atividadeComplementos;
	}
	public Double getPopularidade() {
		return popularidade;
	}
	public void setPopularidade(Double popularidade) {
		this.popularidade = popularidade;
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
