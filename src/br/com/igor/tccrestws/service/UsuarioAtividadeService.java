package br.com.igor.tccrestws.service;

import java.util.ArrayList;
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

import br.com.igor.tccrestws.Fuzzy;
import br.com.igor.tccrestws.dao.AtividadeComplementoDao;
import br.com.igor.tccrestws.dao.AtividadeDao;
import br.com.igor.tccrestws.dao.ComplementoDao;
import br.com.igor.tccrestws.dao.PerfilDao;
import br.com.igor.tccrestws.dao.UsuarioAtividadeDao;
import br.com.igor.tccrestws.dao.UsuarioDao;
import br.com.igor.tccrestws.entity.Atividade;
import br.com.igor.tccrestws.entity.AtividadeComplemento;
import br.com.igor.tccrestws.entity.Complemento;
import br.com.igor.tccrestws.entity.Perfil;
import br.com.igor.tccrestws.entity.Usuario;
import br.com.igor.tccrestws.entity.UsuarioAtividade;
import br.com.igor.tccrestws.vo.AtividadeVo;
import br.com.igor.tccrestws.vo.UsuarioAtividadeVo;

@Path("/UsuarioAtividadeService")
public class UsuarioAtividadeService {

   UsuarioAtividadeDao dao = new UsuarioAtividadeDao();

    @POST
	@Path("/selectAllUsuarioAtividade")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
   public String selectAllUsuarioAtividade(String filtro){
	   Gson gson = new Gson();
	   Usuario usuario = gson.fromJson(filtro, Usuario.class);
	   AtividadeComplementoDao atividadeComplementoDao = new AtividadeComplementoDao();
	   List<UsuarioAtividadeVo> atividades = dao.selectAllUsuarioAtividade(usuario);
	   String userJSONString = gson.toJson(atividades);
	   return userJSONString;
   }	
    
    @POST
	@Path("/selectUsuarioAtividade")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
   public String selectUsuarioAtividade(String filtro){
	   Gson gson = new Gson();
	   UsuarioAtividadeVo ua = gson.fromJson(filtro, UsuarioAtividadeVo.class);
	   List<UsuarioAtividadeVo> atividades = dao.selectAllUsuarioAtividade(ua);
	   String userJSONString = gson.toJson(atividades);
	   return userJSONString;
   }	
    
