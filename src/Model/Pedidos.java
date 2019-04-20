/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Leonardo
 */
public class Pedidos {

        private int ped_id; 
        private int nrConta;
         private int codProduto;
        private int quantidade;
        private double total;
        private int nrmesa;
        private String prodNome;
        private double prodPreco;

    public int getNrmesa() {
        return nrmesa;
    }

    public void setNrmesa(int nrmesa) {
        this.nrmesa = nrmesa;
    }

    public String getProdNome() {
        return prodNome;
    }

    public void setProdNome(String prodNome) {
        this.prodNome = prodNome;
    }

    public double getProdPreco() {
        return prodPreco;
    }

    public void setProdPreco(double prodPreco) {
        this.prodPreco = prodPreco;
    }
       

    public int getPed_id() {
        return ped_id;
    }

    public void setPed_id(int ped_id) {
        this.ped_id = ped_id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getNrConta() {
        return nrConta;
    }

    public void setNrConta(int nrConta) {
        this.nrConta = nrConta;
    }

    public int getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(int codProduto) {
        this.codProduto = codProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
        
    
}
