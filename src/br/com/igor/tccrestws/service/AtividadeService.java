package br.com.igor.tccrestws.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import br.com.igor.tccrestws.dao.AtividadeDao;
import br.com.igor.tccrestws.filtro.FiltroAtividade;
import br.com.igor.tccrestws.filtro.FiltroUsuario;

@Path("/AtividadeService")
public class AtividadeService {

   AtividadeDao dao = new AtividadeDao();

    @GET
	@Path("/atividade")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
   public String selectAtividade(String filtro){
	   Gson gson = new Gson();
	   FiltroAtividade filtroAtividade = gson.fromJson(filtro, FiltroAtividade.class);
	   String userJSONString = gson.toJson(dao.selectAtividade(filtroAtividade));
	   return userJSONString;
   }
    
    @GET
	@Path("/allAtividade")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
   public String selectAllAtividade(String filtro){
	   Gson gson = new Gson();
	   FiltroAtividade filtroAtividade = gson.fromJson(filtro, FiltroAtividade.class);
	   String userJSONString = gson.toJson(dao.selectAtividade(filtroAtividade));
	   return userJSONString;
   }
}