package SolucionMetodos.TercerParcial;

import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory;
import sample.funcionMultivariable;

import java.text.DecimalFormat;

public class Lineales {
    private int numeroVariables;
    private double matriz[][], errorPermitido;
    private String procedimiento;
    private DecimalFormat formato;
    private String separador;

    public Lineales(){
        procedimiento="";
        separador="";
        formato = new DecimalFormat("0.00");
    }

    /**
     * ===============
     *   Gauss-Jordan
     * ===============
     */
    public boolean gaussJordan(){
        int primerPivote, fila, columna;
        double pivote, factor;

        for(primerPivote=0; primerPivote < numeroVariables; primerPivote++){ //recorre diagonal de pivotes
            pivote = matriz[primerPivote][primerPivote]; //[0][0]|[1][1]|[2][2]..etc

            if(pivote==0){
                int filaACambiar = encontrarCambioFila(primerPivote);
                if(filaACambiar != -1){

                    cambiarFilas(primerPivote,filaACambiar);
                    pivote = matriz[primerPivote][primerPivote];
                    procedimiento += "Cambiar la fila R"+(primerPivote+1)+" por la R"+(filaACambiar+1);
                    concatenarProcedimiento();

                } else { //Si es igual a -1
                    procedimiento = "No es posible realizar este problema con Gauss-Jordan";
                    return false;
                }
            }

            procedimiento += "Convertir a uno el pivote "+formato.format(pivote) + " al dividir la fila "+(primerPivote+1)+" sobre "+formato.format(pivote);
            concatenarProcedimiento();//Imprimir matriz

            for(columna=primerPivote; columna<numeroVariables+1; columna++){ //Recorre la fila del pivote para dividirla sobre el pivote
                matriz[primerPivote][columna] = matriz[primerPivote][columna]/pivote;
            }
            concatenarProcedimiento();//Imprime nueva matriz

            if(primerPivote+1 < numeroVariables){
                procedimiento += "Convertir a 0 los valores diferentes al pivote\n";
            }

            for(fila=0; fila<numeroVariables; fila++){
                if(fila !=  primerPivote){
                    factor = matriz[fila][primerPivote];

                    if(factor != 0){
                        procedimiento += "\nHacer 0 el indice de la fila "+ (fila+1) + " haciendo: R"+(fila+1)+" (-)"+formato.format(factor) + "*R"+ (primerPivote+1);

                        for(columna=primerPivote; columna<numeroVariables+1;columna++){
                            matriz[fila][columna] = matriz[fila][columna] - factor*matriz[primerPivote][columna];
                        }
                        concatenarProcedimiento();
                    }
                }
            }
        }
        return true;
    }//Fin Gauss-Jordan

    public double[] resultadoGaussJordan(){
        double resultados[] = new double[numeroVariables];

        for(int fila=0; fila<numeroVariables; fila++){
            resultados[fila] = matriz[fila][numeroVariables];
        }
        return resultados;
    }

    /**
     *==================
     *       JACOBI
     * =================
     */

