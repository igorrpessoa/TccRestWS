package br.com.igor.tccrestws.vo;

import java.util.List;

import br.com.igor.tccrestws.entity.Atividade;
import br.com.igor.tccrestws.entity.Complemento;

public class AtividadeVo {

	public AtividadeVo(){
	}
	public AtividadeVo(Atividade a, List<Complemento> c){
		atividade =a;
		complementos = c;
	}
	
	private Atividade atividade;
	private List<Complemento> complementos;
	private Integer valido;
	
	public Atividade getAtividade() {
		return atividade;
	}
	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}
	public List<Complemento> getComplementos() {
		return complementos;
	}
	public void setComplementos(List<Complemento> complementos) {
		this.complementos = complementos;
	}
	public Integer getValido() {
		return valido;
	}
	public void setValido(Integer valido) {
		this.valido = valido;
	}
	
}
