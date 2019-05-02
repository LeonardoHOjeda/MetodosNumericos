package SolucionMetodos.TercerParcial;

import java.util.List;
import herramientas.escenas;

public class OperacionesFuncionales {

    private List<InsercionValores> lista;
    private double x=0, y=0,xiyi=0,xcua=0,mediaX=0,mediaY=0,St=0,Sr=0;
    private String procedimiento;

    public OperacionesFuncionales(List<InsercionValores> lista){
        procedimiento = String.format("%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s",
                new Object[] {"X","Y","XiYi","X²","Media X","Media Y","St","Sr"});
        this.lista = lista;
        for(InsercionValores xy: lista){
            x += xy.getX();
            y += xy.getY();
            xiyi += (xy.getX() * xy.getY());
            xcua += (xy.getX() * xy.getX());
        }

        mediaX = x/lista.size();
        mediaY = y / lista.size();

        agregarProcedimiento(x,y,xiyi,xcua,mediaX,mediaY,St,Sr);
    }

    private void agregarProcedimiento(double x, double y, double xiyi, double xcua, double mediaX, double mediaY, double St, double Sr){
        String aX = escenas.numeroReducido(x);
        String aY = escenas.numeroReducido(y);
        String aF1 = escenas.numeroReducido(xiyi);
        String aF2 = escenas.numeroReducido(xcua);
        String aDerF1x = escenas.numeroReducido(mediaX);
        String aDerF1y = escenas.numeroReducido(mediaY);
        String aDerF2x = escenas.numeroReducido(St);
        String aDerF2y = escenas.numeroReducido(Sr);

        String formatoProcedimiento = String.format("%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s",
                new Object[] {aX,aY,aF1,aF2,aDerF1x,aDerF1y,aDerF2x,aDerF2y});

        procedimiento +=  ("\n"+formatoProcedimiento);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getXiyi() {
        return xiyi;
    }

    public double getXcua() {
        return xcua;
    }

    public double getMediaX() {
        return mediaX;
    }

    public double getMediaY() {
        return mediaY;
    }

    public double getSt() {
        return St;
    }

    public double getSr() {
        return Sr;
    }

    public String getProcedimiento() {
        return procedimiento;
    }

    public double a1(){
        int n = lista.size();
        return (((n*xiyi)-(x*y)) / ((n*xcua) - (double)Math.pow(x,2)));
    }

    public double a0(){
        return (mediaY - (a1()*mediaX));
    }

    public double coeficiente(){
        return (double)Math.sqrt((St-Sr)/St);
    }

    public void sumatoriaST(){
        St = 0;
        for(InsercionValores xy : lista){
            St += xy.getST();
        }
    }

    public void sumatoriaSR(){
        Sr = 0;
        for(InsercionValores xy: lista){
            Sr += xy.getSR();
        }
    }
}
