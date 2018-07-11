package br.com.igor.tccrestws.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import br.com.igor.tccrestws.dao.ComplementoDao;
import br.com.igor.tccrestws.entity.Complemento;

@Path("/ComplementoService")
public class ComplementoService {

	ComplementoDao dao = new ComplementoDao();

   	@POST
	@Path("/selectAllComplemento")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
   public String selectAllComplemento(String filtro){
	   Gson gson = new Gson();
	   Complemento filtroAtividade = gson.fromJson(filtro, Complemento.class);
	   String userJSONString = gson.toJson(dao.selectAllComplemento(filtroAtividade));
	   return userJSONString;
   }
   	
}