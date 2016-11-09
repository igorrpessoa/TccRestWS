package br.com.igor.tccrestws.entity;

public class Usuario {
	public static final String ID = "id";
	public static final String NOME = "nome";
	public static final String EMAIL = "email";
	public static final String PERFIL = "perfil_id";
	public static final String SENHA = "senha";

	public Usuario(int id,String nome,String email,String senha,Perfil perfil){
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.perfil = perfil;
	}
	
	private Integer id;
	private String nome;
	private String email;
	private String senha;
	private Perfil perfil;
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
	

}
