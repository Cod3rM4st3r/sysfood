/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Conta;

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
 * @author Leonardo
 */
public class ContaDao {
    
    private Connection conn;

    public ContaDao() {
        this.conn = new ConectionFactory().getConection();
    }
    
    public void insert(Conta conta){
        String sql = "insert into Conta(numerodaconta,dtaabertura,conta_tipo,conta_status,nrmesa) values(?,?,?,?,?)";
        try {
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, conta.getNumeroDaConta());
            stm.setString(2,conta.getDataAbertura());
            stm.setString(3, conta.getTipoConta());
            stm.setString(4, "Nao Pago");
            stm.setInt(5, conta.getNumeroDaMesa());
            stm.execute();
            MesaDao dao = new MesaDao();
            dao.mudarStatusDaMesa(true, conta.getNumeroDaMesa());
            new Funcoes().alertaConfirmacao("Sucesso", null, "Sucesso", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            Logger.getLogger(ContaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public ArrayList<Double> getValores(int numero){
        String sql = "select \n" +
                     "c.numerodaconta,\n" +
                     "sum(cast(pro.prod_preco as float) * cast(p.ped_qtde as float)) as total,\n" +
                     "sum (cast(pro.prod_preco as float) * cast(p.ped_qtde as float) + cast(pro.prod_preco as float) * cast(p.ped_qtde as float) * 10 /100)	 as percente									 \n" +
                     "\n" +
                     "from Conta  as c\n" +
                     "inner join pedido as p on p.ped_nrconta = c.numerodaconta\n" +
                     "inner join produto as pro on pro.prod_cod = p.ped_prod_cod\n" +
                     "where c.conta_status = 'Pago' and c.numerodaconta = ?\n" +
                     "group by \n" +
                     "c.numerodaconta";
        ArrayList<Double> list = new ArrayList<>();
        
        try {
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, numero);
            
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
            
            list.add(rs.getDouble("total"));
            list.add(rs.getDouble("percente"));
            
            return list;
            }else{
                new Funcoes().alertaConfirmacao("Atenção", "", "Já foi dado baixa nessa conta, ou essa conta ainda não foi paga", Alert.AlertType.WARNING);
                return list;
            }
            //new Funcoes().alertaConfirmacao("Sucesso", null, "Sucesso", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            Logger.getLogger(ContaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException();
        }
    }
    
    public void inserBalc(Conta conta){
         String sql = "insert into Conta(numerodaconta,dtaabertura,conta_tipo,conta_status,nrmesa) values(?,?,?,?,?)";
        try {
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, conta.getNumeroDaConta());
            stm.setString(2,conta.getDataAbertura());
            stm.setString(3, conta.getTipoConta());
            stm.setString(4, "Nao Pago");
            stm.setInt(5, conta.getNumeroDaMesa());
            stm.execute();
            
            MesaDao dao = new MesaDao();
            dao.mudarStatusDaMesa(true, conta.getNumeroDaMesa());
            new Funcoes().alertaConfirmacao("Sucesso", null, "Sucesso", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            Logger.getLogger(ContaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void update (int nrConta,int nrmesa){
        String sql = "Update Conta set conta_status ='Pago' where numerodaconta = ?";
        
        MesaDao m = new MesaDao();
        m.mudarStatusDaMesa(false, nrmesa);
        
        try {
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, nrConta);
            
            stm.execute();
            new Funcoes().alertaConfirmacao("Sucesso", null, "Sucesso", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            Logger.getLogger(ContaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Baixa (int nrConta) {
        String sql = "Update Conta set conta_status ='Baixa' where numerodaconta = ?";
        
        try {
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, nrConta);
            
            stm.execute();
            //new Funcoes().alertaConfirmacao("Sucesso", null, "Sucesso", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
          ex.printStackTrace();
        }
    }
    
    public int getNumeroDaoConta(int mesa){
        String sql = "select numerodaconta from conta as cont inner join mesa as mes on mes.numerodamesa = cont.nrmesa where mes.numerodamesa = ? and  cont.conta_status = 'Nao Pago'";
        
        try {
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, mesa);
            ResultSet rs = stm.executeQuery();
            rs.next();
            return rs.getInt("numerodaconta");
        } catch (SQLException ex) {
            Logger.getLogger(ContaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException();
        }
    }
    
    
     public ArrayList<Conta> getAllPg() throws SQLException{
        String sql ="Select * from conta where conta_status = 'Pago'";
        
        PreparedStatement stm =  this.conn.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        
        ArrayList<Conta> c = new ArrayList<>();
        
        
        while (rs.next()){
            Conta conta = new Conta();
            
            conta.setNumeroDaConta(rs.getInt("numerodaconta"));
            conta.setNumeroDaMesa(rs.getInt("nrmesa"));
            conta.setStatus(rs.getString("conta_status"));
            c.add(conta);
        }
        return c;
    }
    
}
