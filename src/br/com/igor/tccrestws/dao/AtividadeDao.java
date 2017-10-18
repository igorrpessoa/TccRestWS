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
import br.com.igor.tccrestws.entity.Perfil;
import br.com.igor.tccrestws.entity.Usuario;
import br.com.igor.tccrestws.entity.UsuarioAtividade;
import br.com.igor.tccrestws.vo.AtividadeVo;

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

	
	public List<AtividadeVo> selectAllAtividadeSugestao(Usuario filtro){
	      Atividade atividade = null;
	      Perfil perfilUsuario = new Perfil();
	      List<AtividadeVo> list = new ArrayList<>();
	      //utilizando 10%
	      float porcentagem = 0.2f;
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	          sql = "SELECT * FROM atividade a "
	          		+ "INNER JOIN usuario_atividade ua ON a.id = ua.atividade_id "
	          		+ "INNER JOIN perfil p ON p.id = ua.perfil_id WHERE " 
					+ "p.saude BETWEEN ? and ? AND " 
					+ "p.intelecto BETWEEN ? AND ? AND " 
					+ "p.artistico BETWEEN ? AND ? AND " 
					+ "p.social BETWEEN ? AND ? AND "
					+ "a.valido = 1";
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
	    	  ResultSet rs = stmt.executeQuery();	          
	          while(rs.next()){
	        	 Integer id  = rs.getInt("a." + Atividade.ID);
	             String nome = rs.getString("a." + Atividade.NOME);	        
	        	 atividade = new Atividade(id, nome);
	             list.add(new AtividadeVo(atividade, null));
	          }
	          rs.close();
	          stmt.close();
	          conn.close();
	          	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return list;
	   }
	
	public List<Atividade> selectAtividades(AtividadeVo vo){
	      Atividade filtro = vo.getAtividade();
	      Atividade atividade = null;
	      List<Atividade> atividades = new ArrayList<>();
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
