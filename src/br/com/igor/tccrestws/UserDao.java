package br.com.igor.tccrestws;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
   public User getAllUsers(){
      List<User> userList = null;
      User user = null;
      try {
    	  ConexaoMySQL conMySQL = new ConexaoMySQL();
    	  Connection conn = conMySQL.getConexaoMySQL();
    	  System.out.println("Creating statement...");
    	  Statement stmt = (Statement) conn.createStatement();
    	  String sql;
          sql = "select * from tcc.user";
          ResultSet rs = stmt.executeQuery(sql);
          
          //STEP 5: Extract data from result set
          while(rs.next()){
             //Retrieve by column name
             int id  = rs.getInt("id");
             int idade = rs.getInt("idade");
             String nome = rs.getString("nome");
             String profissao = rs.getString("profissao");
             user = new User(id, nome, profissao);
             userList.add(user);
          }
          //STEP 6: Clean-up environment
          rs.close();
          stmt.close();
          conn.close();
          
          userList = new ArrayList<User>();
         
          saveUserList(userList);		

      } catch (Exception e) {
         e.printStackTrace();
      }
      return user;
   }

   private void saveUserList(List<User> userList){
      try {
    	  
      } catch (Exception e) {
         e.printStackTrace();
      } 
   }   
}