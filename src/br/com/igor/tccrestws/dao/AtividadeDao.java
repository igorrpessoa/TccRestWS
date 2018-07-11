package br.com.igor.tccrestws.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import br.com.igor.tccrestws.ConexaoMySQL;
import br.com.igor.tccrestws.entity.Atividade;
import br.com.igor.tccrestws.entity.AtividadeComplemento;
import br.com.igor.tccrestws.entity.Complemento;
import br.com.igor.tccrestws.entity.Perfil;
import br.com.igor.tccrestws.entity.Usuario;
import br.com.igor.tccrestws.entity.UsuarioAtividade;
import br.com.igor.tccrestws.vo.AtividadeVo;
import br.com.igor.tccrestws.vo.UsuarioAtividadeVo;

public class AtividadeDao {

	private ConexaoMySQL conMySQL = new ConexaoMySQL();
	
	public Atividade selectAtividade(AtividadeVo vo){
	      Atividade filtro = vo.getAtividade();
	      Atividade atividade = null;
	      int contador=1;
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql,where="";
	          sql = "SELECT * FROM ATIVIDADE";
    		  if(filtro.getId()!=null){
    			  where +=" WHERE ID = ?";
    		  }
    		  if(filtro.getNome()!=null && !filtro.getNome().isEmpty() && !filtro.getNome().equals("")){
    			  if(where != null && !where.isEmpty()){
    				  where+=" AND";
    			  }else{
    				  where+= " WHERE";
    			  }
    			  where+=" NOME = ?";
    		  }
    		  if(filtro.getValido()!=null && !filtro.getValido().equals(1)){
    			  if(where != null && !where.isEmpty()){
    				  where+=" AND";
    			  }else{
    				  where+= " WHERE";
    			  }
    			  where+=" VALIDO = ?";
    		  }
    		  sql += where;
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
    		  if(filtro.getId()!=null){
    			  stmt.setInt(contador,filtro.getId());
    			  contador++;
    		  }
    		  if(filtro.getNome()!=null && !filtro.getNome().isEmpty() && !filtro.getNome().equals("")){
    			  stmt.setString(contador,filtro.getNome());
    			  contador++;
    		  }
    		  if(filtro.getValido()!=null && !filtro.getValido().equals(1)){
    			  stmt.setInt(contador, filtro.getValido());
    			  contador++;
    		  }
	    	  ResultSet rs = stmt.executeQuery();	          
	          while(rs.next()){
	        	 Integer id  = rs.getInt(Atividade.ID);
	             String nome = rs.getString(Atividade.NOME);
	             Integer valido = rs.getInt(Atividade.VALIDO);
	             atividade = new Atividade(id, nome,valido);
	          }
	          rs.close();
	          stmt.close();
	          conn.close();
	          	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return atividade;
	   }

	
	public Atividade salvarAtividade(Atividade filtro){
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	    	  sql = "INSERT INTO ATIVIDADE(NOME";
	    	  sql += ") VALUES (?)";
	         
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS );
	    	  stmt.setString(1,filtro.getNome());
	    	  
