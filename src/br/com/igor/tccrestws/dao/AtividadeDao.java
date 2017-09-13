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

public class AtividadeDao {

	private ConexaoMySQL conMySQL = new ConexaoMySQL();
	
	public Atividade selectAtividade(Atividade filtro){
	      Atividade atividade = null;
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	          sql = "SELECT * FROM ATIVIDADE WHERE ID = ??";
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
	    	  stmt.setString(1,filtro.getNome());
	    	  ResultSet rs = stmt.executeQuery();	          
	          while(rs.next()){
	        	 Integer id  = rs.getInt(Atividade.ID);
	             String nome = rs.getString(Atividade.NOME);
	             Integer perfilId = rs.getInt(Atividade.PERFIL_ID);
	             atividade = new Atividade(id, nome,new Perfil(perfilId));
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

	
	public List<Atividade> selectAllAtividade(Atividade filtro){
	      Atividade atividade = null;
	      List<Atividade> list = new ArrayList<>();
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	          sql = "SELECT * FROM ATIVIDADE AS A INNER JOIN PERFIL AS P ON A.PERFIL_ID = P.ID";
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
	    	  ResultSet rs = stmt.executeQuery();	          
	          while(rs.next()){
	        	 Integer id  = rs.getInt(Atividade.ID);
	             String nome = rs.getString(Atividade.NOME);	        
	        	 Integer perfilId  = rs.getInt(Atividade.PERFIL_ID);
	        	 atividade = new Atividade(id, nome,new Perfil(perfilId));
	             list.add(atividade);
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
