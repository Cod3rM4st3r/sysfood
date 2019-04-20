/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Adm;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leonardo
 */
public class AdmDao {

    private Connection conn;
    
    public AdmDao() {
        this.conn = new ConectionFactory().getConection();
    }
    
    public String  getAdm(Adm adm){
        String sql = "Select * from adm where adm_nome=?";
        
        try {
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, adm.getNome());
            ResultSet rs = stm.executeQuery();
            rs.next();

            String nome = rs.getString("adm_nome");
            String pass = rs.getString("adm_pass");
            
            if (adm.getNome().equals(nome) && adm.getSenha().equals(pass)){
                return "1";
            }else{
                return "0";
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AdmDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException();
        }
        
    }
    
    
    
}
