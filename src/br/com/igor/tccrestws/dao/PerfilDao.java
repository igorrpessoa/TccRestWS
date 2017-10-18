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

public class PerfilDao {

	private ConexaoMySQL conMySQL = new ConexaoMySQL();
	
	public Perfil selectPerfil(Perfil filtro){
		Perfil perfil = null;
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	          sql = "select * from perfil where id = ?";
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
	    	  stmt.setInt(1,filtro.getId());
	    	  ResultSet rs = stmt.executeQuery();	          
	          while(rs.next()){
	        	 Integer id  = rs.getInt(Perfil.ID);
	             Double artistico = rs.getDouble(Perfil.ARTISTICO);
	             Double intelecto = rs.getDouble(Perfil.INTELECTO);
	             Double social = rs.getDouble(Perfil.SOCIAL);
	             Double saude = rs.getDouble(Perfil.SAUDE);
             
	             perfil = new Perfil(id, saude,social,intelecto,artistico);
	          }
	          rs.close();
	          stmt.close();
	          conn.close();
	          	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return perfil;
	   }

	public Perfil salvarPerfil(Perfil filtro){
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	    	  sql = "INSERT INTO PERFIL(SAUDE,SOCIAL,INTELECTO,ARTISTICO";
	    	  sql += ") VALUES (?,?,?,?)";
	         
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS );
	    	  stmt.setDouble(1,filtro.getSaude());
	    	  stmt.setDouble(2, filtro.getSocial());
	    	  stmt.setDouble(3, filtro.getIntelecto());
	    	  stmt.setDouble(4, filtro.getArtistico());
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
	
	public Perfil updatePerfil(Perfil perfil){
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	    	  sql = "UPDATE PERFIL SET SOCIAL=?,SAUDE=?,INTELECTO= ?, ARTISTICO=? WHERE ID = ?";
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
	    	  stmt.setDouble(1,perfil.getSocial());
	    	  stmt.setDouble(2,perfil.getSaude());
	    	  stmt.setDouble(3,perfil.getIntelecto());
	    	  stmt.setDouble(4,perfil.getArtistico());
	    	  stmt.setInt(5,perfil.getId());
    	  	  int affectedRows = stmt.executeUpdate();
	          if (affectedRows == 0) {
	              throw new SQLException("Creating user failed, no rows affected.");
	          }
	          stmt.close();
	          conn.close();
	          	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return perfil;
	   }
}
