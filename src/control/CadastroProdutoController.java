/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Dao.CategoriaDao;
import Dao.ProdutoDao;
import Model.Categoria;
import Model.Produto;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javax.swing.event.DocumentEvent;
import view.Funcoes;

/**
 * FXML Controller class
 *
 * @author Leonardo
 */
public class CadastroProdutoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private TextField nomeProduto;
    @FXML private TextField precoProduto;
    @FXML private ComboBox categoria;
    @FXML private Label mensagemDeAlerta;
    private List<Categoria> cat;
    
    ///Aba Editar
    @FXML private TableView<Produto> tabela;
    @FXML private TableColumn<Produto, Boolean> selecionarCol;
    @FXML  private TableColumn<Produto, Integer> codigoCol;
    @FXML private TableColumn<Produto,String> nomeCol;
    @FXML private TableColumn<Produto,String> cateCol;
    @FXML private TableColumn<Produto, String> precoCol;
    private List<Produto> list;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cat = new CategoriaDao().getAll();
        for(int i=0; i<cat.size();i++){
            categoria.getItems().add( this.cat.get(i).getCodigoCategoria() + " - " +this.cat.get(i).getNomeCategoria());
        }
       // preencherTabelaAbaEditar();
    }    
    
    public void limpacampo(){
        this.nomeProduto.setText("");
        this.precoProduto.setText("");
    }
    
    @FXML private void cadastrarProduto(){
        
        if (nomeProduto.getText().equals("") || precoProduto.getText().equals("")){
         /*   this.mensagemDeAlerta.styleProperty().setValue("-fx-background-color: red"); 
            this.mensagemDeAlerta.setText("Existem campos em branco!");*/
            if (nomeProduto.getText().equals("")){
                this.nomeProduto.styleProperty().setValue("-fx-border-color:red");
            }
            if (precoProduto.getText().equals("")){
                this.precoProduto.styleProperty().setValue("-fx-border-color:red");
            }
            if (categoria.getPromptText().equals("Categoria")){
                this.categoria.styleProperty().setValue("-fx-border-color:red");
                
            }
        }
        else{
            this.nomeProduto.styleProperty().setValue("-fx-border-color:green");
            this.precoProduto.styleProperty().setValue("-fx-border-color:green");
            this.categoria.styleProperty().setValue("-fx-border-color:green");
            
            String spl = String.valueOf(this.categoria.getValue());
            String[] a = spl.split(" ");
            int cod = Integer.parseInt(a[0]);
            
            Produto p = new Produto();
            p.setCat_cod(cod);
            p.setNomeDoProduto(nomeProduto.getText());
            p.setPrecoUnitario(precoProduto.getText());
            ProdutoDao pdao = new ProdutoDao();
            pdao.insert(p);
            limpacampo();
        }
    }
    
    
    private void preencherTabelaAbaEditar(){
        List<Produto> a = new ProdutoDao().getListaDeProdutos();
        this.list = a;
        tabela.setEditable(true);
        selecionarCol.setCellValueFactory(new PropertyValueFactory<>("selecionado"));
        codigoCol.setCellValueFactory(new PropertyValueFactory<>("codigoDoProduto"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nomeDoProduto"));
        cateCol.setCellValueFactory(new PropertyValueFactory<>("cat_cod"));
        precoCol.setCellValueFactory(new PropertyValueFactory<>("precoUnitario"));
               
        
        //codigoCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nomeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nomeCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Produto, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Produto, String> event) {
                ((Produto) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())).setNomeDoProduto(event.getNewValue());
            }
        });
        precoCol.setCellFactory(TextFieldTableCell.forTableColumn());
        precoCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Produto, String>>() {
            public void handle(TableColumn.CellEditEvent<Produto, String> event) {
                ((Produto) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())).setPrecoUnitario(event.getNewValue());
            }
        });
        
        for(int i=0; i<a.size();i++){
            tabela.getItems().add(a.get(i));
        }
    }
    
    public void test(){
        
    }
    
    @FXML private void abaEditarBtnAppMudancas(){
        
        List<Produto> listaEditar = new ArrayList<>();
        
        for(Produto p : list)
        {
            ProdutoDao pdao = new ProdutoDao();
            if (p.isSelecionado().isSelected())
            {
                listaEditar.add(p);
                pdao.update(p);
            }
            pdao = null;
        }
    }
    
    @FXML private void abaEditarBtnExluir(){
        List<Produto> listaEditar = new ArrayList<>();
        
        for(Produto p : list)
        {
            ProdutoDao pdao = new ProdutoDao();
            if (p.isSelecionado().isSelected())
            {
                listaEditar.add(p);
                pdao.delete(p);
                tabela.getItems().remove(p);
                tabela.refresh();
            }
            pdao = null;
        }
    }
    
    @FXML private void abaEditarAtualizarTabela(){
        tabela.refresh();
        tabela.getItems().clear();
        preencherTabelaAbaEditar();
        tabela.refresh();
    }
    
    
}
