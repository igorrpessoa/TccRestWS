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
import br.com.igor.tccrestws.vo.FuzzyficationVo;

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
	   List<Atividade> atividades = dao.selectAllUsuarioAtividade(usuario);
	   List<AtividadeVo> auxiliaryList = new ArrayList<>();
	   for(Atividade a : atividades){
		   AtividadeVo auxiliary = new AtividadeVo();
		   List<Complemento> complementos = new ArrayList<>();
		   auxiliary.setAtividade(a);
		   for(AtividadeComplemento ac : atividadeComplementoDao.selectAllAtividadeComplementoFromAtividade(a)){
			   complementos.add(ac.getComplemento());
		   }
		   auxiliary.setComplementos(complementos);
		   auxiliaryList.add(auxiliary);
	   }
	   String userJSONString = gson.toJson(auxiliaryList);
	   return userJSONString;
   }	
    
    @POST
	@Path("/saveUsuarioAtividade")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
   public String saveUsuario(String usuarioAtividade){
	   Gson gson = new Gson();
	   UsuarioAtividade ua = gson.fromJson(usuarioAtividade,UsuarioAtividade.class);
	   String userJSONString = gson.toJson(dao.saveUsuarioAtividade(ua));
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

   	   FuzzyficationVo af = gson.fromJson(aux,FuzzyficationVo.class);
   	   UsuarioAtividade ua = af.getUsuarioAtividade();
   	   //Salva o nome da atividade caso ela não exista
   	   AtividadeVo vo = new AtividadeVo();
   	   vo.setAtividade(ua.getAtividade());
   	   vo.setAtividade(atividadeDao.selectAtividade(vo));
   	   if(vo.getAtividade() == null){
   		   ua.setAtividade(atividadeDao.salvarAtividade(ua.getAtividade()));
   	   }else{
   	   	   ua.setAtividade(vo.getAtividade());
   	   }
   	   if(af.getComplementos() != null && !af.getComplementos().isEmpty()){
	   	  
	   	   //Salva atividade complemento
	   	   List<AtividadeComplemento> list = new ArrayList<>();
	   	   for(Complemento c : af.getComplementos()){
	   	   	   Complemento complemento = complementoDao.selectComplemento(c);
	   		   if(complemento == null){
	   			 //Salva o nome do complemento se não existe
	   			complemento = complementoDao.salvarComplemento(c);
	   	   	   }
	   		   AtividadeComplemento atividadeComplemento= new AtividadeComplemento();
	   		   atividadeComplemento.setAtividade(ua.getAtividade());
	   		   atividadeComplemento.setComplemento(complemento);
	   		   list.add(atividadeComplemento);
	   	   }
	   	   atividadeComplementoDao.salvarAtividadeComplemento(list);
   	   }
   	   //Salva o perfil do usuario atividade
   	   ua.setPerfil(perfilDao.salvarPerfil(ua.getPerfil()));
   	   //Salva a atividade ao usuario
   	   dao.saveUsuarioAtividade(ua);
   	   //Fuzzyfica
   	   Perfil perfil = Fuzzy.executaFuzzy(ua);
   	   //Salva o perfil do usuário novo
   	   Usuario usuario = ua.getUsuario();
   	   usuario.setPerfil(perfil);
   	   String userJSONString = gson.toJson(usuario);
   	   if(usuario.getPerfil() != null && usuario.getPerfil().getId() != null && usuario.getPerfil().getId()>0){
   		    perfilDao.updatePerfil(usuario.getPerfil());
		}else{
			usuario.setPerfil(perfilDao.salvarPerfil(usuario.getPerfil()));
			usuarioDao.updateUsuario(usuario);
   	   	}
   	   return userJSONString;
    }
 
}