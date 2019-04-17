package sample;

import Funcion.Function;

import java.text.DecimalFormat;

public class MetodoSecante {
    private double XiMenosUno, Xi, XiMasUno, errorPermitido;
    private String procedimiento;
    Function function;
    DecimalFormat formato;

    public MetodoSecante(){
        procedimiento = String.format("%-10s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s", new Object[] {"Num","Xi-1","Xi","f(Xi-1)","f(X)","Xi+1","Error"});
        formato = new DecimalFormat("0.000000");
    }

    public MetodoSecante(Function function){
        this.function = function;
        procedimiento = String.format("%-10s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s", new Object[] {"Num","Xi-1","Xi","f(Xi-1)","f(X)","Xi+1","Error"});
        formato = new DecimalFormat("0.000000");
    }

    public void Secante(){
        double error = Double.POSITIVE_INFINITY;
        int i = 1;

        try{
            double funcionMenosUno = function.evaluateFrom(XiMenosUno);
            double funcionXi = function.evaluateFrom(Xi);
            XiMasUno = ((Xi)-(((funcionXi)*((XiMenosUno-Xi)))/((funcionMenosUno)-funcionXi)));
            error = Math.abs((XiMasUno-Xi)/XiMasUno)*100.0D;

            agregarPrccedimiento(i,XiMenosUno,Xi,funcionMenosUno,funcionXi,XiMasUno,error);

            do{
                XiMenosUno = Xi;
                Xi = XiMasUno;

                funcionMenosUno = function.evaluateFrom(XiMenosUno);
                funcionXi = function.evaluateFrom(Xi);
                XiMasUno = ((Xi)-(((funcionXi)*((XiMenosUno-Xi)))/((funcionMenosUno)-funcionXi)));
                error = Math.abs((XiMasUno-Xi)/XiMasUno)*100.0D;
                i++;

                agregarPrccedimiento(i,XiMenosUno,Xi,funcionMenosUno,funcionXi,XiMasUno,error);

            }while(error > errorPermitido);
        } catch (Exception localException) {}

    }

    public void agregarPrccedimiento(int i, double XiMenosUno, double Xi, double funcionXiMenosUno, double funcionXi, double XiMasUno, double error){
        String ximenosuno = formato.format(XiMenosUno);
        String xi = formato.format(Xi);
        String funximenosuno = formato.format(funcionXiMenosUno);
        String funxi = formato.format(funcionXi);
        String ximasuno = formato.format(XiMasUno);
        String e = formato.format(error);

        String formatoProcedimiento = String.format("%-10s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s", new Object[]{Integer.valueOf(i),ximenosuno,xi, funximenosuno, funxi, ximasuno,e});

        procedimiento += ("\n" + formatoProcedimiento);
    }

    public void setXiMenosUno(double xiMenosUno) {
        XiMenosUno = xiMenosUno;
    }

    public void setXi(double xi) {
        Xi = xi;
    }

    public void setErrorPermitido(double errorPermitido) {
        this.errorPermitido = errorPermitido;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public double getXiMasUno() {
        return XiMasUno;
    }

    public String getProcedimiento() {
        return procedimiento;
    }

    public String getFormato(double root) {
        return formato.format(root);
    }

    public void reiniciarProcedimiento(){
        procedimiento = String.format("%-10s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s", new Object[] {"Num","Xi-1","Xi","f(Xi-1)","f(X)","Xi+1","Error"});
    }
}
