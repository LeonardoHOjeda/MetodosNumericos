package sample;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;

import java.util.Set;
import java.util.TreeSet;

public class funcionMultivariable {
    private String funcion;
    private Expression expression;
    private int numeroVariables;

    public funcionMultivariable(String funcion, int numeroVariables) {
        this.funcion = funcion;
        this.numeroVariables = numeroVariables;

        expression = new ExpressionBuilder(funcion).implicitMultiplication(true).variables(obVar()).build();
    }

    private Set<String> obVar(){
        Set<String> var = new TreeSet<String>();

        if(numeroVariables < 2){
            var.add("x");
        } else {
            for(int i=1; i<= numeroVariables; i++){
                var.add("x"+i);
            }
        }
        return var;
    }

    public double evaluar(double[] valor) throws Exception{
        double resultado = Double.NaN;

        if(numeroVariables < 2){
            expression.setVariable("x",valor[0]);
        } else {
            for (int i=0; i<valor.length; i++){
                expression.setVariable("x"+(i+1),valor[i]);
            }
        }

        ValidationResult validacion = expression.validate();
        if(!validacion.isValid()){
            throw new Exception("Expresion invalida");
        }

        resultado = expression.evaluate();
        return resultado;
    }

    /**
     * ==========
     *   Getters
     * ==========
     */
    public String getFuncion() {
        return funcion;
    }
}
