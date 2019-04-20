/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Dao.CaixaDao;
import Model.Imprimir;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import view.Funcoes;


/**
 * FXML Controller class
 *
 * @author Leonardo
 */
public class FecharCaixaController implements Initializable{

 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML private void fechar(){
        Imprimir imp = new Imprimir();
        imp.imprimirRelatorioDoDia();
        CaixaDao xs = new CaixaDao();
        xs.update(new Funcoes().getData());
        System.exit(0);
    }
    
    
    
}
