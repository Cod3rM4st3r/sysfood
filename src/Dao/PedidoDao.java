/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import java.sql.Connection;
import Model.Pedidos;
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
public class PedidoDao {
    private Connection conn;
    
    public PedidoDao(){
        this.conn = new ConectionFactory().getConection();
    }
    
    public void insert(Pedidos p ){
        String sql = "insert into pedido(ped_nrconta, ped_prod_cod,ped_qtde) values(?,?,?)";
        
        try {
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, p.getNrConta());
            stm.setInt(2, p.getCodProduto());
            stm.setInt(3, p.getQuantidade());
            
            stm.execute();
            new Funcoes().alertaConfirmacao("Sucesso", null, "O pedido foi incluido com sucesso", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void delete(Pedidos p){
        String sql = "delete from pedido where ped_id=?";
    }
   
    
    public ArrayList<Pedidos> getAll(int nrmesa){
        ArrayList<Pedidos> pedidos = new ArrayList<>();
        String sql = "select cast(s.prod_preco as float) * cast(s.ped_qtde as float) as Sub_total,* from (select \n" +
                     "cont.numerodaconta,\n" +
                     "cont.nrmesa,\n" +
                     "prod.prod_nome,\n" +
                     "prod.prod_preco,\n" +
                     "sum(ped.ped_qtde) as ped_qtde\n" +
                     "from \n" +
                     "Pedido as ped inner join produto as prod on ped.ped_prod_cod = prod.prod_cod\n" +
                     "inner join conta as cont on cont.numerodaconta = ped.ped_nrconta\n" +
                     "inner join mesa on mesa.numerodamesa = cont.nrmesa\n" +
                     "where cont.nrmesa = ? and cont.dtaabertura = ? and cont.conta_status = 'Nao Pago' and mesa.status = true "
                + "group by \n" +
                "cont.numerodaconta,\n" +
                "cont.nrmesa,\n" +
                "prod.prod_nome,\n" +
                "prod.prod_preco,\n" +
                "conta_status\n" +
                "order by\n" +
                "prod.prod_preco desc) as s";
        try {
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, nrmesa);
            stm.setString(2, new Funcoes().getData());
            
            
            ResultSet rs = stm.executeQuery();
            
            System.out.println(new Funcoes().getData() + ' ' + nrmesa);
            while(rs.next()){
                Pedidos ped = new Pedidos();
                ped.setNrConta(rs.getInt("numerodaconta"));
                ped.setNrmesa(rs.getInt("nrmesa"));
                ped.setProdNome(rs.getString("prod_nome"));
                ped.setProdPreco(rs.getDouble("prod_preco"));
                ped.setQuantidade(rs.getInt("ped_qtde"));
                ped.setTotal(rs.getDouble("Sub_total"));
                pedidos.add(ped);
            }
            
            return pedidos;
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException();
        }
    }
    
}
