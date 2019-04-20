/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import view.Funcoes;

/**
 *
 * @author Leonardo
 */
public class MesaDao {
    private Connection conn;
    
    private Funcoes a ;
    public MesaDao() {
        this.conn = new ConectionFactory().getConection();
    }
    
    
    public  boolean checkInstance(int i)
    {
        String sql = "select * from mesa where numerodamesa=?";
        
        try {
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, i);
            
            ResultSet rs = stm.executeQuery();
            
            if (rs.next()){
            
                if (rs.getBoolean("status")){
                    return true;
                }else{
                    return false;
                }
            }
            else{
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MesaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException();
        } 
    }
    
    
    public void mudarStatusDaMesa(boolean valor, int nrmesa){
        String sql = "update mesa set status =? where numerodamesa=?";
        a = new Funcoes();
        try {
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(2, nrmesa);
            stm.setBoolean(1, valor);
            stm.execute();
           // a.alertaConfirmacao("Sucesso", null, "Sucesso", Alert.AlertType.INFORMATION);
         
        } catch (SQLException ex) {
            Logger.getLogger(MesaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException();
        } 
    }
    
}
