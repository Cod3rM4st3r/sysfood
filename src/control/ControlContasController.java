/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Dao.CategoriaDao;
import Dao.ContaDao;
import Dao.MesaDao;
import Dao.PedidoDao;
import Dao.ProdutoDao;
import Model.Categoria;
import Model.Conta;
import Model.Pedidos;
import Model.Produto;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import view.Funcoes;
import view.Restaurante;


/**
 * FXML Controller class
 *
 * @author Leonardo
 */
public class ControlContasController implements Initializable {

    /**
     * Initializes the controller class.
     */
//    Aba Abrir conta 
    @FXML private TextField  numeroDaConta;
    @FXML private TextField  numeroDaMesa;
    @FXML private TextField  statusDaMesa;
    @FXML private ComboBox   tipoDeConta;
    @FXML private Button btnAbrir;
    private Funcoes func;

// Aba Tirar Pedido
    @FXML private TextField tpedidoNumerodaMesa;
    @FXML private TextField tpedidoQuantidade;
    @FXML private ComboBox categoria;
    @FXML private ComboBox produto;
     private List<Categoria> cat;
     private List<Produto> list;
    
    
//    aba Acompanhar pedido
    @FXML private TextField numeroDaMesaAcomp;
    @FXML private TableView listaAcompanhamento;
    @FXML private TableColumn<Pedidos,Integer> nrconta;
    @FXML private TableColumn<Pedidos,Integer> nrmesa;
    @FXML private TableColumn<Pedidos,String> nomeProduto;
    @FXML private TableColumn<Pedidos, Double> preco;
    @FXML private TableColumn<Pedidos,Integer> quantidade;
    @FXML private TableColumn<Pedidos,Double> subtotal;
    @FXML private Label totall;
    private double tottall;
//    Aba fechar conta 
    
    
    
    
    ///Aba Acompanhamento de conta
    @FXML private TableView camp;
    @FXML private TableColumn<Conta,Integer> cconta;
    @FXML private TableColumn<Conta,Integer> cmesa;
    @FXML private TableColumn<Conta,String> cstatus;
    @FXML private TableColumn<Conta,Boolean> colselecionado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.tipoDeConta.setPromptText("Tipo de Conta");
        this.tipoDeConta.getItems().addAll("Mesa", "Balcao");
        this.func = new Funcoes();
        btnAbrir.setDisable(true);
        this.listaAcompanhamento.getItems().clear();
        repopulateCombos();
        
    }    
    
    /*
    Procura no banco de dados algum registro que contem o numero da mesa que foi especificada
    se houver a mesa esta ocupada 
    se nao ele está livre
    
    */
    @FXML private void checarMesa(){
        int numeroMesa = Integer.parseInt(this.numeroDaMesa.getText());
        MesaDao dao = new MesaDao();
        boolean bo = dao.checkInstance(numeroMesa);
        
        
        if (bo){
            this.statusDaMesa.setText("Mesa Ocupada"); 
            btnAbrir.setDisable(true);
        }else{
            this.statusDaMesa.setText("Mesa Livre");
            btnAbrir.setDisable(false);
        }
    }
    
    
    private boolean checarMesaB(int numerodaMesa){
        MesaDao dao = new MesaDao();
        boolean bo = dao.checkInstance(numerodaMesa);
        
        if (bo){
            return true;
        }else{
            return false;
        }
    }
    
    @FXML private void abrirConta(){
        String tipo = tipoDeConta.getValue().toString();
        String status =  statusDaMesa.getText();
        
        if (tipo.equals("Balcao")){
            LocalDate dta = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
       
            String nrcont = func.gerarNumeroConta();
            this.numeroDaConta.setText(nrcont);
            String data = formatter.format(dta);
            Conta conta = new Conta();
            conta.setNumeroDaConta(Integer.parseInt(nrcont));
            conta.setDataAbertura(data);
            conta.setTipoConta(tipo);
            conta.setStatus(status);
            conta.setNumeroDaMesa(200);
            ContaDao dao = new ContaDao();
            dao.inserBalc(conta);
        }else{
            numeroDaMesa.setDisable(false);
            LocalDate dta = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            String nrcont = func.gerarNumeroConta();
            this.numeroDaConta.setText(nrcont);
            String data = formatter.format(dta);
            Conta conta = new Conta();
            conta.setNumeroDaConta(Integer.parseInt(nrcont));
            conta.setDataAbertura(data);
            conta.setTipoConta(tipo);
            conta.setStatus(status);
            conta.setNumeroDaMesa(Integer.parseInt(numeroDaMesa.getText()));

            ContaDao dao = new ContaDao();
            dao.insert(conta);
        }
    }
    
    
    @FXML private void desabilitaCampoMesa(){
       String tipo = tipoDeConta.getValue().toString();
        if (tipo.equals("Balcao")){
            numeroDaMesa.setDisable(true);
            btnAbrir.setDisable(false);
            numeroDaMesa.setText("200");
        }
        else{
            btnAbrir.setDisable(false);
            numeroDaMesa.setDisable(false);
            numeroDaMesa.setText("");
        }
    }
    
    @FXML private void registrarPedido()
    {
      int nmesa = Integer.parseInt(tpedidoNumerodaMesa.getText());
      int npedido = Integer.parseInt(tpedidoQuantidade.getText());
      String categoria = this.categoria.getValue().toString();
      String[] prosel = this.produto.getValue().toString().split("-");
      boolean abert;
      
      abert = checarMesaB(nmesa);
      
      if (abert){
          
          int produto =Integer.parseInt(prosel[0]);
          int nrconta = getNumeroContaByMesaNumero(nmesa);
          Pedidos p = new Pedidos();
          PedidoDao sql = new PedidoDao();
          
          p.setNrConta(nrconta);
          p.setQuantidade(npedido);
          p.setCodProduto(produto);
          sql.insert(p);
          
          clean();
      }else{
          
      }
    }
    
    private void clean(){
        tpedidoNumerodaMesa.setText("");
        tpedidoQuantidade.setText("");
        categoria.getItems().removeAll(categoria.getItems());
        produto.getItems().removeAll(produto.getItems());
        repopulateCombos();
    }
    
    
    private void repopulateCombos(){
        this.cat = new CategoriaDao().getAll();
        for(int i=0; i<cat.size();i++){
            categoria.getItems().add( this.cat.get(i).getCodigoCategoria() + " -" +this.cat.get(i).getNomeCategoria());
        }
        this.list = new ProdutoDao().getListaDeProdutos();
        for(int i=0; i< list.size();i++){
            produto.getItems().add(this.list.get(i).getCodigoDoProduto() + "-" + this.list.get(i).getNomeDoProduto());
        }
    }
    
    @FXML public void atualizaComboProduto(){
        String categoria =  "";
       // 
        String[] cate = this.categoria.getValue().toString().split("-");
        categoria = cate[1];
       
         for (Produto p:list){
             produto.getItems().clear();

         }
         this.list = new ProdutoDao().getListaByCategoria(categoria);
         System.out.println(this.list.size());
         for (int p=0; p< list.size();p++){
             produto.getItems().add(this.list.get(p).getCodigoDoProduto() + "- " + this.list.get(p).getNomeDoProduto());
             
         }
       
       
    }
    
    private int getNumeroContaByMesaNumero(int nrmesa){
        int nconta= new ContaDao().getNumeroDaoConta(nrmesa);
        
        return nconta;
    }
    
    
    @FXML private void listContas(){
        cconta.setCellValueFactory(new PropertyValueFactory<>("numeroDaConta"));
        cmesa.setCellValueFactory(new PropertyValueFactory<>("numeroDaMesa"));
        cstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colselecionado.setCellValueFactory(new PropertyValueFactory<>("selecionado"));
        try {
            ArrayList<Conta> conta = new ContaDao().getAllPg();
            
            this.camp.getItems().clear();
            
            for(Conta c: conta){
                this.camp.getItems().add(c);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ControlContasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    @FXML private void listarPedidos()
    {   
        
        ArrayList<Pedidos> pedidos;
        nrconta.setCellValueFactory(new PropertyValueFactory<>("nrConta"));
        nrmesa.setCellValueFactory(new PropertyValueFactory<>("nrmesa"));
        nomeProduto.setCellValueFactory(new PropertyValueFactory<>("prodNome"));
        preco.setCellValueFactory(new PropertyValueFactory<>("prodPreco"));
        quantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        subtotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        
        int nrmesa = Integer.parseInt(this.numeroDaMesaAcomp.getText().toString());
        boolean check = checarMesaB(nrmesa);
        double total = 0;
        limparTabela();
        
        if (check){
            PedidoDao sql = new PedidoDao();
            pedidos = sql.getAll(nrmesa);
            
            for (int p=0;p<pedidos.size();p++){
                
            
                total += pedidos.get(p).getTotal();
                this.listaAcompanhamento.getItems().add(pedidos.get(p));
                
            }
            DecimalFormat fmt = new DecimalFormat("0.00");
            this.totall.setText("Total  :  ");
            this.totall.setText(totall.getText() + fmt.format(total));
            tottall = total;
        }else{
            
        }
    }
    
    @FXML private void fecharConta()
    {
       /*Ira verificar se a conta/mesa esta aberta, se sim 
        ira gerar e imprimir a nota e fechar a conta 
        */ 
        Stage a =  new Stage();
        try {
            //Parent FXMDocument = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Restaurante.class.getResource("FeixarConta.fxml"));
            //System.out.println(Restaurante.class.getResource("FeixarConta.fxml"));
            Parent FXMDocument = loader.load();
            
            FeicharcontaController control = loader.getController();
            
            control.pegarDados(this.numeroDaMesaAcomp.getText(), tottall,a);
            Scene cena = new Scene(FXMDocument);
            a.setScene(cena);
            a.show();
        } catch (IOException ex) {
            Logger.getLogger(ControlContasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML private void baixarComandasSelecionadas(){
        try {
            ArrayList<Conta> conta = new ContaDao().getAllPg();
            ContaDao c = new ContaDao();
            for(Conta con : conta){
                if(con.isSelecionado().isSelected()){
                    c.Baixa(con.getNumeroDaConta());
                }else{
                    System.out.println("control.Cont");
                }
            }
            
        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }
    
    private void limparTabela(){
        this.listaAcompanhamento.refresh();
        this.listaAcompanhamento.getItems().clear();
        this.listaAcompanhamento.refresh();
    }
    
}
