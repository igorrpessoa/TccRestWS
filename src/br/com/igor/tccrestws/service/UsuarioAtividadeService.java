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

import br.com.igor.tccrestws.AuxiliaryFuzzyfication;
import br.com.igor.tccrestws.Fuzzy;
import br.com.igor.tccrestws.dao.AtividadeComplementoDao;
import br.com.igor.tccrestws.dao.AtividadeDao;
import br.com.igor.tccrestws.dao.ComplementoDao;
import br.com.igor.tccrestws.dao.UsuarioAtividadeDao;
import br.com.igor.tccrestws.dao.UsuarioDao;
import br.com.igor.tccrestws.entity.AtividadeComplemento;
import br.com.igor.tccrestws.entity.Complemento;
import br.com.igor.tccrestws.entity.Perfil;
import br.com.igor.tccrestws.entity.Usuario;
import br.com.igor.tccrestws.entity.UsuarioAtividade;

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
	   String userJSONString = gson.toJson(dao.selectAllUsuarioAtividade(usuario));
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
   	   AuxiliaryFuzzyfication af = gson.fromJson(aux,AuxiliaryFuzzyfication.class);
   	   UsuarioAtividade ua = af.getUsuarioAtividade();
   	   //Salva o nome da atividade
   	   AtividadeDao atividadeDao = new AtividadeDao();
   	   ua.setAtividade(atividadeDao.salvarAtividade(ua.getAtividade()));
   	   //Salva o nome do complemento
   	   ComplementoDao complementoDao = new ComplementoDao();
   	   af.setComplementos(complementoDao.salvarComplementos(af.getComplementos()));
   	   //Salva atividade complemento
   	   List<AtividadeComplemento> list = new ArrayList<>();
   	   for(Complemento c : af.getComplementos()){
   		   AtividadeComplemento atividadeComplemento= new AtividadeComplemento();
   		   atividadeComplemento.setAtividade(ua.getAtividade());
   		   atividadeComplemento.setComplemento(c);
   		   list.add(atividadeComplemento);
   	   }
   	   AtividadeComplementoDao atividadeComplementoDao = new AtividadeComplementoDao();
   	   atividadeComplementoDao.salvarAtividadeComplemento(list);
   	   //Salva a atividade ao usuario
   	   dao.saveUsuarioAtividade(ua);
   	   //Fuzzyfica
   	   Perfil perfil = Fuzzy.executaFuzzy(ua);
   	   //Salva o perfil do usuário novo
   	   Usuario usuario = ua.getUsuario();
   	   usuario.setPerfil(perfil);
   	   UsuarioDao usuarioDao = new UsuarioDao();
   	   String userJSONString = gson.toJson(usuarioDao.saveUsuario(usuario));
   	   return userJSONString;
    }
 
}