	    	  int affectedRows = stmt.executeUpdate();
	    	  try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	    		  if (generatedKeys.next()) {
	            	  filtro.setId((int)generatedKeys.getLong(1));
	              }
	              else {
	                  throw new SQLException("Creating user failed, no ID obtained.");
	              }
	          }
	          if (affectedRows == 0) {
	              throw new SQLException("Creating user failed, no rows affected.");
	          }
	          stmt.close();
	          conn.close();
	       	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return filtro;
	   }
	public Atividade updateAtividade(Atividade filtro){
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	    	  sql = "UPDATE ATIVIDADE SET VALIDO = 1 WHERE ID=?";
	         
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS );
	    	  stmt.setInt(1,filtro.getId());
	    	  
	    	  int affectedRows = stmt.executeUpdate();
	    	  try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	    		  if (generatedKeys.next()) {
	            	  filtro.setId((int)generatedKeys.getLong(1));
	              }
	              else {
	                  throw new SQLException("Creating user failed, no ID obtained.");
	              }
	          }
	          if (affectedRows == 0) {
	              throw new SQLException("Creating user failed, no rows affected.");
	          }
	          stmt.close();
	          conn.close();
	       	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return filtro;
	   }
	
	public List<UsuarioAtividadeVo> selectAllAtividadeSugestao(Usuario filtro,float porcentagem){
	      Perfil perfilUsuario = new Perfil();
	      List<Complemento> complementos = new ArrayList<>();
	      List<UsuarioAtividadeVo> list = new ArrayList<>();
	      UsuarioAtividadeVo usuarioAtividade = new UsuarioAtividadeVo();
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	          sql = "SELECT * FROM USUARIO_ATIVIDADE UA INNER JOIN USUARIO U ON UA.USUARIO_ID=U.ID"
	        		+ " INNER JOIN ATIVIDADE_COMPLEMENTO AC ON UA.ATIVIDADE_COMPLEMENTO_ID = AC.ID"
	        		+ " INNER JOIN ATIVIDADE A ON AC.ATIVIDADE_ID = A.ID"
	        		+ " LEFT JOIN COMPLEMENTO C ON AC.COMPLEMENTO_ID = C.ID"
	        		+ " INNER JOIN PERFIL P ON UA.PERFIL_ID = P.ID WHERE "
					+ "p.saude BETWEEN ? and ? AND " 
					+ "p.intelecto BETWEEN ? AND ? AND " 
					+ "p.artistico BETWEEN ? AND ? AND " 
					+ "p.social BETWEEN ? AND ? AND "
					+ "a.valido = 1 AND ac.atividade_id NOT IN ("
					+ "SELECT ac.atividade_id FROM usuario_atividade ua INNER JOIN atividade_complemento ac ON ac.id = ua.atividade_complemento_id "
					+ "WHERE ua.usuario_id = ?)";
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
	    	  perfilUsuario = filtro.getPerfil();
	    	  stmt.setDouble(1,perfilUsuario.getSaude() - perfilUsuario.getSaude()*porcentagem);
	    	  stmt.setDouble(2,perfilUsuario.getSaude() + perfilUsuario.getSaude()*porcentagem);
	    	  stmt.setDouble(3,perfilUsuario.getIntelecto() - perfilUsuario.getIntelecto()*porcentagem);
	    	  stmt.setDouble(4,perfilUsuario.getIntelecto() + perfilUsuario.getIntelecto()*porcentagem);
	    	  stmt.setDouble(5,perfilUsuario.getArtistico() - perfilUsuario.getArtistico()*porcentagem);
	    	  stmt.setDouble(6,perfilUsuario.getArtistico() + perfilUsuario.getArtistico()*porcentagem);
	    	  stmt.setDouble(7,perfilUsuario.getSocial() - perfilUsuario.getSocial()*porcentagem);
	    	  stmt.setDouble(8,perfilUsuario.getSocial() + perfilUsuario.getSocial()*porcentagem);
	    	  stmt.setInt(9,filtro.getId());
	    	  ResultSet rs = stmt.executeQuery();	          
	          while(rs.next()){
		         //Usuario Atividade
	        	 Integer uaId  = rs.getInt("UA." + UsuarioAtividade.ID);
	        	 Double freq = rs.getDouble("UA."+ UsuarioAtividade.FREQUENCIA);
	        	 Double satis = rs.getDouble("UA."+ UsuarioAtividade.SATISFACAO);
	        	 usuarioAtividade.setId(uaId);
	        	 usuarioAtividade.setFrequencia(freq);
	        	 usuarioAtividade.setSatisfacao(satis);
	        	 //Atividade
	        	 Integer aId  = rs.getInt("a." + Atividade.ID);
	             String nomeAtividade = rs.getString("a." + Atividade.NOME);	        
	        	 Atividade atividade = new Atividade(aId, nomeAtividade);
	        	 usuarioAtividade.setAtividade(atividade);
	        	 //Complemento
	             Integer cId = rs.getInt("C." + Complemento.ID);
	             String nomeComplemento = rs.getString("C." + Complemento.NOME);
	             Complemento complemento = new Complemento(cId, nomeComplemento);
	             complementos.add(complemento);
	             usuarioAtividade.setComplementos(complementos);
	             //Perfil
	             Integer pId = rs.getInt("P."+Perfil.ID);
	             Double art = rs.getDouble("P."+Perfil.ARTISTICO);
	             Double inte = rs.getDouble("P."+Perfil.INTELECTO);
	             Double soc = rs.getDouble("P."+Perfil.SOCIAL);
	             Double sau = rs.getDouble("P."+Perfil.SAUDE);
	             Perfil p = new Perfil(pId,sau,soc,inte,art);
	             usuarioAtividade.setPerfil(p);
	             //AtividadeComplemento
	        	 Integer acId = rs.getInt("AC." + AtividadeComplemento.ID);
	        	 
	             list.add(usuarioAtividade);
	          }
	          rs.close();
	          stmt.close();
	          conn.close();
	          	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return list;
	   }
	
	public List<AtividadeVo> selectAtividades(AtividadeVo vo){
	      Atividade filtro = vo.getAtividade();
	      AtividadeVo atividade = null;
	      List<AtividadeVo> atividades = new ArrayList<>();
	      int contador=1;
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql,where="";
	          sql = "SELECT * FROM ATIVIDADE";
  		  if(filtro != null && filtro.getId()!=null){
  			  where +=" WHERE ID = ?";
  		  }
  		  if(filtro != null && filtro.getNome()!=null && !filtro.getNome().isEmpty() && !filtro.getNome().equals("")){
  			  if(where != null && !where.isEmpty()){
  				  where+=" AND";
  			  }else{
  				  where+= " WHERE";
  			  }
  			  where+=" NOME = ?";
  		  }
  		  if(filtro != null && filtro.getValido()!=null && !filtro.getValido().equals(1)){
  			  if(where != null && !where.isEmpty()){
  				  where+=" AND";
  			  }else{
  				  where+= " WHERE";
  			  }
  			  where+=" VALIDO = ?";
  		  }
  		  sql += where;
  		sql += " ORDER BY NOME ASC";
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
  		  if(filtro != null && filtro.getId()!=null){
  			  stmt.setInt(contador,filtro.getId());
  			  contador++;
  		  }
  		  if(filtro != null && filtro.getNome()!=null && !filtro.getNome().isEmpty() && !filtro.getNome().equals("")){
  			  stmt.setString(contador,filtro.getNome());
  			  contador++;
  		  }
  		  if(filtro != null && filtro.getValido()!=null && !filtro.getValido().equals(1)){
  			  stmt.setInt(contador, filtro.getValido());
  			  contador++;
  		  }
	    	  ResultSet rs = stmt.executeQuery();	          
	          while(rs.next()){
	        	 Integer id  = rs.getInt(Atividade.ID);
	             String nome = rs.getString(Atividade.NOME);
	             Integer valido = rs.getInt(Atividade.VALIDO);
	             atividade = new AtividadeVo();
	             atividade.setAtividade(new Atividade(id, nome,valido));
	             atividades.add(atividade);
	          }
	          rs.close();
	          stmt.close();
	          conn.close();
	          	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return atividades;
	   }
	
}
