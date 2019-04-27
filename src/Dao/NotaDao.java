/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Nota;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import view.Funcoes;

/**
 *
 * @author leoro
 */
public class NotaDao {
    
     private Connection conn;

    public NotaDao() {
        this.conn = new ConectionFactory().getConection();
    }
    
    public void insert(Nota n){
        String sql = "Insert into Nota(nr_conta,valorpago) values(?,?)";
       // System.out.println(n.getConta() + " asdasd " + n.getValor());
        try {
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, n.getConta());
            stm.setDouble(2, n.getValor());
            stm.execute();
            
            new Funcoes().alertaConfirmacao("Sucesso", null, "Sucesso", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            Logger.getLogger(ContaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public ArrayList<Nota> getAlll(String data){
        String sql = "select\n" +
                    "c.nrmesa,\n" +
                    "c.numerodaconta,\n" +
                    "c.conta_tipo,\n" +
                    "n.valorpago\n" +
                    "from Nota as n\n" +
                    "inner join Conta as c on c.numerodaconta = n.nr_conta\n" +
                    "where \n" +
		    "c.dtaabertura = ?"+
                    "group by \n" +
                    "c.nrmesa,\n" +
                    "c.numerodaconta,\n" +
                    "c.conta_tipo,\n" +
                    "n.valorpago";
        ArrayList<Nota> list = new ArrayList<>();
        try {
            PreparedStatement stm = this.conn.prepareStatement(sql);
            
            stm.setString(1, data);
            
            ResultSet rs = stm.executeQuery();
            
           
            while(rs.next()){
                Nota nota = new Nota();
                
                nota.setConta(rs.getInt("numerodaconta"));
                nota.setContatipo(rs.getString("conta_tipo"));
                nota.setMesa(rs.getInt("nrmesa"));
                nota.setValor(rs.getDouble("valorpago"));
                list.add(nota);
                
            }
            
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException();
        }
    }
}
