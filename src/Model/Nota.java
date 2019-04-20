package Model;

import java.util.ArrayList;

public class Nota{
 
    private double valor;
    private int conta;
    private String contatipo;
    private int mesa;
    

    public Nota(String n1, String nrconta) {
        try {
            
            n1 = n1.replace(",", ".");
            this.valor = Double.parseDouble(n1);
            this.conta = Integer.parseInt(nrconta);
        } catch (Exception e) {
        }
    }

    public Nota() {
    }

    public String getContatipo() {
        return contatipo;
    }

    public void setContatipo(String contatipo) {
        this.contatipo = contatipo;
    }

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }
    
    

    
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getConta() {
        return conta;
    }

    public void setConta(int conta) {
        this.conta = conta;
    }
    
    
}