package FuncionNewton;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;

public class FuncionNewton {
    private String funcion;
    private Expression expresion;

    public FuncionNewton(String funcion) {
        this.funcion = funcion;
        expresion = new ExpressionBuilder(funcion).variables("x","y").build();
    }

    public double evaluar(double valorX, double valorY) throws Exception{
        double resultado = Double.NaN;
        expresion.setVariable("x",valorX);
        expresion.setVariable("y",valorY);

        ValidationResult validacion = expresion.validate();
        if(!validacion.isValid()){
            throw new Exception("Expresion invalida");
        }

        resultado = expresion.evaluate();
        return resultado;
    }

    public String getFuncion() {
        return funcion;
    }
}
