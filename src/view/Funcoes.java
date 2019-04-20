/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

/**
 *
 * @author Leonardo
 */
public class Funcoes {
    
    public void alertaConfirmacao(String titulo, String cabecalho, String conteudo,Alert.AlertType s){
        Alert a = new Alert(s);
        
        a.setTitle(titulo);
        a.setHeaderText(cabecalho);
        a.setContentText(conteudo);
        a.showAndWait();
        
        Alert teste = new Alert(Alert.AlertType.INFORMATION);
    }
    
    public String getDataAtual(){
        Date a = new Date();
        String time = String.valueOf(a);
        
        return time;
    }
    
    public String getData(){
        Date a = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        
        
        return format.format(a);
    }
    
     public String getDataH(){
        Date a = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        
        
        return format.format(a);
    }
    
    public String gerarNumeroConta(){
        Date a = new Date();
        String numeroConta  = String.valueOf(a.getDay() + "" + "" +a.getHours() +  "" + a.getMonth()  + "" + a.getYear()  + "" + a.getSeconds());
        return numeroConta;
    }
    
    public void somDeInclusao(){
        
    }
    
    public void somDeErro(){
        
    }
}
