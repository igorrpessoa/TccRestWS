package br.com.igor.tccrestws.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.PreparedStatement;

import br.com.igor.tccrestws.ConexaoMySQL;
import br.com.igor.tccrestws.entity.Atividade;
import br.com.igor.tccrestws.entity.AtividadeComplemento;
import br.com.igor.tccrestws.entity.Complemento;
import br.com.igor.tccrestws.entity.Perfil;
import br.com.igor.tccrestws.entity.Usuario;
import br.com.igor.tccrestws.entity.UsuarioAtividade;
import br.com.igor.tccrestws.vo.UsuarioAtividadeVo;

public class UsuarioAtividadeDao {

	private ConexaoMySQL conMySQL = new ConexaoMySQL();

	
	public List<UsuarioAtividadeVo> selectAllUsuarioAtividade(Usuario filtro){
		Map<Integer,UsuarioAtividadeVo> mapper = new HashMap<>();
		List<UsuarioAtividadeVo> retorno = new ArrayList<>();
		int idPerfilAtual = -1;
		boolean mesmaAtividade = true;
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	          sql = "SELECT " 
		        	+  "UA." +UsuarioAtividade.ID + ","
	        		+  "UA." +UsuarioAtividade.FREQUENCIA + ","
	        		+  "UA." +UsuarioAtividade.SATISFACAO + ","
	        		+  "UA." +UsuarioAtividade.ATIVIDADE_COMPLEMENTO + ","
	        		+  "UA." +UsuarioAtividade.PERFIL + ","
	        		+  "UA." +UsuarioAtividade.RELACAO + ","
	        		+  "UA." +UsuarioAtividade.RESPOSTA + ","
	        		+  "P." +Perfil.ARTISTICO + ","
	        		+  "P." +Perfil.INTELECTO + ","
	        		+  "P." +Perfil.SOCIAL + ","	        		
	        		+  "P." +Perfil.SAUDE + ","
	        		+  "AC." +AtividadeComplemento.ATIVIDADE + ","
	        		+  "A." +Atividade.ID + ","
	        		+  "A." +Atividade.NOME + ","
	        		+  "C." +Complemento.ID + ","
	        		+  "C." +Complemento.NOME
	        		+ " FROM USUARIO_ATIVIDADE UA INNER JOIN USUARIO U ON UA.USUARIO_ID=U.ID"
	        		+ " INNER JOIN ATIVIDADE_COMPLEMENTO AC ON UA.ATIVIDADE_COMPLEMENTO_ID = AC.ID"
	        		+ " INNER JOIN ATIVIDADE A ON AC.ATIVIDADE_ID = A.ID"
	        		+ " LEFT JOIN COMPLEMENTO C ON AC.COMPLEMENTO_ID = C.ID"
	        		+ " INNER JOIN PERFIL P ON UA.PERFIL_ID = P.ID"
	        		+ " WHERE U.EMAIL = ?";
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
	    	  stmt.setString(1,filtro.getEmail());

	    	  ResultSet rs = stmt.executeQuery();
	          while(rs.next()){
	             int id  = rs.getInt("UA."+UsuarioAtividade.ID);
	             Double frequencia = rs.getDouble("UA."+UsuarioAtividade.FREQUENCIA);
	             Double satisfacao = rs.getDouble("UA."+UsuarioAtividade.SATISFACAO);	             
	             Double relacao = rs.getDouble("UA."+UsuarioAtividade.RELACAO);	             
	             String resposta = rs.getString("UA."+UsuarioAtividade.RESPOSTA);	             
	             Integer atividadeComplementoId = rs.getInt("UA."+UsuarioAtividade.ATIVIDADE_COMPLEMENTO);
             
	             int perfilId  = rs.getInt("UA."+UsuarioAtividade.PERFIL);
	            
	             Double artistico = rs.getDouble("P."+Perfil.ARTISTICO);	             
	             Double intelecto = rs.getDouble("P."+Perfil.INTELECTO);	             
	             Double saude = rs.getDouble("P."+Perfil.SAUDE);	             
	             Double social = rs.getDouble("P."+Perfil.SOCIAL);	             
	             Perfil perfil = new Perfil(perfilId,saude,social,intelecto,artistico);
	             
	            
	             //Complemento
	             Integer complementoId = rs.getInt("C."+Complemento.ID);           
	             String nomeComplemento = rs.getString("C."+Complemento.NOME);
	             Complemento complemento = null;
	             if(complementoId != null && complementoId >0 && nomeComplemento != null){
	            	 complemento = new Complemento(complementoId,nomeComplemento);
	             }

	             if(mapper.containsKey(perfilId)){

	            	 UsuarioAtividadeVo aux = mapper.get(perfilId);
        			 List<Complemento> complementos = aux.getComplementos();
        			 if(complemento != null){
        				 complementos.add(complemento);
        			 }
        			 aux.setComplementos(complementos);
		             mapper.put(perfilId, aux);
		             //retorno.set(index, aux);
	             }else{
	            	//Atividade
		             Integer atividadeId = rs.getInt("A."+Atividade.ID);           
		             String nomeAtividade = rs.getString("A."+Atividade.NOME);
		             Atividade atividade = new Atividade(atividadeId, nomeAtividade);

		             //Complemento
		             List<Complemento> complementos = new ArrayList<>();
		             complementos.add(complemento);
		             
		             UsuarioAtividadeVo vo = new UsuarioAtividadeVo();
		             vo.setAtividade(atividade);
		             vo.setComplementos(complementos);
		             vo.setPerfil(perfil);
		             vo.setFrequencia(frequencia);
		             vo.setRelacao(relacao);
		             vo.setResposta(resposta);
		             vo.setSatisfacao(satisfacao);
		             //retorno.add(vo);
		             mapper.put(perfilId, vo);
	             }
	          }
	          rs.close();
	          stmt.close();
	          conn.close();
	          	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      retorno.addAll(mapper.values());
	      return retorno;
	   }
	public List<UsuarioAtividadeVo> selectAllUsuarioAtividade(UsuarioAtividadeVo filtro){
		Map<Integer,UsuarioAtividadeVo> mapper = new HashMap<>();
		List<UsuarioAtividadeVo> retorno = new ArrayList<>();
		int idPerfilAtual = -1;
		boolean mesmaAtividade = true;
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	          sql = "SELECT " 
		        	+  "UA." +UsuarioAtividade.ID + ","
	        		+  "UA." +UsuarioAtividade.FREQUENCIA + ","
	        		+  "UA." +UsuarioAtividade.SATISFACAO + ","
	        		+  "UA." +UsuarioAtividade.ATIVIDADE_COMPLEMENTO + ","
	        		+  "UA." +UsuarioAtividade.PERFIL + ","
	        		+  "UA." +UsuarioAtividade.RELACAO + ","
	        		+  "UA." +UsuarioAtividade.RESPOSTA + ","
	        		+  "P." +Perfil.ARTISTICO + ","
	        		+  "P." +Perfil.INTELECTO + ","
	        		+  "P." +Perfil.SOCIAL + ","	        		
	        		+  "P." +Perfil.SAUDE + ","
	        		+  "AC." +AtividadeComplemento.ATIVIDADE + ","
	        		+  "A." +Atividade.ID + ","
	        		+  "A." +Atividade.NOME + ","
	        		+  "C." +Complemento.ID + ","
	        		+  "C." +Complemento.NOME
	        		+ " FROM USUARIO_ATIVIDADE UA INNER JOIN USUARIO U ON UA.USUARIO_ID=U.ID"
	        		+ " INNER JOIN ATIVIDADE_COMPLEMENTO AC ON UA.ATIVIDADE_COMPLEMENTO_ID = AC.ID"
	        		+ " INNER JOIN ATIVIDADE A ON AC.ATIVIDADE_ID = A.ID"
	        		+ " LEFT JOIN COMPLEMENTO C ON AC.COMPLEMENTO_ID = C.ID"
	        		+ " INNER JOIN PERFIL P ON UA.PERFIL_ID = P.ID"
	        		+ " WHERE UA.PERFIL_ID = ?";
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
	    	  stmt.setInt(1,filtro.getPerfil().getId());
	    	  ResultSet rs = stmt.executeQuery();
	          while(rs.next()){
	        	 UsuarioAtividadeVo aux = new UsuarioAtividadeVo();
	        	 int id  = rs.getInt("UA."+UsuarioAtividade.ID);
	             Double frequencia = rs.getDouble("UA."+UsuarioAtividade.FREQUENCIA);
	             Double satisfacao = rs.getDouble("UA."+UsuarioAtividade.SATISFACAO);	             
	             Double relacao = rs.getDouble("UA."+UsuarioAtividade.RELACAO);	             
	             String resposta = rs.getString("UA."+UsuarioAtividade.RESPOSTA);	             
	             Integer atividadeComplementoId = rs.getInt("UA."+UsuarioAtividade.ATIVIDADE_COMPLEMENTO);
	             aux.setFrequencia(frequencia);
	             aux.setSatisfacao(satisfacao);
	             aux.setRelacao(relacao);
	             aux.setResposta(resposta);
	             aux.setId(id);
	             int perfilId  = rs.getInt("UA."+UsuarioAtividade.PERFIL);
	            
	             Double artistico = rs.getDouble("P."+Perfil.ARTISTICO);	             
	             Double intelecto = rs.getDouble("P."+Perfil.INTELECTO);	             
	             Double saude = rs.getDouble("P."+Perfil.SAUDE);	             
	             Double social = rs.getDouble("P."+Perfil.SOCIAL);	             
	             Perfil perfil = new Perfil(perfilId,saude,social,intelecto,artistico);
	             
	            
	             //Complemento
	             Integer complementoId = rs.getInt("C."+Complemento.ID);           
	             String nomeComplemento = rs.getString("C."+Complemento.NOME);
	             Complemento complemento = null;
	             if(complementoId != null && complementoId >0 && nomeComplemento != null){
	            	 complemento = new Complemento(complementoId,nomeComplemento);
	             }

	             if(mapper.containsKey(perfilId)){

	            	 UsuarioAtividadeVo uaVo = mapper.get(perfilId);
        			 List<Complemento> complementos = uaVo.getComplementos();
        			 if(complemento != null){
        				 complementos.add(complemento);
        			 }
        			 uaVo.setComplementos(complementos);
		             mapper.put(perfilId, uaVo);
        			 //retorno.set(index, aux);
	             }else{
	            	//Atividade
		             Integer atividadeId = rs.getInt("A."+Atividade.ID);           
		             String nomeAtividade = rs.getString("A."+Atividade.NOME);
		             Atividade atividade = new Atividade(atividadeId, nomeAtividade);

		             //Complemento
		             List<Complemento> complementos = new ArrayList<>();
		             complementos.add(complemento);
		             
		             aux.setAtividade(atividade);
		             aux.setComplementos(complementos);
		             aux.setPerfil(perfil);
		             //retorno.add(vo);
		             mapper.put(perfilId, aux);
	             }
	          }
	          rs.close();
	          stmt.close();
	          conn.close();
	          	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      retorno.addAll(mapper.values());
	      return retorno;
	   }
	
