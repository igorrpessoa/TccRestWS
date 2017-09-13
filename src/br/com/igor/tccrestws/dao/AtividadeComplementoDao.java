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
	


	public List<AtividadeComplemento> salvarAtividadeComplemento(List<AtividadeComplemento> filtro){
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	    	  sql = "INSERT INTO ATIVIDADE_COMPLEMENTO(ATIVIDADE_ID,COMPLEMENTO_ID";
	    	  sql += ") VALUES (?,?)";
	         
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS );

	    	  for (AtividadeComplemento n : filtro)
	    	    {
	    		  stmt.setInt(1,n.getAtividade().getId());
	    		  stmt.setInt(2,n.getComplemento().getId());
	    		  stmt.addBatch();  

	    	    }
	    	  int[] valores = stmt.executeBatch();  

//	    	  int affectedRows = stmt.executeUpdate();
	          for (int i =0;i<filtro.size();i++) {
	        	  if(valores[i] == 0){
	        		  throw new SQLException("Creating user failed, no rows affected.");
	        	  }
	          }
	    	  try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	              int i =0;
	    		  if (generatedKeys.next()) {
	            	  filtro.get(i).setId((int)generatedKeys.getLong(1));
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

	
	public List<AtividadeComplemento> selectAllAtividadeComplementoFromAtividade(Atividade filtro){
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
	        	 Integer perfilId  = rs.getInt("A."+Atividade.PERFIL_ID);

	             atividadeComplemento = new AtividadeComplemento(new Atividade(atividadeId,atividadeNome,new Perfil(perfilId)), new Complemento(complementoId,complementoNome));
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
