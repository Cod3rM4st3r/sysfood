/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Model.Imprimir;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.Restaurante;

/**
 *
 * @author Leonardo
 */
public class TelaPrincipalController implements Initializable{

    @FXML
    private AnchorPane painelAncora;
    @FXML
    private MenuBar barraMenu;
    
    @FXML private BorderPane borderpane;
    
    
    @FXML
    private void loadUi(String ui){
        Parent root = null;
        
        try {
            root = FXMLLoader.load(Restaurante.class.getResource(ui));
            
        } catch (IOException ex) {
            Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        borderpane.setCenter(root);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    @FXML
    private void abrirCaixa(ActionEvent event){
        
    }
    
    
    @FXML private void abrirNotas(){
        try {
            Stage a =  new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Restaurante.class.getResource("Notas.fxml"));
            Parent FXMDocument = loader.load();
            
            NotasController control = loader.getController();
            control.pegarStage(a);
            Scene cena = new Scene(FXMDocument);
            
            a.setScene(cena);
            a.show();
        } catch (IOException ex) {
            Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void feicharCaixa(ActionEvent event){
        loadUi("FecharCaixa.fxml");
        
    }
    
    
    @FXML
    private void retirada(ActionEvent event){
        
    }
    
    @FXML private void abrirConta(){
        loadUi("ControlContas.fxml");
    }
    
    @FXML private void produto(){
        loadUi("CadastroProduto.fxml");
    }
    
    @FXML private void categoria(){
        loadUi("CadastroDeCategoria.fxml");
    }
    
    @FXML private void funcionarios(){
        
    }
    
    @FXML private void relatorioVendM(){
        
    }
    
    @FXML private void relatorioVendA(){
        
    }
    
    @FXML private void relatorioVendD(){
        Imprimir imp = new Imprimir();
        imp.imprimirRelatorioDoDia();
    }
    
    @FXML private void relatorioEstoque(){
        
    }
    
    @FXML private void configuracao(){
        
    }
    
    @FXML
    private void sair(){
         Alert alert = new Alert(AlertType.WARNING);
       alert.setTitle("Alerta");
       alert.setHeaderText("");
       alert.setContentText("Antes de sair e bom checar se está tudo ok, para evitar transtornos!!");
       alert.showAndWait();
       Restaurante.changeScreen("login");
       Restaurante.getStage().setResizable(false);
    }
    
    
}