	public Integer countAtividade(Atividade at){
	  	  Integer quantidade = -1;
	  	  try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	    	  sql = "SELECT COUNT(DISTINCT perfil_id) as total FROM usuario_atividade ua"
	    	  		+ " INNER JOIN atividade_complemento ac ON ua.atividade_complemento_id=ac.id"
	    	  		+ " WHERE ac.atividade_id = ?";

	        
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
	    	  stmt.setInt(1,at.getId());

	    	  ResultSet rs = stmt.executeQuery();	          
	          while(rs.next()){
	        	  
		        	 quantidade  = rs.getInt("total");
	          }
	          stmt.close();
	          conn.close();
		       	         
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
		      return quantidade;
		   }
	public List<UsuarioAtividadeVo> selectAllRelacao(UsuarioAtividadeVo ua){
		List<UsuarioAtividadeVo> retorno = new ArrayList<>();
		Map<Integer,UsuarioAtividadeVo> mapper = new HashMap<>();
		try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	    	  sql = "SELECT "
	    	  		+  "UA." +UsuarioAtividade.ID + ","
	        		+  "UA." +UsuarioAtividade.FREQUENCIA + ","
	        		+  "UA." +UsuarioAtividade.SATISFACAO + ","
	        		+  "UA." +UsuarioAtividade.ATIVIDADE_COMPLEMENTO + ","
	        		+  "UA." +UsuarioAtividade.PERFIL + ","
	        		+  "UA." +UsuarioAtividade.RELACAO + ","
	        		+  "UA." +UsuarioAtividade.RESPOSTA + ","
	        		+  "P." +Perfil.ARTISTICO + ","
	        		+  "P." +Perfil.INTELECTO + ","
	        		+  "P." +Perfil.SOCIAL + ","	        		
	        		+  "P." +Perfil.SAUDE + ","
	        		+  "AC."+AtividadeComplemento.ATIVIDADE + ","
	        		+  "A." +Atividade.ID + ","
	        		+  "A." +Atividade.NOME + ","
	        		+  "C." +Complemento.ID + ","
	        		+  "C." +Complemento.NOME
	        		+ " FROM USUARIO_ATIVIDADE UA INNER JOIN USUARIO U ON UA.USUARIO_ID=U.ID"
	        		+ " INNER JOIN ATIVIDADE_COMPLEMENTO AC ON UA.ATIVIDADE_COMPLEMENTO_ID = AC.ID"
	        		+ " INNER JOIN ATIVIDADE A ON AC.ATIVIDADE_ID = A.ID"
	        		+ " LEFT JOIN COMPLEMENTO C ON AC.COMPLEMENTO_ID = C.ID"
	        		+ " INNER JOIN PERFIL P ON UA.PERFIL_ID = P.ID"
	    	  		+ " WHERE UA.usuario_id = ?";
	    	  	  
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
	    	  stmt.setInt(1,ua.getUsuario().getId());

	    	  ResultSet rs = stmt.executeQuery();	          
	    	  while(rs.next()){
		        	 UsuarioAtividadeVo aux = new UsuarioAtividadeVo();
		        	 int id  = rs.getInt("UA."+UsuarioAtividade.ID);
		             Double frequencia = rs.getDouble("UA."+UsuarioAtividade.FREQUENCIA);
		             Double satisfacao = rs.getDouble("UA."+UsuarioAtividade.SATISFACAO);	             
		             Double relacao = rs.getDouble("UA."+UsuarioAtividade.RELACAO);	             
		             String resposta = rs.getString("UA."+UsuarioAtividade.RESPOSTA);	             
		             Integer atividadeComplementoId = rs.getInt("UA."+UsuarioAtividade.ATIVIDADE_COMPLEMENTO);
		             aux.setFrequencia(frequencia);
		             aux.setSatisfacao(satisfacao);
		             aux.setRelacao(relacao);
		             aux.setResposta(resposta);
		             aux.setId(id);
		             int perfilId  = rs.getInt("UA."+UsuarioAtividade.PERFIL);
		            
		             Double artistico = rs.getDouble("P."+Perfil.ARTISTICO);	             
		             Double intelecto = rs.getDouble("P."+Perfil.INTELECTO);	             
		             Double saude = rs.getDouble("P."+Perfil.SAUDE);	             
		             Double social = rs.getDouble("P."+Perfil.SOCIAL);	             
		             Perfil perfil = new Perfil(perfilId,saude,social,intelecto,artistico);
		             
		            
		             //Complemento
		             Integer complementoId = rs.getInt("C."+Complemento.ID);           
		             String nomeComplemento = rs.getString("C."+Complemento.NOME);
		             Complemento complemento = null;
		             if(complementoId != null && complementoId >0 && nomeComplemento != null){
		            	 complemento = new Complemento(complementoId,nomeComplemento);
		             }

		             if(mapper.containsKey(perfilId)){

		            	 UsuarioAtividadeVo uaVo = mapper.get(perfilId);
	        			 List<Complemento> complementos = uaVo.getComplementos();
	        			 if(complemento != null){
	        				 complementos.add(complemento);
	        			 }
	        			 uaVo.setComplementos(complementos);
			             mapper.put(perfilId, uaVo);
	        			 //retorno.set(index, aux);
		             }else{
		            	//Atividade
			             Integer atividadeId = rs.getInt("A."+Atividade.ID);           
			             String nomeAtividade = rs.getString("A."+Atividade.NOME);
			             Atividade atividade = new Atividade(atividadeId, nomeAtividade);

			             //Complemento
			             List<Complemento> complementos = new ArrayList<>();
			             complementos.add(complemento);
			             
			             aux.setAtividade(atividade);
			             aux.setComplementos(complementos);
			             aux.setPerfil(perfil);
			             //retorno.add(vo);
			             mapper.put(perfilId, aux);
		             }
		          }
	          rs.close();
	          stmt.close();
	          conn.close();
		       	         
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
	      retorno.addAll(mapper.values());

		      return retorno;
		   }
	public void deleteUsuarioAtividade(UsuarioAtividadeVo ua){
	  	  Integer quantidade = -1;
	  	  try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	    	  sql = "DELETE FROM usuario_atividade WHERE usuario_id = ?"
	    	  		+ " AND perfil_id = ?";
	        
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
	    	  stmt.setInt(1,ua.getUsuario().getId());
	    	  stmt.setInt(2,ua.getPerfil().getId());

	    	  stmt.executeUpdate();	          
	          
	          stmt.close();
	          conn.close();
		       	         
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
		      
		   }
	public Integer countAll(){
	  	  Integer quantidade = -1;
	  	  try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	    	  sql = "SELECT COUNT(DISTINCT perfil_id) as total FROM usuario_atividade ua"
	    	  		+ " INNER JOIN atividade_complemento ac ON ua.atividade_complemento_id=ac.id"
	    	  		+ " GROUP BY ua.perfil_id";

	        
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);

