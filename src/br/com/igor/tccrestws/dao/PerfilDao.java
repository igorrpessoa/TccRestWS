package br.com.igor.tccrestws.dao;

import java.sql.Connection;
import java.sql.ResultSet;
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

}