    @POST
   	@Path("/editarUsuarioAtividade")
   	@Produces(MediaType.APPLICATION_JSON)
   	@Consumes(MediaType.APPLICATION_JSON)
      public String editarUsuarioAtividade(String filtro){
   	   Gson gson = new Gson();
   	   UsuarioDao usuarioDao = new UsuarioDao();
   	   PerfilDao perfilDao = new PerfilDao();
   	   AtividadeDao atividadeDao = new AtividadeDao();
   	   AtividadeComplementoDao atividadeComplementoDao = new AtividadeComplementoDao();
   	   ComplementoDao complementoDao = new ComplementoDao();
   	   List<AtividadeComplemento> listaAtividadeComplemento = new ArrayList<>();
   	   List<AtividadeComplemento> novaLista = new ArrayList<>();
   	   UsuarioAtividadeVo ua = gson.fromJson(filtro, UsuarioAtividadeVo.class);
   	   //Faz o delete dos usuario atividade do perfil antigo
   	   dao.deleteUsuarioAtividade(ua);
   	   //Salva o nome da atividade caso ela não exista
   	   AtividadeVo vo = new AtividadeVo();
   	   vo.setAtividade(ua.getAtividade());
   	   vo.setAtividade(atividadeDao.selectAtividade(vo));
   	   if(vo.getAtividade() == null){
   		   ua.setAtividade(atividadeDao.salvarAtividade(ua.getAtividade()));
   	   }else{
   	   	   ua.setAtividade(vo.getAtividade());
   	   }	   	  
   	   //Salva atividade complemento
	   if(ua.getComplementos() != null && ua.getComplementos().size()>0){
	   	   for(Complemento c : ua.getComplementos()){
	   	   	   Complemento complemento = complementoDao.selectComplemento(c);
	   		   if(complemento == null){
	   			 //Salva o nome do complemento se não existe
	   			complemento = complementoDao.salvarComplemento(c);
	   	   	   }
	   		   AtividadeComplemento atividadeComplemento= new AtividadeComplemento();
	   		   atividadeComplemento.setAtividade(ua.getAtividade());
	   		   atividadeComplemento.setComplemento(complemento);
	   		   listaAtividadeComplemento.add(atividadeComplemento);
	   	   }
	   }else{
		   AtividadeComplemento atividadeComplemento= new AtividadeComplemento();
   		   atividadeComplemento.setAtividade(ua.getAtividade());
   		   listaAtividadeComplemento.add(atividadeComplemento);
	   }
	   //Salva todos os atividade complemento, verificando antes se já existe um par cadastrado
   	   for(AtividadeComplemento ac : listaAtividadeComplemento){
   		AtividadeComplemento atividadeComplemento = atividadeComplementoDao.selectParAtividadeComplemento(ac);
   		if(!(atividadeComplemento != null && atividadeComplemento.getId() != null && atividadeComplemento.getId()>0)){
   			AtividadeComplemento atividadeComplementoAux = atividadeComplementoDao.salvarAtividadeComplemento(ac);
   		   	novaLista.add(atividadeComplementoAux);
   		}else{
   			novaLista.add(atividadeComplemento);
   		}
   	   }
   	   ua.setAtividadeComplemento(novaLista);
   	   //Salva o perfil do usuario atividade
   	   //ua.setPerfil(perfilDao.salvarPerfil(ua.getPerfil()));
   	   //Faz o select de todos os usuario atividade do usuario, itera sobre a relação e atualiza o seu perfil
   	   List<UsuarioAtividadeVo> list = dao.selectAllRelacao(ua);
   	   Integer aux = 0;
   	   UsuarioAtividadeVo novoUsuarioAtividade = null;
   	   //Fuzzyfica
   	   for(UsuarioAtividadeVo i :list){
   		Perfil novoPerfil = null;
		   if(aux==0){
			   aux++;
			   novoUsuarioAtividade = new UsuarioAtividadeVo(i);
			   novoUsuarioAtividade.setUsuario(ua.getUsuario());
			   novoPerfil = i.getPerfil();
			   Double relacao = i.getRelacao();
			   novoPerfil.setArtistico((relacao+novoPerfil.getArtistico())/2);
			   novoPerfil.setIntelecto((relacao+novoPerfil.getIntelecto())/2);
			   novoPerfil.setSocial((relacao+novoPerfil.getSocial())/2);
			   novoPerfil.setSaude((relacao+novoPerfil.getSaude())/2);
		   }else{
			   novoPerfil = novoUsuarioAtividade.getUsuario().getPerfil();
		   
   		   Perfil atividadePerfil = i.getPerfil();
		   Double relacao = i.getRelacao();
		   	novoPerfil.setArtistico(novoPerfil.getArtistico() + (novoPerfil.getArtistico() - atividadePerfil.getArtistico())*(-relacao/100));
			novoPerfil.setIntelecto(novoPerfil.getIntelecto() + (novoPerfil.getIntelecto() - atividadePerfil.getIntelecto())*(-relacao/100));
			novoPerfil.setSocial( novoPerfil.getSocial() + (novoPerfil.getSocial() - atividadePerfil.getSocial())*(-relacao/100));
			novoPerfil.setSaude(novoPerfil.getSaude() + (novoPerfil.getSaude() - atividadePerfil.getSaude())*(-relacao/100));
		   }
		   novoUsuarioAtividade.getUsuario().setPerfil(novoPerfil);
   	   }
   	   if(novoUsuarioAtividade == null){
   		   novoUsuarioAtividade = new UsuarioAtividadeVo();
   	   }
   	   novoUsuarioAtividade.setResposta(ua.getResposta());
   	   novoUsuarioAtividade.setSatisfacao(ua.getSatisfacao());
	   novoUsuarioAtividade.setFrequencia(ua.getFrequencia());
   	   novoUsuarioAtividade.setAtividadeComplemento(ua.getAtividadeComplemento());
   	   novoUsuarioAtividade = Fuzzy.executaFuzzy(ua);
   	   
   	   //Salva o perfil do usuário novo
   	   Usuario usuario = ua.getUsuario();
   	   usuario.setPerfil(novoUsuarioAtividade.getUsuario().getPerfil());
	   if(usuario.getPerfil() != null && usuario.getPerfil().getId() != null && usuario.getPerfil().getId()==0){
			usuario.setPerfil(perfilDao.salvarPerfil(usuario.getPerfil()));
	   }else{
		 //Salva perfil do usuário
		   usuario.setPerfil(perfilDao.updatePerfil(usuario.getPerfil()));
	   }
	   //Salva perfil do usuário
		usuarioDao.updateUsuario(usuario);
   	   //Salva a atividade ao usuario
	   	dao.saveMultiplosUsuarioAtividade(novoUsuarioAtividade);
	   	novoUsuarioAtividade.setUsuario(usuario);
   	   String userJSONString = gson.toJson(novoUsuarioAtividade);
   	   return userJSONString;
      }	
    
