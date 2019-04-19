package sample;

import Funcion.Function;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import utils.MyUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class segundoParcial implements Initializable  {
    ObservableList<String> list = FXCollections.observableArrayList("2","3","4","5");

    @FXML
    TextField funcionPuntoFijoFx, funcionDespejadaPuntoFijoFx, valorXPuntoFijoFx, errorPermitidoPuntoFijoFx;
    @FXML
    TextArea txtAreaPuntoFijoFx;
    @FXML
    Button solucionPuntoFijoFx;

    @FXML
    TextField funcionNewtonFx, funcionDerivadaNewtonFx, valorXiNewtonFx, errorPermitidoNewtonFx;
    @FXML
    TextArea txtAreaNewtonFx;
    @FXML
    Button solucionNewtonFx;

    @FXML
    TextField funcionSecanteFx, valorXiMenosUnoSecanteFx, valorXiSecanteFx, errorPermitidoSecanteFx;
    @FXML
    TextArea txtAreaSecanteFx;
    @FXML
    Button solucionSecanteFx;

    @FXML
    ComboBox<String> cmbVariables;
    @FXML
    GridPane gridPaneGaussJordan;
    @FXML
    TextArea txtAreaGaussJordan;
    @FXML
    Button generarMatriz, solucionGauss, limpiarGauss;

    int numVariables;
    MetodoPuntoFijo metodoPuntoFijo;
    MetodoNewtonRhapson metodoNewtonRhapson;
    MetodoSecante metodoSecante;
    Lineales lin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GUI();

    }

    private void GUI(){
        cmbVariables.setItems(list);
        solucionGauss.setDisable(true);

        //numVariables = Integer.parseInt(cmbVariables.getValue());


        limpiarGauss.setOnAction(event -> limpiar());

        metodoPuntoFijo = new MetodoPuntoFijo();
        metodoNewtonRhapson = new MetodoNewtonRhapson();
        metodoSecante = new MetodoSecante();
        lin = new Lineales();

        solucionPuntoFijoFx.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PuntoFijo();
            }
        });

        solucionNewtonFx.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Newton();
            }
        });

        solucionSecanteFx.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Secante();
            }
        });

        solucionGauss.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                double info[][];
                try{
                    info = obtenerMatrizTextField();
                    lin.setMatriz(info);
                    lin.setNumeroVariables(numVariables);

                    GaussJordan();
                    limpiarGauss.setDisable(false);
                }catch (Exception e){
                    alertaError("Campos vacios, favor de llenar","CAMPOS VACIOS","ERROR! Campos vacios", Alert.AlertType.ERROR);
                }
            }
        });

        generarMatriz.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    numVariables = Integer.parseInt(cmbVariables.getValue());
                    creacionTextField(numVariables);
                }catch(Exception e){
                    alertaError("Introduce las variables","Sin variables detectadas","No has seleccionado las variables", Alert.AlertType.ERROR);
                }

            }
        });
    } // Fin GUI

    private void limpiar(){
        txtAreaGaussJordan.clear();
    }

    private void PuntoFijo(){
        try{
            String fun = funcionPuntoFijoFx.getText().trim();
            String funDes = funcionDespejadaPuntoFijoFx.getText().trim();
            double valorX0 = Double.parseDouble(valorXPuntoFijoFx.getText());
            double error = Double.parseDouble(errorPermitidoPuntoFijoFx.getText());
            Function function = new Function(fun);
            Function function1 = new Function(funDes);

            metodoPuntoFijo.setFuncion(function);
            metodoPuntoFijo.setFuncionDespejada(function1);
            metodoPuntoFijo.setX0(valorX0);
            metodoPuntoFijo.setErrorPermitido(error);
            metodoPuntoFijo.PuntoFijo();

            txtAreaPuntoFijoFx.setText(metodoPuntoFijo.getProcedimiento());
            txtAreaPuntoFijoFx.appendText("\n\n Raiz: " + metodoPuntoFijo.getFormato(metodoPuntoFijo.getX1()));
            metodoPuntoFijo.reiniciarProcedimiento();

        }catch(NumberFormatException e){
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("ERROR!");
            al.setContentText("Asegurate de meter los vaores correctos");
            al.setHeaderText("Fail interval!");
            al.show();
        }
    } //Fin Punto Fijo

    private void Newton(){
        try{
            String fun = funcionNewtonFx.getText().trim();
            String funDer = funcionDerivadaNewtonFx.getText().trim();
            double valorXi = Double.parseDouble(valorXiNewtonFx.getText());
            double error = Double.parseDouble(errorPermitidoNewtonFx.getText());
            Function funNor = new Function(fun);
            Function funDeri = new Function(funDer);

            metodoNewtonRhapson.setXi(valorXi);
            metodoNewtonRhapson.setErrorPermitido(error);
            metodoNewtonRhapson.setFuncion(funNor);
            metodoNewtonRhapson.setFuncionDerivada(funDeri);
            metodoNewtonRhapson.NewtonRhapson();

            txtAreaNewtonFx.setText(metodoNewtonRhapson.getProcedimiento());
            txtAreaNewtonFx.appendText("\n\n Raiz: " + metodoNewtonRhapson.getFormato(metodoNewtonRhapson.getXiPlusOne()));
            metodoNewtonRhapson.reiniciarProcedimiento();
        }catch(NumberFormatException e){
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("ERROR!");
            al.setContentText("Asegurate de meter los vaores correctos");
            al.setHeaderText("Fail interval!");
            al.show();
        }
    }

    private void Secante(){
        try{
            String fun = funcionSecanteFx.getText().trim();
            double valorXiMenosUno = Double.parseDouble(valorXiMenosUnoSecanteFx.getText());
            double valorXi = Double.parseDouble(valorXiSecanteFx.getText());
            double error = Double.parseDouble(errorPermitidoSecanteFx.getText());
            Function funSecante = new Function(fun);

            metodoSecante.setXiMenosUno(valorXiMenosUno);
            metodoSecante.setXi(valorXi);
            metodoSecante.setErrorPermitido(error);
            metodoSecante.setFunction(funSecante);
            metodoSecante.Secante();

            txtAreaSecanteFx.setText(metodoSecante.getProcedimiento());
            txtAreaSecanteFx.appendText("\n\n Raiz: " + metodoSecante.getFormato(metodoSecante.getXiMasUno()));
            metodoSecante.reiniciarProcedimiento();
        }catch(NumberFormatException e){
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("ERROR!");
            al.setContentText("Asegurate de meter los vaores correctos");
            al.setHeaderText("Fail interval!");
            al.show();
        }
    }

    private void GaussJordan(){
        double resultado[];

        boolean estado = lin.gaussJordan();
        resultado = lin.resultadoGaussJordan();

        txtAreaGaussJordan.clear();
        txtAreaGaussJordan.setText(lin.getProcedimiento());
        lin.reiniciarProcedimiento();

        if(estado){
            txtAreaGaussJordan.appendText("\nCon esto obtenemos el valor de las incognitas\n");

            imprimirResultados(resultado);
        }else{
        }
    }

    /**
     *
     */
    private void creacionTextField(int numVariables){
        gridPaneGaussJordan.getChildren().clear();
        gridPaneGaussJordan.getColumnConstraints().clear();
        solucionGauss.setDisable(false);

        //Ciclo para crear los 'labels'
        for (int i=0; i<numVariables; i++){
            Label lbl = new Label("X" + (i+1));
            lbl.getStyleClass().add("lbl");
            lbl.getStyleClass().add("lbl-danger");
            lbl.getStyleClass().add("h2");
            gridPaneGaussJordan.add(lbl,i,0);
            //gridPaneGaussJordan.setAlignment(lbl, HPos.CENTER);
            gridPaneGaussJordan.setHalignment(lbl,HPos.CENTER);
        }

        //Ciclo para crear los TextFields dependiendo de la variable
        for(int fila=1;fila <= numVariables; fila++){
            for (int columna=0; columna <= numVariables; columna++){
                TextField txtField = new TextField();
                txtField.getStyleClass().add("text-primary");
                txtField.setOnKeyTyped(keyEventNumber);
                gridPaneGaussJordan.add(txtField,columna,fila);
                gridPaneGaussJordan.setHgap(5);
                gridPaneGaussJordan.setVgap(5);
                gridPaneGaussJordan.setPadding(new Insets(10,10,10,10));
            }
        }

        for(int i=0; i<= numVariables; i++){
            ColumnConstraints restriccion = new ColumnConstraints(5,10, Double.MAX_VALUE);
            restriccion.setHgrow(Priority.ALWAYS);
            gridPaneGaussJordan.getColumnConstraints().add(restriccion);
        }
    }//Fin creacionTextField

    /**
     * Metodo que transforma los TextField en un arreglo 2D
     * @return el numero en el arreglo 2D
     */
    private double[][] obtenerMatrizTextField(){
        double numeros[][] = new double[numVariables][numVariables+1];
        int cantidadTxtField = numVariables;
        double numero;

        for(int fila=0; fila<numVariables; fila++){
            for (int columna=0; columna<numVariables+1; columna++, cantidadTxtField++){
                numero = Double.valueOf(((TextField)gridPaneGaussJordan.getChildren().get(cantidadTxtField)).getText());
                numeros[fila][columna] = numero;
            }
        }
        return numeros;
    }



    private void imprimirResultados(double[] resultados){
        for(int i=0; i<resultados.length; i++){
            txtAreaGaussJordan.appendText("X"+(i+1)+ "="+ MyUtils.format(resultados[i])+"\n");
        }
    }


    EventHandler<KeyEvent> keyEventNumber = new EventHandler<KeyEvent>() {
        public void handle(KeyEvent event) {
            if(Character.isLetter(event.getCharacter().charAt(0)))
                event.consume();
        }
    };

    /*private void GaussJordan(){

        gridPane.getChildren().clear();
        limpiarGauss.setDisable(false);
        TextField tx = new TextField();
        tx.setMaxWidth(100);

        if(cmbVariables.getValue()=="2"){
            gridPane.getChildren().clear();
            solucionGauss.setDisable(false);
            gridPane.setHgap(10);
            gridPane.setVgap(5);
            gridPane.add(tx,0,0);
            gridPane.add(new TextField(),1,0);
            gridPane.add(new TextField(),2,0);
            gridPane.add(new TextField(),0,1);
            gridPane.add(new TextField(),1,1);
            gridPane.add(new TextField(),2,1);

        } else if (cmbVariables.getValue()=="3"){

            solucionGauss.setDisable(false);
            gridPane.setHgap(10);
            gridPane.setVgap(5);
            gridPane.add(new TextField(),0,0);
            gridPane.add(new TextField(),1,0);
            gridPane.add(new TextField(),2,0);
            gridPane.add(new TextField(),3,0);
            gridPane.add(new TextField(),0,1);
            gridPane.add(new TextField(),1,1);
            gridPane.add(new TextField(),2,1);
            gridPane.add(new TextField(),3,1);
            gridPane.add(new TextField(),0,2);
            gridPane.add(new TextField(),1,2);
            gridPane.add(new TextField(),2,2);
            gridPane.add(new TextField(),3,2);
            //System.out.println("Tres");
        } else if (cmbVariables.getValue()=="4"){
            //System.out.println("Four");
            solucionGauss.setDisable(false);
            gridPane.setHgap(10);
            gridPane.setVgap(5);
            gridPane.add(new TextField(),0,0);
            gridPane.add(new TextField(),1,0);
            gridPane.add(new TextField(),2,0);
            gridPane.add(new TextField(),3,0);
            gridPane.add(new TextField(),4,0);
            gridPane.add(new TextField(),0,1);
            gridPane.add(new TextField(),1,1);
            gridPane.add(new TextField(),2,1);
            gridPane.add(new TextField(),3,1);
            gridPane.add(new TextField(),4,1);
            gridPane.add(new TextField(),0,2);
            gridPane.add(new TextField(),1,2);
            gridPane.add(new TextField(),2,2);
            gridPane.add(new TextField(),3,2);
            gridPane.add(new TextField(),4,2);
            gridPane.add(new TextField(),0,3);
            gridPane.add(new TextField(),1,3);
            gridPane.add(new TextField(),2,3);
            gridPane.add(new TextField(),3,3);
            gridPane.add(new TextField(),4,3);
        } else if (cmbVariables.getValue()=="5"){
            //solucionGauss.setDisable(false);
            gridPane.setHgap(10);
            gridPane.setVgap(5);
            gridPane.add(new TextField(),0,0);
            gridPane.add(new TextField(),1,0);
            gridPane.add(new TextField(),2,0);
            gridPane.add(new TextField(),3,0);
            gridPane.add(new TextField(),4,0);
            gridPane.add(new TextField(),5,0);

            gridPane.add(new TextField(),0,1);
            gridPane.add(new TextField(),1,1);
            gridPane.add(new TextField(),2,1);
            gridPane.add(new TextField(),3,1);
            gridPane.add(new TextField(),4,1);
            gridPane.add(new TextField(),5,1);

            gridPane.add(new TextField(),0,2);
            gridPane.add(new TextField(),1,2);
            gridPane.add(new TextField(),2,2);
            gridPane.add(new TextField(),3,2);
            gridPane.add(new TextField(),4,2);
            gridPane.add(new TextField(),5,2);

            gridPane.add(new TextField(),0,3);
            gridPane.add(new TextField(),1,3);
            gridPane.add(new TextField(),2,3);
            gridPane.add(new TextField(),3,3);
            gridPane.add(new TextField(),4,3);
            gridPane.add(new TextField(),5,3);

            gridPane.add(new TextField(),0,4);
            gridPane.add(new TextField(),1,4);
            gridPane.add(new TextField(),2,4);
            gridPane.add(new TextField(),3,4);
            gridPane.add(new TextField(),4,4);
            gridPane.add(new TextField(),5,4);
            //System.out.println("Fais");
        }
    }*/

    private void alertaError(String mensaje, String titulo, String Subtitulo, Alert.AlertType tipoAlerta){
        Alert al = new Alert(tipoAlerta);
        al.setTitle(titulo);
        al.setHeaderText(Subtitulo);
        al.setContentText(mensaje);
        al.show();
    }
}
