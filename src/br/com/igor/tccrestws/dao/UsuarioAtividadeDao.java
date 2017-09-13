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

public class UsuarioAtividadeDao {

	private ConexaoMySQL conMySQL = new ConexaoMySQL();

	
	public List<Atividade> selectAllUsuarioAtividade(Usuario filtro){
		List<Atividade> retorno = new ArrayList<>();
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	          sql = "SELECT " 
		        	+  "UA." +UsuarioAtividade.ID + ","
	        		+  "UA." +UsuarioAtividade.FREQUENCIA + ","
	        		+  "UA." +UsuarioAtividade.SATISFACAO + ","
	        		+  "UA." +UsuarioAtividade.ATIVIDADE + ","
	        		+  "UA." +UsuarioAtividade.DESCRICAO + ","
	        		+  "A." +Atividade.NOME
	        		+ " FROM USUARIO_ATIVIDADE UA INNER JOIN USUARIO U ON UA.USUARIO_ID=U.ID"
	        		+ " INNER JOIN ATIVIDADE A ON UA.ATIVIDADE_ID = A.ID"
	        		+ " INNER JOIN PERFIL P ON A.PERFIL_ID = P.ID"
	        		+ " WHERE U.EMAIL = ?";
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
	    	  stmt.setString(1,filtro.getEmail());
	    	  ResultSet rs = stmt.executeQuery();
	          while(rs.next()){
	             int id  = rs.getInt("UA."+UsuarioAtividade.ID);
	             Double frequencia = rs.getDouble("UA."+UsuarioAtividade.FREQUENCIA);
	             Double satisfacao = rs.getDouble("UA."+UsuarioAtividade.SATISFACAO);	             
	             Integer atividadeId = rs.getInt(UsuarioAtividade.ATIVIDADE);
	             String descricao = rs.getString(UsuarioAtividade.DESCRICAO);
             
	             int perfilId  = rs.getInt("P."+Perfil.ID);
	             Double artistico = rs.getDouble("P."+Perfil.ARTISTICO);	             
	             Double intelecto = rs.getDouble("P."+Perfil.INTELECTO);	             
	             Double saude = rs.getDouble("P."+Perfil.SAUDE);	             
	             Double social = rs.getDouble("P."+Perfil.SOCIAL);	             

	             String nomeAtividade = rs.getString(Atividade.NOME);
	             Atividade atividade = new Atividade(atividadeId, nomeAtividade, new Perfil(perfilId,saude,social,intelecto,artistico));
	             
	             retorno.add(atividade);
	          }
	          rs.close();
	          stmt.close();
	          conn.close();
	          	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return retorno;
	   }
	
	public UsuarioAtividade saveUsuarioAtividade(UsuarioAtividade usuarioAtividade){
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	    	  sql = "INSERT INTO USUARIO_ATIVIDADE(USUARIO_ID,ATIVIDADE_ID,FREQUENCIA,SATISFACAO)";
	    	  sql +=" VALUES (?,?,?,?)";
		    	   
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
	    	  stmt.setInt(1,usuarioAtividade.getUsuario().getId());
	    	  stmt.setInt(2,usuarioAtividade.getAtividade().getId());
	    	  stmt.setDouble(3,usuarioAtividade.getFrequencia());
	    	  stmt.setDouble(4,usuarioAtividade.getSatisfacao());	    	
	    	  int affectedRows = stmt.executeUpdate();
	          if (affectedRows == 0) {
	              throw new SQLException("Creating user failed, no rows affected.");
	          }
	          stmt.close();
	          conn.close();
	          	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return usuarioAtividade;
	   }
}
