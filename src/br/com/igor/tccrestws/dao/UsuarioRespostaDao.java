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
import br.com.igor.tccrestws.entity.UsuarioAtividade;
import br.com.igor.tccrestws.entity.UsuarioResposta;

public class UsuarioRespostaDao {

	private ConexaoMySQL conMySQL = new ConexaoMySQL();

	
	public List<UsuarioResposta> selectAllUsuarioResposta(UsuarioResposta filtro){
		List<UsuarioResposta> retorno = new ArrayList<>();
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	          sql = "SELECT " 
		        	+  "UR." +UsuarioResposta.ID + ","
	        		+  "UR." +UsuarioResposta.RESPOSTA + ","
	        		+  "UR." +UsuarioResposta.USUARIO_ATIVIDADE + ","	    
	        		+ " FROM USUARIO_RESPOSTA UR INNER JOIN USUARIO U ON UA.USUARIO_ID=U.ID"
	        		+ " INNER JOIN ATIVIDADE_COMPLEMENTO AC ON UA.ATIVIDADE_COMPLEMENTO_ID = AC.ID"
	        		+ " INNER JOIN ATIVIDADE A ON UC.ATIVIDADE_ID = A.ID"
	        		+ "WHERE U.EMAIL = ?";
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
	    	  stmt.setString(1,filtro.getUsuarioAtividade().getUsuario().getEmail());
	    	  ResultSet rs = stmt.executeQuery();	          
	          while(rs.next()){
	             int id  = rs.getInt(UsuarioResposta.ID);
	             String resposta = rs.getString(UsuarioResposta.RESPOSTA);
	             Integer atividadeId = rs.getInt("UA."+UsuarioAtividade.ATIVIDADE_COMPLEMENTO);
             
	             retorno.add(new UsuarioResposta(id,new UsuarioAtividade(atividadeId,null,null,null,null,null,null),resposta));
	          }
	          rs.close();
	          stmt.close();
	          conn.close();
	          	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return retorno;
	   }
	
}