	    	  ResultSet rs = stmt.executeQuery();	          
	          while(rs.next()){
		        	 quantidade  = rs.getInt("total");
	          }
	          stmt.close();
	          conn.close();
		       	         
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
		      return quantidade;
		   }
	
	public UsuarioAtividadeVo saveMultiplosUsuarioAtividade(UsuarioAtividadeVo usuarioAtividade){
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  //conn.setAutoCommit(false);
	    	  PreparedStatement stmt = null;
	    	  String sql;
		    	  sql = "INSERT INTO USUARIO_ATIVIDADE " + 
		    	  "(" + UsuarioAtividade.USUARIO +
		    	  "," + UsuarioAtividade.ATIVIDADE_COMPLEMENTO +
		    	  "," + UsuarioAtividade.PERFIL +
		    	  "," + UsuarioAtividade.FREQUENCIA +
		    	  "," + UsuarioAtividade.SATISFACAO +
 		    	  "," + UsuarioAtividade.RELACAO +
 		    	  "," + UsuarioAtividade.RESPOSTA +
 		    	  ")";
		    	  sql +=" VALUES (?,?,?,?,?,?,?)";
			    	   
		    	  stmt = (PreparedStatement) conn.prepareStatement(sql);

		          for(AtividadeComplemento ac : usuarioAtividade.getAtividadeComplemento()){
		    		  stmt.setInt(1,usuarioAtividade.getUsuario().getId());
			    	  stmt.setInt(2,ac.getId());
			    	  stmt.setInt(3,usuarioAtividade.getPerfil().getId());
			    	  stmt.setDouble(4,usuarioAtividade.getFrequencia());
			    	  stmt.setDouble(5,usuarioAtividade.getSatisfacao());
			    	  stmt.setDouble(6,usuarioAtividade.getRelacao());
			    	  stmt.setString(7,usuarioAtividade.getResposta());
			    	  stmt.addBatch();
	    	  }
	    	  int[] updateCounts = stmt.executeBatch();
	    	  //int affectedRows = stmt.executeUpdate();
//	          if (affectedRows == 0) {
//	              throw new SQLException("Creating user failed, no rows affected.");
//	          }
	          stmt.close();
	          conn.close();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return usuarioAtividade;
	   }
	

}
