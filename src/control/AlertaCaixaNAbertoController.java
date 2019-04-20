/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Dao.CaixaDao;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import view.Funcoes;
import view.Restaurante;

/**
 * FXML Controller class
 *
 * @author Leonardo
 */
public class AlertaCaixaNAbertoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private DatePicker dtPicker;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        Restaurante.getStage().setResizable(false);
    }    
    
    @FXML private void btnAbrirCaixa(){
        LocalDate dta = dtPicker.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String data = formatter.format(dta);
        CaixaDao dt = new  CaixaDao();
        boolean res = dt.insert(data, null);
        
        if (!res){
            System.out.println(res);
            Restaurante.getStage().setResizable(true);    
            Restaurante.changeScreen("princ");
            Restaurante.getStage().setMaximized(true);
            Restaurante.getStage().setResizable(false); 
        }else{
            
            new Funcoes().alertaConfirmacao("Algo ocorreu", null, "Ocorreu algum erro ao tentar abrir o sistema contate o administrador ", Alert.AlertType.ERROR);
        }
    }
    
    @FXML private void btnNAbrirCaixa(){
        System.exit(0);
    }
    
}
