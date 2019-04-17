package sample;

import Funcion.Function;

import java.text.DecimalFormat;
//Metodo para la resolucion del Punto Fijo

public class MetodoPuntoFijo {
    private double X0,X1;
    private double errorPermitido;
    private String procedimiento;
    Function funcion, funcionDespejada;
    DecimalFormat formato;

    public MetodoPuntoFijo(){
        procedimiento = String.format("%-10s\t%-15s\t%-15s\t%-15s", new Object[] {"Num","X0","X1","Error"});
        formato = new DecimalFormat("0.000000");
    }

    public MetodoPuntoFijo(Function funcion){
        this.funcion = funcion;
        procedimiento = String.format("%-10s\t%-15s\t%-15s\t%-15s", new Object[] {"Num","X0","X1","Error"});
        formato = new DecimalFormat("0.000000");
    }


    public void PuntoFijo(){
        double error = Double.POSITIVE_INFINITY;
        int i = 1;

        try{
            X1 = funcionDespejada.evaluateFrom(X0);

            error = Math.abs((X1 - X0) / X1)*100.0D;

            agregarProcedimiento(i,X0,X1,error);
            do{
                X0 = X1;
                X1 = funcionDespejada.evaluateFrom(X0);

                error = Math.abs((X1 - X0) / X1)*100.0D;
                i++;

                agregarProcedimiento(i,X0,X1,error);

            }while(error > errorPermitido);
        }catch (Exception localException) {}
    }

    /**
     *
     * @param i es el numero de intervalos que tiene la solucion
     * @param X0 es el valor de Xo al que se le va agregando
     * @param X1 es el valor de Xi que se va evaluando dependiendo de la funcion despejada
     * @param error imprime el error que va teniendo el metodo
     */
    public void agregarProcedimiento(int i, double X0, double X1, double error){
        String xo = formato.format(X0);
        String xi = formato.format(X1);
        String e = formato.format(error);

        String formatoProcedimiento = String.format("%-10s\t%-15s\t%-15s\t%-15s", new Object[]{Integer.valueOf(i), xo, xi, e});

        procedimiento += ("\n" + formatoProcedimiento);
    }

    public void setX0(double x0) {
        X0 = x0;
    }

    public void setErrorPermitido(double errorPermitido) {
        this.errorPermitido = errorPermitido;
    }

    public void setFuncion(Function funcion) {
        this.funcion = funcion;
    }

    public void setFuncionDespejada(Function funcionDespejada) {
        this.funcionDespejada = funcionDespejada;
    }

    public double getX1() {
        return X1;
    }

    public String getProcedimiento() {
        return procedimiento;
    }

    public String getFormato(double root) {
        return formato.format(root);
    }

    public void reiniciarProcedimiento(){
        procedimiento = String.format("%-10s\t%-15s\t%-15s\t%-15s", new Object[] {"Num","X0","X1","Error"});

    }
}
