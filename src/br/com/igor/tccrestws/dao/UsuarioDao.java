package br.com.igor.tccrestws.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import br.com.igor.tccrestws.ConexaoMySQL;
import br.com.igor.tccrestws.entity.Perfil;
import br.com.igor.tccrestws.entity.Usuario;

public class UsuarioDao {

	private ConexaoMySQL conMySQL = new ConexaoMySQL();

	
	public Usuario selectUsuario(Usuario filtro){
	      Usuario usuario = null;
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	          sql = "SELECT * FROM USUARIO ";
	          sql += "AS U INNER JOIN PERFIL AS P ON U.PERFIL_ID=P.ID WHERE U.EMAIL = ?";
	    	 
	          PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
	    	  stmt.setString(1,filtro.getEmail());
	    	  ResultSet rs = stmt.executeQuery();	          
	          while(rs.next()){
	             int id  = rs.getInt(Usuario.ID);
	             String email = rs.getString(Usuario.EMAIL);
	             String nome = rs.getString(Usuario.NOME);
	             Integer perfil = rs.getInt(Usuario.PERFIL);
	             String senha = rs.getString(Usuario.SENHA);	             
	             Perfil p = new Perfil();
	             Double artistico = rs.getDouble(Perfil.ARTISTICO);
	             Double intelecto = rs.getDouble(Perfil.INTELECTO);
	             Double social = rs.getDouble(Perfil.SOCIAL);
	             Double saude = rs.getDouble(Perfil.SAUDE);
	             p.setArtistico(artistico);
	             p.setIntelecto(intelecto);
	             p.setSaude(saude);
	             p.setSocial(social);
	             p.setId(perfil);
	             

	             usuario = new Usuario(id, nome, email,senha,p);
	          }
	          rs.close();
	          stmt.close();
	          conn.close();
	          	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return usuario;
	   }
	
	public Usuario saveUsuario(Usuario usuario){
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	    	  sql = "INSERT INTO USUARIO(NOME,EMAIL,SENHA";
	    	  if(usuario.getPerfil() != null){
	    		  sql += ",PERFIL_ID) VALUES (?,?,?,?)";
	    	  } else{
	    		  sql += ") VALUES (?,?,?)";
	    	  }
	         
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
	    	  stmt.setString(1,usuario.getNome());
	    	  stmt.setString(2,usuario.getEmail());
	    	  stmt.setString(3,usuario.getSenha());
	    	  if(usuario.getPerfil() != null){
	    		  stmt.setInt(5,usuario.getPerfil().getId());
	    	  }
	    	  int affectedRows = stmt.executeUpdate();
	          if (affectedRows == 0) {
	              throw new SQLException("Creating user failed, no rows affected.");
	          }
	          stmt.close();
	          conn.close();
	          	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return usuario;
	   }
}
