/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import Dao.CaixaDao;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Leonardo
 */
public class Restaurante extends Application {
 
    private static Stage stage;
    private static Scene telaPrincipal;
    private static Scene FXMLDocument;
    private static Scene telaCaixaNaoAberto;
    
    
    @Override
    public void start(Stage stage) {
       try{
        stage.initStyle(StageStyle.DECORATED);
        Parent FXMDocument = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Parent telaprincipal = FXMLLoader.load(getClass().getResource("TelaPricipal.fxml"));
        Parent tcaixa = FXMLLoader.load(getClass().getResource("AlertaCaixaNAberto.fxml"));
        
        Restaurante.telaCaixaNaoAberto = new Scene(tcaixa);
        Restaurante.stage = stage;
        Restaurante.FXMLDocument = new Scene(FXMDocument);
        Restaurante.telaPrincipal = new Scene(telaprincipal);
        
        stage.setScene(Restaurante.FXMLDocument);
       
      
        stage.show();
        stage.setResizable(false);
       }catch(IOException e){
           String rs = e.getMessage() + "\n" + "Hora exata do erro : " + new Funcoes().getDataAtual() + "\n";
            File logs = new File("src/logsDeErros/logsDeErros.txt");
            try {
                BufferedWriter fileWrite = new BufferedWriter(new FileWriter(logs));
                fileWrite.write(rs);
                fileWrite.close();
            } catch (IOException ex1) {
                Logger.getLogger(CaixaDao.class.getName()).log(Level.SEVERE, null, ex1);
            }
       }
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        Restaurante.stage = stage;
    }
    
    public static void  changeScreen(String scr){
        switch(scr){
            case "princ":Restaurante.getStage().setScene(Restaurante.telaPrincipal);break;
            case "login":Restaurante.getStage().setScene(Restaurante.FXMLDocument);break;
        }
    }
    /*Essa funcao ira checar se o caixa esta abero na data atual 
    se estiver aberta ele deixa o sistema abrir normalmente porem exibe um aviso informando que o caixa ja esta 
    aberto
    
    se o caixa nao estiver aberto ele exibe uma tela perguntando se o usuario deseja abrir o caixa 
    se ele escolher nao o sistema ira fechar 
    se ele escolher sim o caixa vai ser aberto
    
    */
    public static void checkCaixaAbert(){
       CaixaDao caixa = new CaixaDao();
       Date data = new Date();
       SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
       
       
       System.out.println(df.format(data));
       String result = caixa.searchCaixa(df.format(data));
        
       if (result.equals("0")){
          Restaurante.getStage().setScene(telaCaixaNaoAberto);    
       }
       else if(result.equals("exit") && !result.equals("0")){
          new Funcoes().alertaConfirmacao("Caixa já foi fechado", null, "Apenas informando que o caixa do dia " + df.format(data) +
                  " já foi fechado, apenas lembrando que não é possivel abrir dois caixas em um unico dia", Alert.AlertType.INFORMATION);

           
       }else{
            Restaurante.changeScreen("princ");
          
            Restaurante.getStage().setResizable(true);
            Restaurante.getStage().setMaximized(true);
       }
      
       
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
       
    }
    
}
