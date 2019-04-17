package sample;

import Funcion.Function;

import java.text.DecimalFormat;

public class MetodoNewtonRhapson {
    private double Xi, errorPermitido,xiPlusOne;
    Function funcion, funcionDerivada;
    private String procedimiento;
    DecimalFormat formato;

    public MetodoNewtonRhapson(){
        procedimiento = String.format("%-10s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s", new Object[] {"Num","Xi","f(Xi)","f'(Xi)","Xi+1","Error"});
        formato = new DecimalFormat("0.000000");
    }

    public MetodoNewtonRhapson(Function funcion){
        this.funcion = funcion;
        procedimiento = String.format("%-10s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s", new Object[] {"Num","Xi","f(Xi)","f'(Xi)","Xi+1","Error"});
        formato = new DecimalFormat("0.000000");
    }

    public void NewtonRhapson(){
        double error = Double.POSITIVE_INFINITY;
        int i = 1;

        try{
            double funcionNor = funcion.evaluateFrom(Xi);
            double funcionDer = funcionDerivada.evaluateFrom(Xi);
            xiPlusOne = ((Xi)-((funcionNor)/funcionDer));
            error = Math.abs((xiPlusOne - Xi) / xiPlusOne)*100.D;

            agregarProcedimiento(i,Xi,funcionNor,funcionDer,xiPlusOne,error);

            do{
                Xi = xiPlusOne;
                funcionNor = funcion.evaluateFrom(Xi);
                funcionDer = funcionDerivada.evaluateFrom(Xi);
                xiPlusOne = ((Xi)-((funcionNor)/funcionDer));
                error = Math.abs((xiPlusOne - Xi) / xiPlusOne)*100.D;
                i++;

                agregarProcedimiento(i,Xi,funcionNor,funcionDer,xiPlusOne,error);

            }while (error > errorPermitido);
        } catch (Exception localException) {}
    }

    public void agregarProcedimiento(int i,double Xi, double funXi, double funDerXi, double xiOne, double error){
        String xi = formato.format(Xi);
        String funxi = formato.format(funXi);
        String funderxi = formato.format(funDerXi);
        String xione = formato.format(xiOne);
        String e = formato.format(error);

        String formatoProcedimiento = String.format("%-10s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s", new Object[]{Integer.valueOf(i),xi,funxi,funderxi,xione,e});

        procedimiento += ("\n" + formatoProcedimiento);
    }

    public void setXi(double xi) {
        Xi = xi;
    }

    public void setErrorPermitido(double errorPermitido) {
        this.errorPermitido = errorPermitido;
    }

    public void setFuncion(Function funcion) {
        this.funcion = funcion;
    }

    public void setFuncionDerivada(Function funcionDerivada) {
        this.funcionDerivada = funcionDerivada;
    }

    public double getXiPlusOne() {
        return xiPlusOne;
    }

    public String getProcedimiento() {
        return procedimiento;
    }

    public String getFormato(double root) {
        return formato.format(root);
    }

    public void reiniciarProcedimiento(){
        procedimiento = String.format("%-10s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s", new Object[] {"Num","Xi","f(Xi)","f'(Xi)","Xi+1","Error"});
    }
}
