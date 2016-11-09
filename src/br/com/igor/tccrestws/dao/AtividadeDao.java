package br.com.igor.tccrestws.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import br.com.igor.tccrestws.ConexaoMySQL;
import br.com.igor.tccrestws.User;
import br.com.igor.tccrestws.entity.Atividade;
import br.com.igor.tccrestws.entity.Perfil;
import br.com.igor.tccrestws.entity.Usuario;
import br.com.igor.tccrestws.filtro.FiltroAtividade;
import br.com.igor.tccrestws.filtro.FiltroUsuario;

public class AtividadeDao {

	private ConexaoMySQL conMySQL = new ConexaoMySQL();
	
	public Atividade selectAtividade(FiltroAtividade filtro){
	      Atividade atividade = null;
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	          sql = "select * from atividade where nome like ?";
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
	    	  stmt.setString(1,filtro.getNome());
	    	  ResultSet rs = stmt.executeQuery();	          
	          while(rs.next()){
	        	 Integer id  = rs.getInt(Atividade.ID);
	             String nome = rs.getString(Atividade.NOME);
	             Perfil perfil = (Perfil)rs.getObject(Atividade.PERFIL);
	             atividade = new Atividade(id, nome,perfil);
	          }
	          rs.close();
	          stmt.close();
	          conn.close();
	          	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return atividade;
	   }

	public List<Atividade> selectAllAtividade(FiltroAtividade filtro){
	      Atividade atividade = null;
	      List<Atividade> list = new ArrayList<>();
	      try {
	    	  Connection conn = conMySQL.getConexaoMySQL();
	    	  String sql;
	          sql = "select * from atividade";
	    	  PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
	    	  ResultSet rs = stmt.executeQuery();	          
	          while(rs.next()){
	        	 Integer id  = rs.getInt(Atividade.ID);
	             String nome = rs.getString(Atividade.NOME);
	             Perfil perfil = (Perfil)rs.getObject(Atividade.PERFIL);
	             atividade = new Atividade(id, nome,perfil);
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
