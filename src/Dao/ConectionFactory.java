/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Leonardo
 */
public class ConectionFactory {
    
    public Connection getConection(){
        try{
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("jdbc:postgresql://localhost/LeozinhoBotekos", "postgres", "261814");
        }catch(SQLException e){
            System.out.println("=======================================\nErro========\n" + e.getMessage());
            throw new RuntimeException();
        }catch(ClassNotFoundException ee){
            throw new RuntimeException();
        }
    }
    
}
