/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Dao.AdmDao;
import Model.Adm;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import view.Restaurante;

/**
 *
 * @author Leonardo
 */
public class FXMLDocumentController implements Initializable{
    
    @FXML
    private Label label;
    @FXML
    private Label mensagemDeErro;
    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField pfUsuario;
    @FXML
    private AnchorPane pane;
    
    
    @FXML
    private void entrar() throws Exception{
        String nomeUsuario = this.tfUsuario.getText();
        String senhaUsuario = this.pfUsuario.getText();
        
        Adm a = new Adm();
        a.setNome(nomeUsuario);
        a.setSenha(senhaUsuario);
        AdmDao dao = new AdmDao();
        //vericica se o  campo esta nulo 
        //se estiver exibe um erro 
        if (!a.getNome().equals("")){
            String result = dao.getAdm(a);
            //verifica se o login esta certo 
            if (result.equals("1")){
                this.mensagemDeErro.setVisible(true);
                this.mensagemDeErro.setText("Acesso Garantido");
                this.mensagemDeErro.styleProperty().setValue("-fx-background-color: green"); 
                
                Restaurante.checkCaixaAbert();
                



            }else{
                //this.mensagemDeErro.styleProperty().setValue("-fx-background-color: red");
                this.mensagemDeErro.setText("Usuário ou senha estão incorretos!");
                this.mensagemDeErro.setVisible(true);
            }
        }
        else{
                //this.mensagemDeErro.styleProperty().setValue("-fx-background-color: red");
                this.mensagemDeErro.setText("Os campos estão em branco!");
                this.mensagemDeErro.setVisible(true);
            }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
