package sample;

import Funcion.Function;

import java.text.DecimalFormat;

public class Metodosprimero {
    private double puntoA;
    private double puntoB;
    private double errorPermitido;
    private double Xr;
    private String procedimiento;
    Function funcion;
    DecimalFormat formato;

    public Metodosprimero(){
        procedimiento = String.format("%-10s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s", new Object[] {"Num","a","b","F(a)","F(b)","Xr","F(Xr)","Error"});
        formato = new DecimalFormat("0.000000");
    }

    public Metodosprimero (Function funcion){
        this.funcion = funcion;
        procedimiento = String.format("%-10s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s", new Object[] {"Num","a","b","F(a)","F(b)","Xr","F(Xr)","Error"});
        formato = new DecimalFormat("0.000000");
    }

    public void Biseccion(){
        double error = Double.POSITIVE_INFINITY;
        int i = 1;
        double anteriorXr =0.0D;

        try{
            double funcionA =funcion.evaluateFrom(puntoA);
            double funcionB = funcion.evaluateFrom(puntoB);
            Xr =((puntoA + puntoB)/ 2.0D);
            double funcionXr =funcion.evaluateFrom(Xr);
            agregarProcedimiento(i, puntoA, puntoB, funcionA, funcionB, Xr, funcionXr, errorPermitido);
            do{
                if(funcionA * funcionXr == 0.0D){
                    break;
                } else if (funcionA * funcionXr > 0.0D){
                    puntoA = Xr;
                } else if (funcionA * funcionXr < 0.0D){
                    puntoB = Xr;
                }
                anteriorXr = Xr;

                funcionA =funcion.evaluateFrom(puntoA);
                funcionB = funcion.evaluateFrom(puntoB);
                Xr =((puntoA + puntoB)/ 2.0D);
                funcionXr =funcion.evaluateFrom(Xr);

                error = Math.abs((Xr - anteriorXr) / Xr)*100.0D;
                i++;

                agregarProcedimiento(i, puntoA, puntoB, funcionA, funcionB, Xr, funcionXr, error);

            }while (error > errorPermitido);
        }catch (Exception localException) {}
    }

    public void ReglaFalsa(){
        double error = Double.POSITIVE_INFINITY;
        int i = 1;
        double anteriorXr = 0.0D;

        try{
            double funcionA = funcion.evaluateFrom(puntoA);
            double funcionB = funcion.evaluateFrom(puntoB);
            Xr = (puntoB - funcionB * (puntoA - puntoB) / (funcionA - funcionB));
            double funcionXr = funcion.evaluateFrom(Xr);
            agregarProcedimiento(i, puntoA, puntoB, funcionA, funcionB, Xr, funcionXr, errorPermitido);
            do{
                if(funcionA * funcionXr == 0.0D){
                    break;
                }else if ( funcionA * funcionXr > 0.0D){
                    puntoA = Xr;
                } else if (funcionA * funcionXr < 0.0D){
                    puntoB = Xr;
                }
                anteriorXr = Xr;

                funcionA = funcion.evaluateFrom(puntoA);
                funcionB = funcion.evaluateFrom(puntoB);
                Xr = (puntoB - funcionB * (puntoA - puntoB) / (funcionA - funcionB));
                funcionXr = funcion.evaluateFrom(Xr);

                error = Math.abs((Xr - anteriorXr) / Xr)*100.0D;
                i++;

                agregarProcedimiento(i, puntoA, puntoB, funcionA, funcionB, Xr, funcionXr, error);


            }while (error > errorPermitido);
        }catch (Exception localException){}
    }

    public void agregarProcedimiento(int i, double puntoA, double puntoB, double funcionA, double funcionB, double Xr, double funcionXr, double errorPermitido){
        String a =formato.format(puntoA);
        String b = formato.format(puntoB);
        String funA = formato.format(funcionA);
        String funB = formato.format(funcionB);
        String xr = formato.format(Xr);
        String funXr = formato.format(funcionXr);
        String e =i>1 ? formato.format(errorPermitido):"------------";

        String formatoProcedimiento = String.format("%-5s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s", new Object[] {Integer.valueOf(i),a,b,funA,funB,xr,funXr,e});

        procedimiento += ("\n" + formatoProcedimiento);
    }

    public void setPuntoA(double puntoA) {
        this.puntoA = puntoA;
    }

    public void setPuntoB(double puntoB) {
        this.puntoB = puntoB;
    }

    public void setErrorPermitido(double errorPermitido) {
        this.errorPermitido = errorPermitido;
    }

    public void setFuncion(Function funcion) {
        this.funcion = funcion;
    }

    public double getXr() {
        return Xr;
    }

    public String getProcedimiento() {
        return procedimiento;
    }

    public String getFormato(double root) {
        return this.formato.format(root);
    }

    public void reiniciarProcedimiento(){
        this.procedimiento = String.format("%-10s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s", new Object[] {"Num","a","b","F(a)","F(b)","Xr","F(Xr)","Error"});
    }
}
