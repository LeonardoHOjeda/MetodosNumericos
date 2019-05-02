package SolucionMetodos.TercerParcial;


import java.text.DecimalFormat;
import FuncionNewton.FuncionNewton;

public class MetodoNewtonMulti {
    private FuncionNewton funcion1,funcion2,funcion1x,funcion1y,funcion2x,funcion2y;
    private double errorPermitido;
    private String procedimiento;
    private DecimalFormat formato = new DecimalFormat("0.000000");
    private double X,Y,XiPlusOne,YiPlusOne;

    public MetodoNewtonMulti(){
        formato = new DecimalFormat("0.000000");
        procedimiento = String.format("%-10s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s",
                new Object[] {"Num","X","Y","f1","f2","∂f1/∂fx","∂f1/∂fy","∂f2/∂fx","∂f2/∂fy","ΔX","ΔY","Xi+1","Y1+1","Error-x","Error-y"});
    }

    public MetodoNewtonMulti(FuncionNewton funcion1, FuncionNewton funcion2, FuncionNewton funcion1x, FuncionNewton funcion1y, FuncionNewton funcion2x, FuncionNewton funcion2y,
                             double XiOne, double YiOne, double errorPermitido) {
        this.funcion1 = funcion1;
        this.funcion2 = funcion2;
        this.funcion1x = funcion1x;
        this.funcion1y = funcion1y;
        this.funcion2x = funcion2x;
        this.funcion2y = funcion2y;
        this.XiPlusOne = XiOne;
        this.YiPlusOne = YiOne;
        this.errorPermitido = errorPermitido;
        formato = new DecimalFormat("0.000000");
        procedimiento = String.format("%-10s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s",
                new Object[] {"Num","X","Y","f1","f2","∂f1/∂fx","∂f1/∂fy","∂f2/∂fx","∂f2/∂fy","ΔX","ΔY","Xi+1","Y1+1","Error-x","Error-y"});
    }

    public void NewtonMulti() throws Exception {

        int i = 1;
        double errorX, errorY;

        do{
            System.out.println("Entre al ciclo");
            double fun1 = funcion1.evaluar(X,Y);
            double fun2 = funcion2.evaluar(X,Y);
            double derFun1x = funcion1x.evaluar(X,Y);
            double derFun1y = funcion1y.evaluar(X,Y);
            double derFun2x = funcion2x.evaluar(X,Y);
            double derFun2y = funcion2y.evaluar(X,Y);
            double deltaX = (((-(fun1)*(derFun2y))+((fun2)*(derFun1y))) / (((derFun1x)*(derFun2y)) - ((derFun2x)*(derFun1y))));
            double deltaY = (((-(fun2)*(derFun1x))+((fun1)*(derFun2x))) / (((derFun1x)*(derFun2y)) - ((derFun2x)*(derFun1y))));
            XiPlusOne = deltaX + X;
            YiPlusOne = deltaY + Y;
            errorX = Math.abs((XiPlusOne - X)/XiPlusOne)*100.0D;
            errorY = Math.abs((YiPlusOne - Y) / YiPlusOne)*100.0D;

            agregarProcedimiento(i,X,Y,fun1,fun2,derFun1x,derFun1y,derFun2x,derFun2y,deltaX,deltaY,XiPlusOne,YiPlusOne,errorX,errorY);

            i++;



            X = XiPlusOne;
            Y = YiPlusOne;
        }while(errorX > errorPermitido && errorY > errorPermitido);
    }

    private void agregarProcedimiento(int i, double Xi, double Y1, double f1, double f2, double derF1x, double derF1y, double derF2x, double derF2y, double delX, double delY,
                                      double xiOne, double yiOne, double errorX, double errorY){
        String aX = formato.format(Xi);
        String aY = formato.format(Y1);
        String aF1 = formato.format(f1);
        String aF2 = formato.format(f2);
        String aDerF1x = formato.format(derF1x);
        String aDerF1y = formato.format(derF1y);
        String aDerF2x = formato.format(derF2x);
        String aDerF2y = formato.format(derF2y);
        String aDelX = formato.format(delX);
        String aDelY = formato.format(delY);
        String aXiOne = formato.format(xiOne);
        String aYiOne = formato.format(yiOne);
        String aErrorX = formato.format(errorX);
        String aErrorY = formato.format(errorY);

        String formatoProcedimiento = String.format("%-10s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s",
                new Object[] {Integer.valueOf(i),aX,aY,aF1,aF2,aDerF1x,aDerF1y,aDerF2x,aDerF2y,aDelX,aDelY, aXiOne,aYiOne,aErrorX,aErrorY});

        procedimiento +=  ("\n"+formatoProcedimiento);
    }

/**
 * ============
 * Setters
 * ============
 */

    public void setFuncion1(FuncionNewton funcion1) {
        this.funcion1 = funcion1;
    }

    public void setFuncion2(FuncionNewton funcion2) {
        this.funcion2 = funcion2;
    }

    public void setFuncion1x(FuncionNewton funcion1x) {
        this.funcion1x = funcion1x;
    }

    public void setFuncion1y(FuncionNewton funcion1y) {
        this.funcion1y = funcion1y;
    }

    public void setFuncion2x(FuncionNewton funcion2x) {
        this.funcion2x = funcion2x;
    }

    public void setFuncion2y(FuncionNewton funcion2y) {
        this.funcion2y = funcion2y;
    }

    public void setErrorPermitido(double errorPermitido) {
        this.errorPermitido = errorPermitido;
    }

    public void setX(double x) {
        X = x;
    }

    public void setY(double y) {
        Y = y;
    }

    /**
     * ==========
     * Getters
     * ==========
     */

    public String getProcedimiento() {
        return procedimiento;
    }

    public String getFormato(double root) {
        return formato.format(root);
    }

    public double getXiPlusOne() {
        return XiPlusOne;
    }

    public double getYiPlusOne() {
        return YiPlusOne;
    }

    public void reiniciarProcedimiento(){
        procedimiento = String.format("%-10s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s",
                new Object[] {"Num","X","Y","f1","f2","∂f1/∂fx","∂f1/∂fy","∂f2/∂fx","∂f2/∂fy","ΔX","ΔY","Xi+1","Y1+1","Error-x","Error-y"});
    }
}
