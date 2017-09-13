package br.com.igor.tccrestws.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import br.com.igor.tccrestws.dao.AtividadeDao;
import br.com.igor.tccrestws.entity.Atividade;

@Path("/AtividadeService")
public class AtividadeService {

   AtividadeDao dao = new AtividadeDao();

   	@POST
	@Path("/selectAtividade")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
   public String selectAtividade(String filtro){
	   Gson gson = new Gson();
	   Atividade filtroAtividade = gson.fromJson(filtro, Atividade.class);
	   String userJSONString = gson.toJson(dao.selectAtividade(filtroAtividade));
	   return userJSONString;
   }
   	
   	@POST
	@Path("/salvarAtividade")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
   public String salvarAtividade(String filtro){
	   Gson gson = new Gson();
	   Atividade filtroAtividade = gson.fromJson(filtro, Atividade.class);
	   String userJSONString = gson.toJson(dao.salvarAtividade(filtroAtividade));
	   return userJSONString;
   }
    @POST
	@Path("/selectAllAtividade")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
   public String selectAllAtividade(String filtro){
	   Gson gson = new Gson();
	   Atividade filtroAtividade = gson.fromJson(filtro, Atividade.class);
	   String userJSONString = gson.toJson(dao.selectAllAtividade(filtroAtividade));
	   return userJSONString;
   }
}