package sample;

import SolucionMetodos.CuartoParcial.Interpolacion;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import Funcion.*;
import herramientas.*;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class cuartoParcial implements Initializable {

    @FXML
    private GridPane gridPanePolinomial;
    @FXML
    private JFXComboBox<String> cmbNumberRow;
    @FXML
    private JFXButton btnCreateRows;
    @FXML
    private JFXTextField txtFieldX0;
    @FXML
    private JFXTextField txtFieldX1;
    @FXML
    private JFXButton btnSolve;
    @FXML
    private JFXTextField txtFieldX;
    @FXML
    private TextArea txtAreaSolve;
    @FXML
    private Label txtFieldLogX0;
    @FXML
    private Label txtFieldLogX1;
    @FXML
    private JFXTextField txtFieldX01;
    @FXML
    private JFXTextField txtFieldX11;
    @FXML
    private JFXTextField txtFieldX2;
    @FXML
    private JFXButton btnSolve1;
    @FXML
    private TextArea txtAreaSolve1;
    @FXML
    private Label txtFieldLogX01;
    @FXML
    private Label txtFieldLogX11;
    @FXML
    private Label lblLogX2;
    @FXML
    private JFXTextField txtFieldXCual;
    @FXML
    private JFXComboBox<String> cmbBoxGradoLagrange;
    @FXML
    private JFXButton btnGenerate;
    @FXML
    private GridPane gridPaneLagrange;
    @FXML
    private Button btnimprimir;
    @FXML
    private JFXTextField cmbValor;
    @FXML
    private Label lblResultado;
    @FXML
    private GridPane gridPaneDiferencias;
    @FXML
    private JFXComboBox<String> cmbGradoDiferencias;
    @FXML
    private JFXButton btnResolverDiferencias;
    @FXML
    private Label lblRespuesta;
    @FXML
    private JFXTextField txtFieldValorALllegar;
    @FXML
    private JFXButton btnGenerateDiferencias;



    ObservableList<String> list = FXCollections.observableArrayList("2","3","4","5","6");
    DecimalFormat format = new DecimalFormat("0.000000");
    Expression expression;
    int numVariables;

    int numRows = 5;
    Function fun = new Function();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDATA();
        btnCreateRows.setOnAction(event -> {
            generatePointsPolinomial();
        });

        cmbNumberRow.setItems(list);
    }

    private void initDATA(){
        cmbGradoDiferencias.setItems(list);
        btnSolve.setOnAction(event -> linealInterpolation());
        btnSolve1.setOnAction(event -> cuadraticInterpolation());
        cmbBoxGradoLagrange.setItems(list);
        btnGenerate.setOnAction(event -> {
            numVariables = Integer.parseInt(cmbBoxGradoLagrange.getValue());
            creacionTextFieldPolinomial(numVariables);
        });
        btnimprimir.setOnAction(event -> {
            obtenerMatrizTextFieldByX();
            obtenerMatrizTextFieldByY();
            initLagrange();
        });
        btnResolverDiferencias.setOnAction(event -> initDiferencias());
        btnGenerateDiferencias.setOnAction(event -> {
            numVariables = Integer.parseInt(cmbGradoDiferencias.getValue());
            creacionTextFieldDiferencias(numVariables);
        });

    }

    /**===================================================
     *                  INTERPOLACION LINEAL
     *===================================================*/
    private void linealInterpolation(){

        /*float x0 = Float.parseFloat(txtFieldX0.getText());
        float x1 = Float.parseFloat(txtFieldX1.getText());
        float x = Float.parseFloat(txtFieldX.getText());*/
        String x0 = txtFieldX0.getText();
        String x1 = txtFieldX1.getText();
        String x = txtFieldX.getText();
        if(Float.parseFloat(x) < Float.parseFloat(x0) || Float.parseFloat(x) > Float.parseFloat(x1)){
            escenas.alertas("Numero no valido","Favor de introducir un numero valido","El numero no esta dentro del intervalo introducido", Alert.AlertType.ERROR);
        } else{
            solveByLineal(x0,x1,x);
        }

    }

    private float solveByLineal(String x0, String x1,String interpol){
        float x0number = Float.parseFloat(x0);
        float x1number = Float.parseFloat(x1);
        float interpolNumber = Float.parseFloat(interpol);

        float fx0 = functionLog(Double.parseDouble(x0));
        txtFieldLogX0.setText(String.valueOf(format.format(fx0)));

        float fx1 = functionLog(Double.parseDouble(x1));
        txtFieldLogX1.setText(String.valueOf(format.format(fx1)));

        float realNumber = functionLog(Double.parseDouble(interpol));
        float resultado = fx0 + (((fx1)-(fx0))/(x1number-x0number)*(interpolNumber-x0number));
        float errorP = Math.abs((realNumber - resultado)/realNumber)*100;

        txtAreaSolve.setText("Valor de la interpolacion: "+format.format(resultado)+"\nError Permitido: "+format.format(errorP));

        return resultado;
    }

    /**====================================================
     *                  INTERPOLACION CUADRATICA
     ======================================================*/

    private void cuadraticInterpolation(){
        String x0 = txtFieldX01.getText();
        String x1 = txtFieldX11.getText();
        String x2 = txtFieldX2.getText();
        String x = txtFieldXCual.getText();

        if(Float.parseFloat(x) < Float.parseFloat(x0) || Float.parseFloat(x) > Float.parseFloat(x1)){
            escenas.alertas("Numero no valido","Favor de introducir un numero valido","El numero no esta dentro del intervalo introducido", Alert.AlertType.ERROR);
        } else{
            txtAreaSolve1.setText(String.valueOf(format.format(solveByCuadratic(x0,x1,x2,x))));
        }
    }

    private float solveByCuadratic(String x0, String x1, String x2, String interpol){
        float x0number = Float.parseFloat(x0);
        float x1number = Float.parseFloat(x1);
        float x2number = Float.parseFloat(x2);
        float interpolNumber = Float.parseFloat(interpol);

        float fx0 = functionLog(Double.parseDouble(x0));
        txtFieldLogX01.setText(String.valueOf(format.format(fx0)));

        float fx1 = functionLog(Double.parseDouble(x1));
        txtFieldLogX11.setText(String.valueOf(format.format(fx1)));

        float fx2 = functionLog(Double.parseDouble(x2));
        lblLogX2.setText(String.valueOf(format.format(fx2)));

        float b0 = fx0;
        float b1 = ((fx1-fx0)/(x1number-x0number));
        float b2 = ((fx2-fx0-((fx1-fx0)/(x1number-x0number))*(x2number-x0number))/((x2number-x0number)*(x2number-x1number)));

        return b0 + b1*(interpolNumber-x0number)+b2*(interpolNumber - x0number)*(interpolNumber-x1number);

    }

    private float functionLog(double function){
        expression = new ExpressionBuilder("log10(x)").variable("x").build().setVariable("x",function);

        float result = (float) expression.evaluate();

        return result;
    }

    /**====================================================
     *                  INTERPOLACION DIFERENCIAS
     ======================================================*/

    private void initDiferencias(){
        //try{
            float variablesX[] = obtenerMatrizTextFieldByXDiferencia();
            float variablesY[] = obtenerMatrizTextFieldByYDiferencia();
            int exponente = Integer.parseInt(cmbGradoDiferencias.getValue())-1;
            float valorALlegar = Float.parseFloat(txtFieldValorALllegar.getText());

            float resultado = diferencesInterpolation(variablesX,variablesY,exponente, valorALlegar);
            System.out.println(resultado+" sout");

            lblRespuesta.setText(resultado+" ");
        //}catch (Exception e){
           // System.err.println(e);
        //}
    }

    private float diferencesInterpolation(float[]Xn, float[]Yn, int exponente, float valorALlegar){
        float[] x = new float[exponente+1];
        System.out.println(Yn.length+" tamano");
        System.out.println();
        x[0] = Yn[exponente];


        for(int i=1; i<=exponente; i++){
            x[i] = procedimientoDiferencias(Xn, Yn,Xn.length-1, Xn.length-i-1, i+1);
        }

        float resolve = x[0];
        float y;

        for(int i=1; i<x.length; i++){
            y = x[i];
            for(int j=0, k = Xn.length-1; j<i; j++,k-- ){
                y *=(valorALlegar - Xn[k]);
            }
            resolve += y;
        }
        return resolve;

    }

    private float procedimientoDiferencias(float[] Xn, float[] Yn, int moreRight, int moreLeft, float IDK){
        if(IDK == 2 ){
            return (Yn[moreLeft] - Yn[moreRight]) / (Xn[moreLeft] - Xn[moreRight]);
        }else{
            return (procedimientoDiferencias(Xn,Yn,moreRight-1,moreLeft, IDK -1) - procedimientoDiferencias(Xn, Yn, moreRight, moreLeft+1,
                    IDK-1)) /(Xn[moreLeft] - Xn[moreRight]);
        }
    }

    /**====================================================
     *                  INTERPOLACION LAGRANGE
     ======================================================*/

    private void initLagrange(){
        try{
            float variablesX[] = obtenerMatrizTextFieldByX();
            double variableY[] = obtenerMatrizTextFieldByY();
            float valor = Float.parseFloat(cmbValor.getText());
            float resultado = lagrangeInterpolation(variablesX,variableY,valor);
            lblResultado.setText(String.valueOf(resultado));
        }catch (Exception e){
            escenas.alertas("VALOR A INTERPOLAR VACIO",null,"INTRODUCE EL VALOR A INTERPOLAR POR FAVOR :)", Alert.AlertType.ERROR);
        }

    }

    private float lagrangeInterpolation(float[]Xn, double[]Yn, float valorALlegar){
        float x, y, z = 0, aux;

        for(int i=0; i< Xn.length; i++){
            System.out.println(Xn.length+" tamanoo");
            x=1;
            y=1;

            for(int j=0; j<Xn.length; j++){
                if(j != i){
                    x *=(valorALlegar - Xn[j]);
                    y *= (Xn[i]- Xn[j]);
                }
            }
            aux = x / y;
            z += aux * Yn[i];
            System.out.println(Yn[i]+" yeyeye");
        }
        return z;

    }

    /**===================================================
     *              CREACION DE TEXTFIELD PARA LAGRANGE
     * =================================================*/
    private void creacionTextFieldPolinomial(int numRowsVar){
        gridPaneLagrange.getChildren().clear();
        gridPaneLagrange.getColumnConstraints().clear();
        gridPaneLagrange.getStyleClass().add("p");

        //Creacion de los labels//
        for (int i=0; i<=1; i++){
            if(i<numRowsVar){
                Label lbl = new Label("X" + (i+1));
                lbl.getStyleClass().add("lbl");
                lbl.getStyleClass().add("lbl-danger");
                gridPaneLagrange.add(lbl,i,0);
                gridPaneLagrange.setHalignment(lbl, HPos.CENTER);
            }
        }

        //Creacion de los Text Fields
        for(int fila=1;fila < numRowsVar+1; fila++){
            for (int columna=0; columna < 2; columna++){
                TextField txtField = new TextField();
                txtField.getStyleClass().add("text-success");
                txtField.setOnKeyTyped(keyEventNumber);
                gridPaneLagrange.add(txtField,columna,fila);
                gridPaneLagrange.setHgap(5);
                gridPaneLagrange.setVgap(15);
                gridPaneLagrange.setPadding(new Insets(10,10,10,10));
                gridPaneLagrange.setHalignment(txtField,HPos.CENTER);
            }
        }
    }

    /**======================================================
     * Metodo que transforma los TextField en un arreglo
     * @return el numero en el arreglo 2D
     * =======================================================
     */
    private float[] obtenerMatrizTextFieldByX(){
        float numeros[] = new float[numVariables];
        int cantidadTxtField = 2;
        float numero;
        for(int fila=0; fila<numVariables; fila++, cantidadTxtField+=2){
            //for (int columna=0; columna<2; columna++, cantidadTxtField++){
                numero = Float.valueOf(((TextField)gridPaneLagrange.getChildren().get(cantidadTxtField)).getText());
                numeros[fila] = numero;
                System.out.println(numero);
            //}
        }
        System.out.println(numeros.toString());
        return numeros;
    }

    private double[] obtenerMatrizTextFieldByY(){
        double numeros[] = new double[numVariables];
        int cantidadTxtField = 2+1;
        double numero;

        for(int fila=0; fila<numVariables; fila++, cantidadTxtField+=2){
            //for (int columna=0; columna<2; columna++, cantidadTxtField++){
            numero = Double.valueOf(((TextField)gridPaneLagrange.getChildren().get(cantidadTxtField)).getText());
            numeros[fila] = numero;
            System.out.println(numero);
            //}
        }
        System.out.println(numeros.toString());
        return numeros;
    }

    /**===================================================
     *        CREACION DE TEXTFIELD PARA DIFERENCIAS
     * =================================================*/

    private void creacionTextFieldDiferencias(int numRowVar){
        gridPaneDiferencias.getChildren().clear();
        gridPaneDiferencias.getColumnConstraints().clear();
        gridPaneDiferencias.getStyleClass().add("p");

        //Creacion de los labels//
        for (int i=0; i<=1; i++){
            if(i<numRowVar){
                Label lbl = new Label("X" + (i+1));
                lbl.getStyleClass().add("lbl");
                lbl.getStyleClass().add("lbl-danger");
                gridPaneDiferencias.add(lbl,i,0);
                gridPaneDiferencias.setHalignment(lbl, HPos.CENTER);
            }
        }

        //Creacion de los Text Fields
        for(int fila=1;fila < numRowVar+1; fila++){
            for (int columna=0; columna < 2; columna++){
                TextField txtField = new TextField();
                txtField.getStyleClass().add("text-success");
                txtField.setOnKeyTyped(keyEventNumber);
                gridPaneDiferencias.add(txtField,columna,fila);
                gridPaneDiferencias.setHgap(5);
                gridPaneDiferencias.setVgap(15);
                gridPaneDiferencias.setPadding(new Insets(10,10,10,10));
                gridPaneDiferencias.setHalignment(txtField,HPos.CENTER);
            }
        }

    }

    private float[] obtenerMatrizTextFieldByXDiferencia(){
        float numeros[] = new float[numVariables];
        int cantidadTxtField = 2;
        float numero;
        for(int fila=0; fila<numVariables; fila++, cantidadTxtField+=2){
            //for (int columna=0; columna<2; columna++, cantidadTxtField++){
            numero = Float.valueOf(((TextField)gridPaneDiferencias.getChildren().get(cantidadTxtField)).getText());
            numeros[fila] = numero;
            System.out.println(numero);
            //}
        }
        System.out.println(numeros.toString());
        return numeros;
    }

    private float[] obtenerMatrizTextFieldByYDiferencia(){
        float numeros[] = new float[numVariables];
        int cantidadTxtField = 2+1;
        float numero;

        for(int fila=0; fila<numVariables; fila++, cantidadTxtField+=2){
            //for (int columna=0; columna<2; columna++, cantidadTxtField++){
            numero = Float.valueOf(((TextField)gridPaneDiferencias.getChildren().get(cantidadTxtField)).getText());
            numeros[fila] = numero;
            System.out.println(numero);
            //}
        }
        System.out.println(numeros.toString());
        return numeros;
    }

    private void processPolinomial(){

    }

    private void getValuesFromText(){

    }

    private void generatePointsPolinomial(){
        numRows = Integer.parseInt(cmbNumberRow.getValue());
        creacionTextFieldPolinomial(numRows);
    }

    EventHandler<KeyEvent> keyEventNumber = new EventHandler<KeyEvent>() {
        public void handle(KeyEvent event) {
            if(Character.isLetter(event.getCharacter().charAt(0)))
                event.consume();
        }
    };
}