    @POST
   	@Path("/fuzzyficar")
   	@Produces(MediaType.APPLICATION_JSON)
   	@Consumes(MediaType.APPLICATION_JSON)
      public String fuzzyficar(String aux){
   	   Gson gson = new Gson();
   	   ComplementoDao complementoDao = new ComplementoDao();
   	   AtividadeComplementoDao atividadeComplementoDao = new AtividadeComplementoDao();
   	   UsuarioDao usuarioDao = new UsuarioDao();
   	   AtividadeDao atividadeDao = new AtividadeDao();
   	   PerfilDao perfilDao = new PerfilDao();
   	   List<AtividadeComplemento> listaAtividadeComplemento = new ArrayList<>();
   	   List<AtividadeComplemento> novaLista = new ArrayList<>();
   	   UsuarioAtividadeVo ua = gson.fromJson(aux,UsuarioAtividadeVo.class);
   	   //Salva o nome da atividade caso ela não exista
   	   AtividadeVo vo = new AtividadeVo();
   	   vo.setAtividade(ua.getAtividade());
   	   vo.setAtividade(atividadeDao.selectAtividade(vo));
   	   if(vo.getAtividade() == null){
   		   ua.setAtividade(atividadeDao.salvarAtividade(ua.getAtividade()));
   	   }else{
   	   	   ua.setAtividade(vo.getAtividade());
   	   }	   	  
   	   //Salva atividade complemento
	
	   if(ua.getComplementos() != null && ua.getComplementos().size()>0){
	   	   for(Complemento c : ua.getComplementos()){
	   	   	   Complemento complemento = complementoDao.selectComplemento(c);
	   		   if(complemento == null){
	   			 //Salva o nome do complemento se não existe
	   			complemento = complementoDao.salvarComplemento(c);
	   	   	   }
	   		   AtividadeComplemento atividadeComplemento= new AtividadeComplemento();
	   		   atividadeComplemento.setAtividade(ua.getAtividade());
	   		   atividadeComplemento.setComplemento(complemento);
	   		   listaAtividadeComplemento.add(atividadeComplemento);
	   	   }
	   }else{
		   AtividadeComplemento atividadeComplemento= new AtividadeComplemento();
   		   atividadeComplemento.setAtividade(ua.getAtividade());
   		   listaAtividadeComplemento.add(atividadeComplemento);
	   }
   	   for(AtividadeComplemento ac : listaAtividadeComplemento){
   		AtividadeComplemento atividadeComplementoAux = atividadeComplementoDao.salvarAtividadeComplemento(ac);
	   	novaLista.add(atividadeComplementoAux);
   	   }
   	  
   	   ua.setAtividadeComplemento(novaLista);
   	   //Salva o perfil do usuario atividade
   	   ua.setPerfil(perfilDao.salvarPerfil(ua.getPerfil()));
   	   //Fuzzyfica
   	   UsuarioAtividadeVo novoUsuarioAtividade = Fuzzy.executaFuzzy(ua);
   	   //Salva o perfil do usuário novo
   	   Usuario usuario = ua.getUsuario();
   	   usuario.setPerfil(novoUsuarioAtividade.getUsuario().getPerfil());
	   if(usuario.getPerfil() != null && usuario.getPerfil().getId() != null && usuario.getPerfil().getId()==0){
			usuario.setPerfil(perfilDao.salvarPerfil(usuario.getPerfil()));
	   }else{
	   	   //Salva perfil do usuário
		   usuario.setPerfil(perfilDao.updatePerfil(usuario.getPerfil()));
	   }
	   //Salva perfil do usuário
	 		usuarioDao.updateUsuario(usuario);
   	   //Salva a atividade ao usuario
   	   dao.saveMultiplosUsuarioAtividade(novoUsuarioAtividade);	   
   	   String userJSONString = gson.toJson(usuario);
//   	   if(usuario.getPerfil() != null && usuario.getPerfil().getId() != null && usuario.getPerfil().getId()>0){
//   		    perfilDao.updatePerfil(usuario.getPerfil());
//		}else{

//   	   	}
   	   return userJSONString;
    }
    
