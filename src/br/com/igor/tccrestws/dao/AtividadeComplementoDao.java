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

public class AtividadeComplementoDao {

	private ConexaoMySQL conMySQL = new ConexaoMySQL();
	

	public AtividadeComplemento selectParAtividadeComplemento(AtividadeComplemento filtro){
	      AtividadeComplemento atividadeComplemento = null;
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	          sql = "SELECT "
	        		  +"AC."+ AtividadeComplemento.ATIVIDADE + ","
	        		  +"AC."+ AtividadeComplemento.COMPLEMENTO + ","
	        		  +"AC."+ AtividadeComplemento.ID
	        		+ " FROM ATIVIDADE_COMPLEMENTO AC "
	          		+ "WHERE AC.ATIVIDADE_ID = ? AND AC.COMPLEMENTO_ID = ?";
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
	    	  stmt.setInt(1,filtro.getAtividade().getId());
	    	  stmt.setInt(2,filtro.getComplemento().getId());
	    	  ResultSet rs = stmt.executeQuery();	          
	          while(rs.next()){
	        	 Integer ativiadeComplementoId  = rs.getInt(AtividadeComplemento.ID);
	             atividadeComplemento = filtro;
	             atividadeComplemento.setId(ativiadeComplementoId);
	          }
	          rs.close();
	          stmt.close();
	          conn.close();
	          	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return atividadeComplemento;
	}

	public AtividadeComplemento salvarAtividadeComplemento(AtividadeComplemento filtro){
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	    	  sql = "INSERT INTO ATIVIDADE_COMPLEMENTO(ATIVIDADE_ID";
	    	  if(filtro.getComplemento() != null){
	    		  sql += ",COMPLEMENTO_ID";
		    	  sql += ") VALUES (?,?)";
	    	  }else{
		    	  sql += ") VALUES (?)";
	    	  }
		         
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS );
	    	  stmt.setInt(1,filtro.getAtividade().getId());
	    	  if(filtro.getComplemento() != null){
	    		  stmt.setInt(2,filtro.getComplemento().getId());
	    	  }
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

	
	public List<AtividadeComplemento> selectAllAtividadeComplementoFromAtividade(AtividadeComplemento filtro){
	      List<AtividadeComplemento> list = new ArrayList<>();
	      AtividadeComplemento atividadeComplemento = null;
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	          sql = "SELECT AC.ID,A.ID,C.ID,A.NOME,C.NOME FROM ATIVIDADE_COMPLEMENTO AC "
	          		+ "INNER JOIN ATIVIDADE A ON AC.ATIVIDADE_ID = A.ID "
	          		+ "INNER JOIN COMPLEMENTO C ON C.ID = AC.COMPLEMENTO_ID "
	          		+ "WHERE AC.ATIVIDADE_ID = ?";
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
	    	  stmt.setInt(1,filtro.getId());
	    	  ResultSet rs = stmt.executeQuery();	          
	          while(rs.next()){
	        	 Integer ativiadeComplementoId  = rs.getInt(AtividadeComplemento.ID);
		        
	        	 Integer complementoId  = rs.getInt("C."+Complemento.ID);
	        	 String complementoNome  = rs.getString("C."+Complemento.NOME);

	        	 Integer atividadeId  = rs.getInt("A."+Atividade.ID);
	        	 String atividadeNome = rs.getString("A."+Atividade.NOME);

	             atividadeComplemento = new AtividadeComplemento(new Atividade(atividadeId,atividadeNome), new Complemento(complementoId,complementoNome));
	             list.add(atividadeComplemento);
	          }
	          rs.close();
	          stmt.close();
	          conn.close();
	          	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return list;
	   }
}
