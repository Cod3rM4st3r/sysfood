/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Dao.ContaDao;
import Dao.NotaDao;
import Model.Nota;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author leoro
 */
public class NotasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Stage stage;
    @FXML private TextField nrConta;
    @FXML private ComboBox valor;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML private void sair(){
        stage.close();
    }
    
    @FXML private void listar(){
        try{
            if (this.valor.getItems().size() > 0){
                this.valor.getItems().clear();
            }
            int numero = Integer.parseInt(this.nrConta.getText());
            ContaDao cdao = new ContaDao();
            ArrayList<Double> list = cdao.getValores(numero);
            DecimalFormat df = new DecimalFormat("0.00");
            
            this.valor.getItems().addAll(df.format(list.get(0)), df.format(list.get(1)));
            
        }catch(Exception e){
            
        }
    }
    
    
    @FXML private void confirmar(){
        String valor = this.valor.getValue().toString();
       // System.out.println(valor + " " + this.nrConta.getText());
        
        ContaDao cdao = new ContaDao();
        
        Nota n = new Nota(valor, this.nrConta.getText());
        NotaDao ndao = new NotaDao();
        cdao.Baixa(n.getConta());
        
        ndao.insert(n);
        
        
        
    }
    public void pegarStage(Stage a ){
        this.stage = a;
    }
}
