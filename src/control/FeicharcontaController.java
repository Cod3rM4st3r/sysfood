/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Dao.ContaDao;
import Dao.PedidoDao;
import Model.Imprimir;
import Model.Pedidos;
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
 *
 * @author leoro
 */
public class FeicharcontaController implements Initializable{
    
    @FXML private TextField nrContaField;
    @FXML private TextField nrMesaField;
    @FXML private TextField nrValorField;
    @FXML private TextField nrValorPField;
    @FXML private ComboBox nrComboTp;
    @FXML private ComboBox vpago;
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.nrComboTp.getItems().addAll("Cartão", "A vista");
        
    }
    
    
    public void pegarDados( String nrMesa, double valor, Stage a){
        this.nrMesaField.setText(nrMesa);
        DecimalFormat fmt = new DecimalFormat("0.00");
        this.nrValorField.setText(fmt.format(valor));
        this.stage = a;
        
        
        int nconta= new ContaDao().getNumeroDaoConta(Integer.parseInt(nrMesa));
        
        this.nrContaField.setText(String.valueOf(nconta));
        
        double percent;
        percent = valor + valor * 10 /100;
         //= valor;
        this.nrValorPField.setText(fmt.format(percent));
        this.vpago.getItems().addAll(fmt.format(valor), fmt.format(percent));
        
    }
    
    @FXML private void imprimir(){
        PedidoDao pdao = new PedidoDao();
        
        ArrayList<Pedidos> ped = new ArrayList<Pedidos>();
        int nmesa = Integer.parseInt(this.nrMesaField.getText());
        
        ped = pdao.getAll(nmesa);
        
        Imprimir imp = new Imprimir();
        
        double valor,valorp;
        
        this.nrValorField.setText(this.nrValorField.getText().replace(",", "."));
        this.nrValorPField.setText(this.nrValorPField.getText().replace(",", "."));
        valor = Double.parseDouble(this.nrValorField.getText());
        valorp = valor * 10 /100;
        
        if (imp.montarTextoPedido(ped, valor, valorp).equals("1")){
            ContaDao cdao = new ContaDao();
            int nconta = cdao.getNumeroDaoConta(nmesa);
            cdao.update(nconta, nmesa);
        }
        fecharTela();
    }
    @FXML private void cancelar(){
        fecharTela();
    }
    
    private void fecharTela(){
        stage.close();
    }
    
}
