/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;
import Model.Categoria;
import Model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leonardo
 */
public class CategoriaDao {

    private Connection conn;
    
    public CategoriaDao() {
        this.conn = new ConectionFactory().getConection();
    }

   
    public void insert(Categoria cat) {
        
    }

  
    public void delete(Categoria cat) {
        
    }

    
    public void update(Categoria cat) {
        
    }

    public List<Categoria> getAll() {
        String sql = "select * from Categoria";
        List<Categoria> lis = new ArrayList<>();
        
        
        try {
            PreparedStatement stm = this.conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            
            while(rs.next()){
               Categoria a = new Categoria();
               a.setCodigoCategoria(rs.getInt("cate_cod"));
               a.setNomeCategoria(rs.getString("cate_nome"));
               
               lis.add(a);
            }
            
            return lis;
            
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException();
        }
        
    }
    
    
}
