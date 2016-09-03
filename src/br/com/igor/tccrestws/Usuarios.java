package br.com.igor.tccrestws;

import java.util.List;

public class Usuarios {
	private List<User> listaUser;
	public List<User> getLista(){
		return listaUser;
	}
	public void setLista(List<User> lista){
		listaUser = lista;
	}
}
