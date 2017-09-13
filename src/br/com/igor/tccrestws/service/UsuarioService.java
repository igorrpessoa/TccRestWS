package br.com.igor.tccrestws.service;

import java.util.List;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import br.com.igor.tccrestws.Util;
import br.com.igor.tccrestws.dao.UsuarioDao;
import br.com.igor.tccrestws.entity.Usuario;

@Path("/UsuarioService")
public class UsuarioService {

   UsuarioDao dao = new UsuarioDao();

    @POST
	@Path("/selectUsuario")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
   public String selectUsuario(String filtro){
	   Gson gson = new Gson();
	   Usuario usuario = gson.fromJson(filtro, Usuario.class);
	   String userJSONString = gson.toJson(dao.selectUsuario(usuario));
	
	   return userJSONString;
   }	
    
    @POST
	@Path("/saveUsuario")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
   public String saveUsuario(String usuario){
	   Gson gson = new Gson();
	   Usuario u = gson.fromJson(usuario,Usuario.class);
	   try{
		   u.setSenha(Util.sha256(u.getSenha()));
	   }
	   catch (Exception e) {
		// TODO: handle exception
	   }
	   String userJSONString = gson.toJson(dao.saveUsuario(u));
	   
	   return userJSONString;
   }
    
    @POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
   public String login(String filtro){
	   Gson gson = new Gson();
	   String retorno ="";
	   Usuario usuario = gson.fromJson(filtro, Usuario.class);
	   Usuario usuarioEncontrado = dao.selectUsuario(usuario);
	   try{
		   usuario.setSenha(Util.sha256(usuario.getSenha()));
		   if(usuarioEncontrado != null && usuario.getSenha().equals(usuarioEncontrado.getSenha())){
			   retorno = gson.toJson(usuarioEncontrado);
		   }else{
			   retorno = gson.toJson(new Usuario());
		   }
	   }
	   catch (Exception e) {
		e.printStackTrace();
	   }	   
	   return retorno;
   }
}