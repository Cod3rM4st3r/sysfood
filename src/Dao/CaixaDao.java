    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Conta;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import view.Funcoes;
/**
 *
 * @author Leonardo
 */
public class CaixaDao {

    private Connection conn;
    
    public CaixaDao() {
        this.conn = new ConectionFactory().getConection();
    }
    
    public boolean insert(String date, String date2){
        String sql = "insert into caixa(caixa_dt_abertura,caixa_dt_fechamento) values(?,?)";
        
        try {
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, date);
            stm.setString(2, date2);
            boolean bo = stm.execute();
            
            new Funcoes().alertaConfirmacao("Caixa aberto com sucesso", null, "Caixa aberto com sucesso", Alert.AlertType.INFORMATION);
            return bo;
        } catch (SQLException ex) {
            Logger.getLogger(CaixaDao.class.getName()).log(Level.SEVERE, null, ex);
            String rs = ex.getMessage() + "\n" + "Hora exata do erro : " + new Funcoes().getDataAtual() + "\n";
            File logs = new File("src/logsDeErros/logsDeErros.txt");
            try {
                BufferedWriter fileWrite = new BufferedWriter(new FileWriter(logs));
                fileWrite.write(rs);
                fileWrite.close();
            } catch (IOException ex1) {
                Logger.getLogger(CaixaDao.class.getName()).log(Level.SEVERE, null, ex1);
            }
            throw new RuntimeException();
        }
    }
    
    public String searchCaixa(String date){
        String sql  = "select * from caixa where CAIXA_DT_ABERTURA = ?";
       
        try {
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1,date);
            ResultSet rs = stm.executeQuery();
          
            
            if(rs.next()){
                if ( rs.getString("CAIXA_DT_fechamento") != null){
                    return "exit";
                }else{
                    return rs.getString("CAIXA_DT_ABERTURA");
                }
               
                
                
            }else{
                
                return "0";
            }
            
        } catch (SQLException ex) {           
            Logger.getLogger(CaixaDao.class.getName()).log(Level.SEVERE, null, ex);
            String rs = ex.getMessage() + "\n" + "Hora exata do erro : " + new Funcoes().getDataAtual();
            File logs = new File("src/logsDeErros/logsDeErros.txt");
            try {
                BufferedWriter fileWrite = new BufferedWriter(new FileWriter(logs));
                fileWrite.write(rs);
                fileWrite.close();
            } catch (IOException ex1) {
                Logger.getLogger(CaixaDao.class.getName()).log(Level.SEVERE, null, ex1);
            }
            
            throw new RuntimeException();
        }
        
       
    }
    
    public void update(String data){
        String sql = "update caixa set caixa_dt_fechamento = current_date where caixa_dt_abertura ='"+data+"'";
        try {
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CaixaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
   
    
}
