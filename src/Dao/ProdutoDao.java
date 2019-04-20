/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;



import Model.Produto;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import view.Funcoes;

/**
 *
 * @author Leonardo
 */
public class ProdutoDao {

    private Connection conn;
    
    public ProdutoDao() {
        this.conn = new ConectionFactory().getConection();
    }
    
    public void insert(Produto p){
        String sql = "insert into Produto(prod_nome,prod_preco,cate_cod) values(?,?,?)";
        Funcoes a;
        try {
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, p.getNomeDoProduto());
            stm.setDouble(2, Double.parseDouble(p.getPrecoUnitario()));
            stm.setInt(3, p.getCat_cod());
            stm.execute();
            a = new Funcoes();
            a.alertaConfirmacao("Sucesso", "Produto foi cadastrado com sucesso", null,Alert.AlertType.CONFIRMATION);
        } catch (SQLException ex) {
            a = new Funcoes();
            a.alertaConfirmacao("Algo ocorreu", "Nao foi possivel cadastrar\n", ex.getMessage(),Alert.AlertType.ERROR);
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            String rs = ex.getMessage() + "\n" + "Hora exata do erro : " + new Funcoes().getDataAtual();
            File logs = new File("src/logsDeErros/logsDeErros.txt");
            try {
                BufferedWriter fileWrite = new BufferedWriter(new FileWriter(logs));
                fileWrite.write(rs);
                fileWrite.close();
            } catch (IOException ex1) {
                Logger.getLogger(CaixaDao.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
    }
    
    
    public void update(Produto p){
        String sql = "update Produto set prod_nome = ?,prod_preco = ?,cate_cod = ? Where prod_cod=?";
        Funcoes a;
        try {
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(4, p.getCodigoDoProduto());
            stm.setString(1, p.getNomeDoProduto());
            stm.setDouble(2, Double.parseDouble(p.getPrecoUnitario()));
            stm.setInt(3, p.getCat_cod());
            stm.execute();
            a = new Funcoes();
            a.alertaConfirmacao("Sucesso", "Produto foi alterado com sucesso", null,Alert.AlertType.CONFIRMATION);
        } catch (SQLException ex) {
            a = new Funcoes();
            a.alertaConfirmacao("Algo ocorreu", "Nao foi possivel alterar\n", ex.getMessage(),Alert.AlertType.ERROR);
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            String rs = ex.getMessage() + "\n" + "Hora exata do erro : " + new Funcoes().getDataAtual();
            File logs = new File("src/logsDeErros/logsDeErros.txt");
            try {
                BufferedWriter fileWrite = new BufferedWriter(new FileWriter(logs));
                fileWrite.write(rs);
                fileWrite.close();
            } catch (IOException ex1) {
                Logger.getLogger(CaixaDao.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    public void delete(Produto p){
        String sql  = "Delete from Produto where prod_cod=?";
        Funcoes a;
        try {
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, p.getCodigoDoProduto());
            stm.execute();
            a = new Funcoes();
            a.alertaConfirmacao("Sucesso", "Produto foi deletado com sucesso", null,Alert.AlertType.CONFIRMATION);
        } catch (SQLException ex) {
            a = new Funcoes();
            a.alertaConfirmacao("Algo ocorreu", "Nao foi possivel deletar\n", ex.getMessage(),Alert.AlertType.ERROR);
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            String rs = ex.getMessage() + "\n" + "Hora exata do erro : " + new Funcoes().getDataAtual();
            File logs = new File("src/logsDeErros/logsDeErros.txt");
            try {
                BufferedWriter fileWrite = new BufferedWriter(new FileWriter(logs));
                fileWrite.write(rs);
                fileWrite.close();
            } catch (IOException ex1) {
                Logger.getLogger(CaixaDao.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    public Produto getInstance(int p){
        String sql = "Select * from Produto where prod_cod=?";
        Produto produto = new Produto();
        try {
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, p);
            ResultSet rs = stm.executeQuery();
            rs.next();
            
            produto.setCodigoDoProduto(p);
            produto.setNomeDoProduto(rs.getString("prod_nome"));
            produto.setCat_cod(rs.getInt("cate_cod"));
            produto.setPrecoUnitario(String.valueOf(rs.getFloat("prod_preco")));
            
            return produto;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public  List<Produto> getListaDeProdutos(){
        String sql = "SELECT * FROM Produto";
        List<Produto> listProd;
        
         try {
            PreparedStatement stm = this.conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            listProd = new ArrayList<>();
            while(rs.next()){
                
                Produto prd = new Produto();
                prd.setCodigoDoProduto(rs.getInt("prod_cod"));
                prd.setNomeDoProduto(rs.getString("prod_nome"));
                DecimalFormat fmt = new DecimalFormat("0.00");
                String a = fmt.format(rs.getFloat("prod_preco"));
                
                prd.setPrecoUnitario(a.replace(",", "."));
                
                prd.setCat_cod(rs.getInt("cate_cod"));
                
               
                listProd.add(prd);
            }
            
            return listProd;
            
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public List<Produto> getListaByCategoria(String categoria){
        ArrayList<Produto> produtolista;
        String sql = "Select prod_cod, prod_nome  from Produto as f inner join Categoria as cc on f.cate_cod = cc.cate_cod where cc.cate_nome = ?";
        
        try {
            produtolista = new ArrayList<Produto>();
            PreparedStatement stm = this.conn.prepareStatement(sql);   
            stm.setString(1, categoria);
            ResultSet rs =  stm.executeQuery();
            while (rs.next()){
                Produto p = new Produto();
                
                p.setCodigoDoProduto(rs.getInt("prod_cod"));
                p.setNomeDoProduto(rs.getString("prod_nome"));
               
                produtolista.add(p);
            }
            return produtolista;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException();
        }
    }
    
    
    public void insertCate(String name) throws SQLException{
        String sql = "insert into categoria(cate_nome) values(?)";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, name);
        stm.execute();
        
    }
}
