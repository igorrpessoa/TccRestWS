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

import br.com.igor.tccrestws.dao.AtividadeDao;
import br.com.igor.tccrestws.dao.ComplementoDao;
import br.com.igor.tccrestws.entity.Atividade;
import br.com.igor.tccrestws.entity.Complemento;
import br.com.igor.tccrestws.entity.Usuario;
import br.com.igor.tccrestws.vo.AtividadeVo;

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
	   AtividadeVo vo = new AtividadeVo();
	   vo.setAtividade(filtroAtividade);
	   String userJSONString = gson.toJson(dao.selectAtividade(vo));
	   return userJSONString;
   }
   	@POST
	@Path("/selectAtividades")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
   public String selectAtividades(String filtro){
	   Gson gson = new Gson();
	   Atividade filtroAtividade = gson.fromJson(filtro, Atividade.class);
	   AtividadeVo vo = new AtividadeVo();
	   vo.setAtividade(filtroAtividade);
	   String userJSONString = gson.toJson(dao.selectAtividades(vo));
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
	@Path("/buscaAtividade")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
   public String buscaAtividade(String json){
	   Gson gson = new Gson();
	   AtividadeVo filtro = gson.fromJson(json, AtividadeVo.class);
	   AtividadeVo atividadeVo = filtro;
	   //Retira pontuação, espaços e letras maiusculas
	   atividadeVo = filtraAtividadeComplemento(filtro);
	   //Tento encontrar atividade igual e valido a sua existência
	   Atividade atividade = dao.selectAtividade(filtro);
	   if(atividade != null){
		   atividadeVo.setAtividade(atividade);
	   }else{
		   atividadeVo.setAtividade(filtro.getAtividade());
	   }
	   //Tento encontrar o complemento igual e valido a sua existência
	   ComplementoDao complementoDao = new ComplementoDao();
	   if(atividadeVo.getComplementos() != null && atividadeVo.getComplementos().size()> 0){
		   List<Complemento> complementos = new ArrayList<>();
		   for(int i=0; i< atividadeVo.getComplementos().size(); i++){
			   Complemento c = atividadeVo.getComplementos().get(i);
			   Complemento complemento = complementoDao.selectComplemento(c);
			   if(complemento != null){
				   complementos.add(complemento);
			   }else{
				   complementos.add(c);
			   }
		   }
		   atividadeVo.setComplementos(complementos);

	   }
	   String userJSONString = gson.toJson(atividadeVo);
	   return userJSONString;
   }
   	
   	private AtividadeVo filtraAtividadeComplemento(AtividadeVo vo){
    	vo.getAtividade().setNome(vo.getAtividade().getNome().replaceAll("[^a-zA-Z0-9]+","").toLowerCase());
    	List<Complemento> lista = vo.getComplementos();
    	for(int i=0;i<lista.size();i++){ 
    		vo.getComplementos().get(i).setNome(lista.get(i).getNome().replaceAll("[^a-zA-Z0-9]+","").toLowerCase()); 
    	}
    	return vo;
    }
   
  
   
    @POST
	@Path("/sugestaoAtividade")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
   public String sugestaoAtividade(String filtro){
	   Gson gson = new Gson();
	   Usuario filtroUsuario = gson.fromJson(filtro, Usuario.class);
	   List<AtividadeVo> atividades = dao.selectAllAtividadeSugestao(filtroUsuario);
	   String userJSONString = gson.toJson(selecionaAtividadeRandom(atividades));
	   return userJSONString;
   }
    
    
    private AtividadeVo selecionaAtividadeRandom(List<AtividadeVo> atividades ){
    	if(atividades !=null && atividades.size()>0){
	    		int index = (int)(10*Math.random())%atividades.size();
	    		return atividades.get(index);
	    	}
	    	else{
	    		return null;
	    	}
	   }
   
    
    
    
    private AtividadeVo selecionaAtividadePorAfinidade(List<AtividadeVo> atividades){
    	if(atividades !=null){
    	int index = (int)(10*Math.random())%atividades.size();
    	return atividades.get(index);
    	}
    	else{
    		return null;
    	}
    }
}