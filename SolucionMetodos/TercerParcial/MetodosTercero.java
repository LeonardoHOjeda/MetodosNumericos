package SolucionMetodos.TercerParcial;

import Funcion.Function;

import java.text.DecimalFormat;

public class MetodosTercero {
    Function funcion1,funcion2, derivada1,derivada2,derivada3,derivada4;
    private double Xi,Yi;
    private double errorPermitido;
    DecimalFormat formato;
    private String procedimiento;

    public MetodosTercero(){
        procedimiento = String.format("%-10s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s",
                new Object[]{"Num","Xi","Yi","F1","F2","Der. Parc. F1/x","Der. Parc. F1/y","Der. Parc. F2/x","Der. Parc. F2/y","Δx","Δy","Xi+1","Yi+1","Error(x)","Error(y)"});
        formato =new DecimalFormat("0.000000");
    }

    public MetodosTercero(Function f1, Function f2){
        this.funcion1 = f1;
        this.funcion2 = f2;
        procedimiento = String.format("%-10s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s",
                new Object[]{"Num","Xi","Yi","F1","F2","Der. Parc. F1/x","Der. Parc. F1/y","Der. Parc. F2/x","Der. Parc. F2/y","Δx","Δy","Xi+1","Yi+1","Error(x)","Error(y)"});
        formato =new DecimalFormat("0.000000");
    }

    public void NewtonMulti(){
        double error = Double.POSITIVE_INFINITY;
        int i = 1;

        try{
            double fun1 = funcion1.evaluateFrom(Xi);
            double fun2 = funcion2.evaluateFrom(Yi);
            double der1 = derivada1.evaluateFrom(Xi);
            double der2 = derivada2.evaluateFrom(Yi);
            double der3 = derivada3.evaluateFrom(Xi);
            double der4 = derivada4.evaluateFrom(Yi);
            double deltaX = ((-((fun1)*(der4))+((fun2)*(der2)))/((der1)*(der4)-(der3)*(der2)));
            double deltaY = 0;
        }catch (Exception localException) {}

    }
}
