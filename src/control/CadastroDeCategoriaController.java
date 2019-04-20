/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Dao.ProdutoDao;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import view.Funcoes;

/**
 * FXML Controller class
 *
 * @author Leonardo
 */
public class CadastroDeCategoriaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private TextField nameCat;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    
    @FXML private void cadastrar(){
        String nome = nameCat.getText();
        
        ProdutoDao p = new ProdutoDao();
        
        try {
            p.insertCate(nome);
            new Funcoes().alertaConfirmacao("Sucesso", "sucesso", "Cadastrado com sucesso", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            new Funcoes().alertaConfirmacao("Erro", "", "Erro : "+ ex.getMessage(), Alert.AlertType.ERROR);
        }
        
    }
    
}
