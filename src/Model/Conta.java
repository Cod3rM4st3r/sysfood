package Model;

import javafx.scene.control.CheckBox;

public class Conta{
  private int numeroDaConta;
  private String dataAbertura;
  private String tipoConta;
  private String status;
  private int numeroDaMesa;
  private CheckBox selecionado; 
  /*Getters and Setters*/

    public CheckBox isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(CheckBox selecionado) {
        this.selecionado = selecionado;
    }

    public int getNumeroDaMesa() {
        return numeroDaMesa;
    }

    public void setNumeroDaMesa(int numeroDaMesa) {
        this.numeroDaMesa = numeroDaMesa;
    }

    public int getNumeroDaConta() {
        return numeroDaConta;
    }

    public void setNumeroDaConta(int numeroDaConta) {
        this.numeroDaConta = numeroDaConta;
    }

    public String getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(String dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Conta() {
        this.selecionado = new CheckBox();
    }

    
  
}