    @POST
  	@Path("/deleteUsuarioAtividade")
  	@Produces(MediaType.APPLICATION_JSON)
  	@Consumes(MediaType.APPLICATION_JSON)
    public String deleteUsuarioAtividade(String str){
    	Gson gson = new Gson();
    	UsuarioAtividadeVo ua = gson.fromJson(str, UsuarioAtividadeVo.class);
    	dao.deleteUsuarioAtividade(ua);
    	UsuarioDao usuarioDao = new UsuarioDao();
     	PerfilDao perfilDao = new PerfilDao();
     	//Faz o select de todos os usuario atividade do usuario, itera sobre a relação e atualiza o seu perfil
	    List<UsuarioAtividadeVo> list = dao.selectAllRelacao(ua);
	    Integer aux = 0;
	    UsuarioAtividadeVo novoUsuarioAtividade = null;
	    //Fuzzyfica
	    for(UsuarioAtividadeVo i :list){
		Perfil novoPerfil = null;
 		   if(aux==0){
 			   aux++;
 			   novoUsuarioAtividade = new UsuarioAtividadeVo(i);
 			   novoUsuarioAtividade.setUsuario(ua.getUsuario());
 			   novoPerfil = i.getPerfil();
 			   Double relacao = i.getRelacao();
 			   novoPerfil.setArtistico((relacao+novoPerfil.getArtistico())/2);
 			   novoPerfil.setIntelecto((relacao+novoPerfil.getIntelecto())/2);
 			   novoPerfil.setSocial((relacao+novoPerfil.getSocial())/2);
 			   novoPerfil.setSaude((relacao+novoPerfil.getSaude())/2);
 		   }else{
 			   novoPerfil = novoUsuarioAtividade.getUsuario().getPerfil();
 		   
    		   Perfil atividadePerfil = i.getPerfil();
	 		   Double relacao = i.getRelacao();
	 		   novoPerfil.setArtistico(novoPerfil.getArtistico() + (novoPerfil.getArtistico() - atividadePerfil.getArtistico())*(-relacao/100));
	 		   novoPerfil.setIntelecto(novoPerfil.getIntelecto() + (novoPerfil.getIntelecto() - atividadePerfil.getIntelecto())*(-relacao/100));
	 		   novoPerfil.setSocial( novoPerfil.getSocial() + (novoPerfil.getSocial() - atividadePerfil.getSocial())*(-relacao/100));
	 		   novoPerfil.setSaude(novoPerfil.getSaude() + (novoPerfil.getSaude() - atividadePerfil.getSaude())*(-relacao/100));
 		   }
 		   novoUsuarioAtividade.getUsuario().setPerfil(novoPerfil);
    	}
	   if(novoUsuarioAtividade == null){
		   novoUsuarioAtividade = new UsuarioAtividadeVo();
	   }
	  

	   novoUsuarioAtividade.setResposta(ua.getResposta());
	   novoUsuarioAtividade.setSatisfacao(ua.getSatisfacao());
       novoUsuarioAtividade.setFrequencia(ua.getFrequencia());
	   novoUsuarioAtividade.setAtividadeComplemento(ua.getAtividadeComplemento());
	   
	   //Salva o perfil do usuário novo
	   Usuario usuario = ua.getUsuario();
	   if(novoUsuarioAtividade.getUsuario() == null){
		   novoUsuarioAtividade.setUsuario(usuario);
		   usuario.getPerfil().setIntelecto(0.0);
		   usuario.getPerfil().setArtistico(0.0);
		   usuario.getPerfil().setSaude(0.0);
		   usuario.getPerfil().setSocial(0.0);
	   }else{
		   usuario.setPerfil(novoUsuarioAtividade.getUsuario().getPerfil());
	   }
 	   if(usuario.getPerfil() != null && usuario.getPerfil().getId() != null && usuario.getPerfil().getId()==0){
 	   }else{
 		 //Salva perfil do usuário
 		   usuario.setPerfil(perfilDao.updatePerfil(usuario.getPerfil()));
 	   }
 	   //Salva perfil do usuário
 		usuarioDao.updateUsuario(usuario);
    	String userJSONString = gson.toJson(usuario);
    	return userJSONString;
    }
    
 
}