/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Dao.NotaDao;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.ArrayList;
import javaapplication1.PrinterService;
import view.Funcoes;

/**
 *
 * @author leoro
 */
public class Imprimir {
    
    private String cabecalho;
    private String footer;
    private String corte;
    private int tamanho;
    private byte[] cortarpapel;
    byte[] comecoNegrito = {0x1B, 0x45};  
    byte[] fimNegrito = {0x1B, 0x46};  

    public Imprimir() {
        cabecalho = " Boteko do leozinho\n";
        //cabecalho += "________________________________________________\n";
        corte = "\n\n\n\n\n\n";
        footer = "\n_______________________________________________" + corte;
        cortarpapel = new byte[] { 0x1d, 'V', 1   };
        tamanho = 48;
    }
    
    
    public String imprimirPapel(String texto){
         PrinterService printerService = new PrinterService();
         printerService.printString("services[0].getName()", cabecalho + texto + footer);
         printerService.printBytes("services[0].getName()", cortarpapel);
        
         return "1";
    }
    
    public void imprimirRelatorioDoDia(){
        NotaDao ndao = new NotaDao();
        ArrayList<Nota> nota = ndao.getAlll(new Funcoes().getData());
        String texto = "";
       
        texto +=" Data :" + new Funcoes().getDataH()+"\n";
        texto += " Relatorio de vendas do dia\n";
        texto += "________________________________________________\n";
        
         double valor = 0;
        for (int c =0; c< nota.size(); c++){
            texto += " Conta : "+nota.get(c).getConta() + "\n Tipo : " + nota.get(c).getContatipo() + " \n Numero da Mesa : " +nota.get(c).getMesa() + " \n Valor Pago: " + nota.get(c).getValor() + "\n\n";
            valor += nota.get(c).getValor();
        }
        
        DecimalFormat df = new DecimalFormat("0.00");
        
        texto += "________________________________________________\n";
        texto += " Valor Total vendido :"+df.format(valor) + "\n";
        //texto += "________________________________________________\n";
        
        imprimirPapel(texto);
    }
   public String montarTextoPedido(ArrayList<Pedidos> ped, double valor, double valorp){
       
       String texto = "";
       texto +=" Data :" + new Funcoes().getDataH()+"\t\t";
       
       if (ped.get(0).getNrmesa() == 200){
           texto += "\n Balcao" +"\n";
       }else{
           texto += "\n Mesa :" + ped.get(0).getNrmesa() + "\n";
       }

       texto += " Conta :" +ped.get(0).getNrConta() + "\n\n";
       
       texto += " Pedidos________________________________________\n";
       int c = 0;
       
       System.out.println(ped.size());
       while(c < ped.size()){
           
           
           texto += complementeProduto(" Qtd: " + ped.get(c).getQuantidade() + " " + ped.get(c).getProdNome()  ) +"R$:"+ ped.get(c).getTotal()+"\n";
           
           c++;
       } 
       valor = 0;
       valorp = 0;
       for (int cc = 0; cc < ped.size(); cc++){
           valor += ped.get(cc).getTotal();
       }
       valorp = valor * 10 /100;
       
       DecimalFormat fmt = new DecimalFormat("0.00");
       texto += " _______________________________________________\n";
       texto += " Total : R$:" + fmt.format(valor) + "\n";
       texto += " 10% Opcional :R$:" + fmt.format(valorp) +"\n";
       valor +=valorp;
       
        
       texto += " Total : R$:" + fmt.format(valor); 
       
       
       return imprimirPapel(texto);
   }
   private String complementeProduto(String as){
       as =  Normalizer.normalize(as, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
     //  as = as.replace("", as)
       while(as.length() < 36){
           as+=".";
       }
       
       return as;
   }
    
    
    
}
