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
import br.com.igor.tccrestws.entity.Complemento;
import br.com.igor.tccrestws.entity.Perfil;
import br.com.igor.tccrestws.entity.Usuario;

public class ComplementoDao {

	private ConexaoMySQL conMySQL = new ConexaoMySQL();
	
	public Complemento selectComplemento(Complemento filtro){
	    List<Complemento> list = new ArrayList<>(); 
		Complemento complemento = null;
	      Integer contador = 0;
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql,where="";
	          sql = "SELECT * FROM COMPLEMENTO"; 
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
	          if(filtro.getValido() != null){
	        	  if(where != null && !where.isEmpty()){
    				  where+=" AND";
    			  }else{
    				  where+= " WHERE";
    			  }
    			  where+=" VALIDO = ?";
	          }
    		  sql += where;
    		  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
	    	  if(filtro.getId() != null){
	    		  contador++;
	    		  stmt.setInt(contador,filtro.getId());
	    	  }
    		  if(filtro.getNome()!=null && !filtro.getNome().isEmpty() && !filtro.getNome().equals("")){
	    		  contador++;
	    		  stmt.setString(contador,filtro.getNome());
	    	  }
	    	  if(filtro.getValido() != null){
	    		  contador++;
	    		  stmt.setInt(contador,filtro.getValido());
	    	  }
	    	  ResultSet rs = stmt.executeQuery();	          
	          while(rs.next()){
	        	 Integer id  = rs.getInt(Complemento.ID);
	             String nome = rs.getString(Complemento.NOME);
	        	 Integer valido  = rs.getInt(Complemento.VALIDO);
	        	 complemento = new Complemento(id, nome,valido);
	          }
	          rs.close();
	          stmt.close();
	          conn.close();
	          	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return complemento;
	   }

	public List<Complemento> salvarComplementos(List<Complemento> filtro){
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	    	  sql = "INSERT INTO COMPLEMENTO(NOME";
	    	  sql += ") VALUES (?)";
	         
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS );
//	          Statement stmt2 = conn.createStatement();

	    	  for (Complemento n : filtro)
	    	    {
	    		  stmt.setString(1,n.getNome());
//	    		  ResultSet rs = stmt.executeUpdate("SELECT LAST_INSERT_ID() as last_id");           // the INSERT happens here
//	    		  n.setId(Integer.valueOf(rs.getString("last_id")));
	    		  stmt.addBatch();  

	    	    }
	    	  int[] valores = stmt.executeBatch();  

//	    	  int affectedRows = stmt.executeUpdate();
	          for (int i =0;i<filtro.size();i++) {
	        	  if(valores[i] == 0){
	        		  throw new SQLException("Creating user failed, no rows affected.");
	        	  }
	          }
              int i =0;
	    	  try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	    		  if (generatedKeys.next()) {
	            	  filtro.get(i).setId((int)generatedKeys.getLong(1));
	            	  i++;
	              }
	              else {
	                  throw new SQLException("Creating user failed, no ID obtained.");
	              }
	          }
	          stmt.close();
	          conn.close();
	       	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return filtro;
	   }

	
	public List<Complemento> selectAllComplemento(Complemento filtro){
	      Complemento complemento = null;
	      int contador=0;
	      List<Complemento> list = new ArrayList<>();
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql="",where="";
	          sql = "SELECT * FROM COMPLEMENTO"; 
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
	          if(filtro.getValido() != null){
	        	  if(where != null && !where.isEmpty()){
    				  where+=" AND";
    			  }else{
    				  where+= " WHERE";
    			  }
    			  where+=" VALIDO = ?";
	          }
    		  sql += where;
    		  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
	    	  if(filtro.getId() != null){
	    		  contador++;
	    		  stmt.setInt(contador,filtro.getId());
	    	  }
    		  if(filtro.getNome()!=null && !filtro.getNome().isEmpty() && !filtro.getNome().equals("")){
	    		  contador++;
	    		  stmt.setString(contador,filtro.getNome());
	    	  }
	    	  if(filtro.getValido() != null){
	    		  contador++;
	    		  stmt.setInt(contador,filtro.getValido());
	    	  }
	    	  ResultSet rs = stmt.executeQuery();	          
	          while(rs.next()){
	        	 Integer id  = rs.getInt(Complemento.ID);
	             String nome = rs.getString(Complemento.NOME);	        
	             complemento = new Complemento(id, nome);
	             list.add(complemento);
	          }
	          rs.close();
	          stmt.close();
	          conn.close();
	          	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return list;
	   }
	
	public Complemento salvarComplemento(Complemento filtro){
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	    	  sql = "INSERT INTO COMPLEMENTO(NOME";
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
}
