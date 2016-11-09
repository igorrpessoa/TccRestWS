package br.com.igor.tccrestws.entity;

public class Perfil {

	private Integer id;
	private Double naturezaSaude;
	private Double humanaSocial;
	private Double educacaoConhecimento;
	private Double culturaCriatividade;
	
	public Double getNaturezaSaude() {
		return naturezaSaude;
	}
	public void setNaturezaSaude(Double naturezaSaude) {
		this.naturezaSaude = naturezaSaude;
	}
	public Double getHumanaSocial() {
		return humanaSocial;
	}
	public void setHumanaSocial(Double humanaSocial) {
		this.humanaSocial = humanaSocial;
	}
	public Double getEducacaoConhecimento() {
		return educacaoConhecimento;
	}
	public void setEducacaoConhecimento(Double educacaoConhecimento) {
		this.educacaoConhecimento = educacaoConhecimento;
	}
	public Double getCulturaCriatividade() {
		return culturaCriatividade;
	}
	public void setCulturaCriatividade(Double culturaCriatividade) {
		this.culturaCriatividade = culturaCriatividade;
	}
	
	
}
