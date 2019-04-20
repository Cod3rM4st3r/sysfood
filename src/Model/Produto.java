package Model;


import Model.Categoria;
import javafx.scene.control.CheckBox;

public class Produto{
  private CheckBox selecionado;  
  private int codigoDoProduto;
  private String nomeDoProduto;
  private String precoUnitario;
  private Categoria categoria;
  private int cat_cod;
  /*Getters and Setters*/

    public Produto(boolean selecionado, int codigoDoProduto, String nomeDoProduto, String precoUnitario, int cat_cod) {
        
        this.codigoDoProduto = codigoDoProduto;
        this.nomeDoProduto = nomeDoProduto;
        this.precoUnitario = precoUnitario;
        this.cat_cod = cat_cod;
    }

    public int getCat_cod() {
        return cat_cod;
    }

    public void setCat_cod(int cat_cod) {
        this.cat_cod = cat_cod;
    }

    public CheckBox isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(CheckBox selecionado) {
        this.selecionado = selecionado;
    }
  
    public Produto() {
        selecionado = new CheckBox();
    }

    public int getCodigoDoProduto() {
        return codigoDoProduto;
    }

    public void setCodigoDoProduto(int codigoDoProduto) {
        this.codigoDoProduto = codigoDoProduto;
    }

    public String getNomeDoProduto() {
        return nomeDoProduto;
    }

    public void setNomeDoProduto(String nomeDoProduto) {
        this.nomeDoProduto = nomeDoProduto;
    }

    public String getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(String precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
  
    public String toString(){
        String str = "";
        
        str+= "Cod : " + this.getCodigoDoProduto() + "\n";
        str += "Nome : " + this.getNomeDoProduto() + "\n";
        str+= "Preco : " + this.getPrecoUnitario() + "\n";
        str+= "Categoria: " + this.getCategoria() + "\n";
        str+= "Checkbox: " + this.isSelecionado().isSelected() + "\n";
        return str;
    }

}
