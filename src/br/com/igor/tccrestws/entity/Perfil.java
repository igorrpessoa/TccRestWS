package br.com.igor.tccrestws.entity;

public class Perfil {

	public static final String ID = "id";
	public static final String SAUDE = "saude";
	public static final String SOCIAL = "social";
	public static final String INTELECTO = "intelecto";
	public static final String ARTISTICO = "artistico";
	
	private Integer id;
	private Double saude;
	private Double social;
	private Double intelecto;
	private Double artistico;
	
	public Perfil(){};
	public Perfil(Integer id){
		this.id = id;
	};

	public Perfil(Integer id, Double saude, Double social, Double intelecto,
			Double artistico) {
		super();
		this.id = id;
		this.saude = saude;
		this.social = social;
		this.intelecto = intelecto;
		this.artistico = artistico;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Double getSaude() {
		return saude;
	}

	public void setSaude(Double saude) {
		this.saude = saude;
	}

	public Double getSocial() {
		return social;
	}

	public void setSocial(Double social) {
		this.social = social;
	}

	public Double getIntelecto() {
		return intelecto;
	}

	public void setIntelecto(Double intelecto) {
		this.intelecto = intelecto;
	}

	public Double getArtistico() {
		return artistico;
	}

	public void setArtistico(Double artistico) {
		this.artistico = artistico;
	}
	
}