    public double[] Jacobi(){
        String tempExpression;
        funcionMultivariable[] functions = new funcionMultivariable[numeroVariables];
        double[] resultado = new double[numeroVariables+1];
        double[] valores = new double[numeroVariables+1];
        double[] errores = new double[numeroVariables+1];

        int i=1;

        for(int primerPivote=0; primerPivote<numeroVariables; primerPivote++){
            if(matriz[primerPivote][primerPivote] == 0){
                int filaCambio = encontrarCambioFilaGauss(primerPivote);

                if(filaCambio != -1){
                    cambiarFilas(primerPivote, filaCambio);
                } else{
                    procedimiento = "Sin solucion";
                    return null;
                }
            }
        }

        for(int j=0; j<functions.length; j++){ //functions.length = 4

            tempExpression = expresionLimpia(matriz[j],j);
            functions[j] = new funcionMultivariable(tempExpression, numeroVariables);
            try {
                resultado[j] = functions[j].evaluar(valores);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ;
            errores[j] = Math.abs( (resultado[j] - valores[j]) / resultado[j])*100.0D;;

            valores[j] = 0;
        }

        try{
            proJaco();
            //concatenarProcedimientoJaco((i),valores,errores);

            /**
             * funAct = funcion Actual
             */
            while(!encontrarErrores(errores)){
                for(int funAct=0; funAct < numeroVariables; funAct++){
                    //System.out.println("Resultado viejo: "+resultado[funAct]);

                    resultado[funAct] = functions[funAct].evaluar(valores); //resultado es igual a la funcion y se evalua con el valor anterios

                    //System.out.println("resultado nuevo: "+resultado[funAct]);
                   // System.out.println("Error: "+errores[funAct]);
                    errores[funAct] = Math.abs( (resultado[funAct] - valores[funAct]) / resultado[funAct])*100.0D;
                    System.out.println("\nResultado: "+resultado[funAct]+"\nValores: "+valores[funAct]+"\nError[+"+funAct+"]: "+errores[funAct]);
                }


                //saliendo del ciclo entonces

                concatenarProcedimientoJaco(i,valores, resultado,errores);
                i++;
                valores = resultado.clone();
                //i++;
            }
        }catch (Exception e){
            System.out.println(e);
            System.out.println("Errorrorororor");
        }
        return resultado;
    }

    private void proJaco(){
        formato = new DecimalFormat("0.000000");
        procedimiento = "";
        procedimiento += String.format("%-6s\t","Num");

        for(int i=1; i<=numeroVariables; i++){
            //procedimiento += String.format("%-14s\t%-14s\t", "X"+i,"EP"+i);
            procedimiento += String.format("%-14s\t", "X"+i);
        }
        //procedimiento +="\n";

        for(int j=1; j<=numeroVariables; j++){
            procedimiento += String.format("%-14s\t", "X"+j+"N");
        }

        for(int k=1; k<=numeroVariables; k++){
            procedimiento += String.format("%-14s\t", "ErrorP"+k);
        }
        procedimiento+= "\n";
    }

    /**
     * ===============
     *  Gauss-Seidel
     *  ==============
     *
     *  expTemp = expresion Temporal
     */
    public double[] GaussSeidel(){
        String expTem;
        funcionMultivariable[] funcionMulti = new funcionMultivariable[numeroVariables];

        double[] resultado = new double[numeroVariables];
        double[] valores = new double[numeroVariables];
        double[] errores = new double[numeroVariables];
        double[] aux = new double[numeroVariables];

        int num = 1;

        for(int primerPivote=0; primerPivote<numeroVariables; primerPivote++){
            if(matriz[primerPivote][primerPivote]==0){
                int filaCambio = encontrarCambioFilaGauss(primerPivote);
                if(filaCambio != -1){
                    cambiarFilas(primerPivote,filaCambio);
                }else{
                    procedimiento += "Sin solucion";
                    return null;
                }
            }
        }

        for(int i=0; i<funcionMulti.length;i++){
            expTem = expresionLimpia(matriz[i],i);
            funcionMulti[i] = new funcionMultivariable(expTem,numeroVariables);

                valores[i] = 0;
                resultado[i] = 0;
                errores[i] = Double.POSITIVE_INFINITY;
        }

        proJaco();

        try{
            while(!encontrarErrores(errores)){

                for(int funAct=0; funAct<numeroVariables;funAct++){

                    resultado[funAct] = funcionMulti[funAct].evaluar(valores);
                    errores[funAct] = Math.abs( (resultado[funAct] - valores[funAct]) / resultado[funAct])*100.0D;
                    aux[funAct] = valores[funAct]; //Creamos un arreglo auxiliar para copiar el valor de 'valores' antes de cambiar, esto para que al imprimir el resultado no nos repita el 'resultado' y nos de el valor 'original'
                    valores[funAct] = resultado[funAct]; //PROBLEMA

                }

                //valores = resultado.clone();

                concatenarProcedimientoJaco(num,aux,resultado,errores);
                num++;

                //num++;

            }
        }catch (Exception e){
            System.out.println(e);
        }

        return resultado;
    }

    /**
     *============FIN PROCEDIMIENTOS==================
     */

    private void concatenarProcedimientoJaco(int i, double[] valor, double[] resultado, double[] error) {
        if (i < 10) {
            procedimiento += String.format("%-6s\t", i);
        } else {
            procedimiento += String.format("%-5s\t", i);
        }

        if (i == 1) {
            for (int j = 0; j < numeroVariables; j++) {
                procedimiento += String.format("%14s\t", formato.format(valor[j])); //Aqui muestra si la iteracion es igual a 1 entonces en el error muestra las lineas ya que no esta disponible este error
            }
            for (int j = 0; j < numeroVariables; j++) {
                procedimiento += String.format("%14s\t", formato.format(resultado[j]));
            }
            for (int j = 0; j < numeroVariables; j++) {
                procedimiento += String.format("%16s\t", "---------");
            }
        } else {
            for (int j = 0; j < numeroVariables; j++) {
                procedimiento += String.format("%14s\t", formato.format(valor[j]));
            }
            for (int j = 0; j < numeroVariables; j++) {
                procedimiento += String.format("%14s\t", formato.format(resultado[j]));
            }
            for (int j = 0; j < numeroVariables; j++) {
                procedimiento += String.format("%14s\t", formato.format(error[j]));
            }
        }
        procedimiento += "\n";
    }

    private boolean encontrarErrores(double[] errores){
        boolean stop = true;

        for(double e: errores){
            if(e > errorPermitido){
                return false;
            }
        }
        return stop;
    }

    private int encontrarCambioFila(int primerPivote){
        for(int i=primerPivote; i<numeroVariables; i++){
            if(matriz[i][primerPivote] != 0){
                return i;
            }
        }
        return -1;
    }

    private void cambiarFilas(int primerPivote, int indiceFilaACambiar){
        double pivoteFila[] = new double[numeroVariables+1];
        double filaACambiar[] = new double[numeroVariables+1];

        for(int i=0; i<numeroVariables+1; i++){
            pivoteFila[i] = matriz[primerPivote][i];
            filaACambiar[i] = matriz[indiceFilaACambiar][i];
        }

        for(int i=0; i<numeroVariables+1;i++){
            matriz[primerPivote][i]=filaACambiar[i];
            matriz[indiceFilaACambiar][i] = pivoteFila[i];
        }
    }//Fin cambiarFilas

    private int encontrarCambioFilaGauss(int primerPivote){
        for(int i=0; i<numeroVariables; i++){
            if(matriz[i][primerPivote] != 0){
                if(matriz[primerPivote][i] != 0){
                    return i;
                }
            }
        }
        return -1;
    }//Fin encontrarCambioFila

    private void concatenarProcedimiento(){
        procedimiento += "\n" + imprimirMatriz() +"\n";
    }//Fin concatenarProcedimiento

    private String imprimirMatriz(){
        String mat ="";
        String aux;

        for (int i=0;i<numeroVariables;i++){
            for(int j=0; j<numeroVariables+1; j++){
                aux = matriz[i][j] < 0 ? formato.format(matriz[i][j]) : " "+ formato.format(matriz[i][j]);
                mat += " | " + aux;
            }
            mat += "\n";
            mat += separador;
            //mat += "\n";
        }
        return mat;
    }

    public String expresionLimpia(double[] valor, int posicion){
        String exp = "";
        exp += "("+ valor[valor.length-1]+" + ";

        for(int pos=0; pos<valor.length-1; pos++){
            if (pos != posicion) {
                exp += (valor[pos]* -1)+"x"+(pos+1)+" + ";
            }
        }

        exp = exp.substring(0,exp.length()-2);

        if(valor[posicion] != 0){
            exp += ") / "+valor[posicion];
        }else{
            exp += ")";
        }
        return exp;
    }



    public String getProcedimiento() {
        return procedimiento;
    }

    public void reiniciarProcedimiento(){
        formato = new DecimalFormat("0.00");
        procedimiento="";
    }

    /**
     * ==============
     *  SETTERS
     * ==============
     */
    public void setMatriz(double[][] matriz) {
        this.matriz = matriz;
    }

    public void setNumeroVariables(int numeroVariables) {
        this.numeroVariables = numeroVariables;
    }

    public void setErrorPermitido(double errorPermitido) {
        this.errorPermitido = errorPermitido;
    }
}
