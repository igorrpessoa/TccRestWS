package br.com.igor.tccrestws.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import br.com.igor.tccrestws.ConexaoMySQL;
import br.com.igor.tccrestws.User;
import br.com.igor.tccrestws.entity.Perfil;
import br.com.igor.tccrestws.entity.Usuario;
import br.com.igor.tccrestws.filtro.FiltroUsuario;

public class UsuarioDao {

	private ConexaoMySQL conMySQL = new ConexaoMySQL();

	
	public Usuario selectUsuario(FiltroUsuario filtro){
	      Usuario usuario = null;
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	          sql = "select * from usuario where email= ?";
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
	    	  stmt.setString(1,filtro.getEmail());
	    	  ResultSet rs = stmt.executeQuery();	          
	          while(rs.next()){
	             int id  = rs.getInt(Usuario.ID);
	             String email = rs.getString(Usuario.EMAIL);
	             String nome = rs.getString(Usuario.NOME);
	             Perfil perfil = (Perfil)rs.getObject(Usuario.PERFIL);
	             String senha = rs.getString(Usuario.SENHA);
	             usuario = new Usuario(id, nome, email,senha,perfil);
	          }
	          rs.close();
	          stmt.close();
	          conn.close();
	          	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return usuario;
	   }
}
