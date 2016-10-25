package br.com.igor.tccrestws.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import br.com.igor.tccrestws.dao.UsuarioDao;
import br.com.igor.tccrestws.filtro.FiltroUsuario;

@Path("/UsuarioService")
public class UsuarioService {

   UsuarioDao dao = new UsuarioDao();

    @GET
	@Path("/usuario")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
   public String selectUsuario(String filtro){
	   Gson gson = new Gson();
	   FiltroUsuario filtroUsuario = gson.fromJson(filtro, FiltroUsuario.class);
	   String userJSONString = gson.toJson(dao.selectUsuario(filtroUsuario));
	
	   return userJSONString;
   }	
}