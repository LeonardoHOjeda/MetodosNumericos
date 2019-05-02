package sample;

import Funcion.Function;
import SolucionMetodos.TercerParcial.Lineales;
import SolucionMetodos.SegundoParcial.MetodoNewtonRhapson;
import SolucionMetodos.SegundoParcial.MetodoPuntoFijo;
import SolucionMetodos.SegundoParcial.MetodoSecante;
import herramientas.escenas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import java.io.IOException;
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
    MenuItem tablaFunciones, comoFunciona, infoNewton, infoPuntoFijo, infoSecante;

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

        infoPuntoFijo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String info = "El método del punto fijo es un método iterativo que permite \nresolver sistemas de ecuaciones no necesariamente lineales. " +
                        "\nEn particular se puede utilizar para \ndeterminar raíces de una función de la forma " +
                        "f(x), siempre y cuando \nse cumplan los criterios de convergencia";
                escenas.escenaInfo(info,500,500);
            }
        });

        comoFunciona.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                escenas.escenaInfo("Info",60,100);
            }
        });

        tablaFunciones.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    Parent root = FXMLLoader.load(getClass().getResource("tablaFunciones.FXML"));
                    Stage stage = new Stage();
                    stage.setTitle("Funciones");

                    Scene sc = new Scene(root,500,600);


                    sc.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");

                    stage.setScene(sc);
                    stage.setResizable(false);
                    stage.show();

                }catch(IOException e){
                    System.out.println(e.getMessage());
                    System.out.println("No se puede abrir");
                }
            }
        });
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
                    alertas("Campos no validos, favor de llenar los campos correctamente","CAMPOS NO VALIDOS","ERROR! CAMPOS NO VALIDOS", Alert.AlertType.ERROR);
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
                    alertas("Introduce las variables","Sin variables detectadas","No has seleccionado las variables", Alert.AlertType.ERROR);
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
            alertas("Asegurate de meter los valores correctos","ERROR!","Intervalo fallido", Alert.AlertType.WARNING);
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
            alertas("Asegurate de meter los valores correctos","ERROR!","Intervalo fallido", Alert.AlertType.WARNING);
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
            alertas("Asegurate de meter los valores correctos","ERROR!","Intervalo fallido", Alert.AlertType.WARNING);
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
     * Crea los Text Field necesarios dependiendo de las variables
     */
    private void creacionTextField(int numVariables){
        gridPaneGaussJordan.getChildren().clear();
        gridPaneGaussJordan.getColumnConstraints().clear();
        gridPaneGaussJordan.getStyleClass().add("p");
        solucionGauss.setDisable(false);

        //Ciclo para crear los 'labels'
        for (int i=0; i<numVariables; i++){
            Label lbl = new Label("X" + (i+1));
            lbl.getStyleClass().add("lbl");
            lbl.getStyleClass().add("lbl-danger");
            gridPaneGaussJordan.add(lbl,i,0);
            //gridPaneGaussJordan.setAlignment(lbl, HPos.CENTER);
            gridPaneGaussJordan.setHalignment(lbl,HPos.CENTER);
        }

        //Ciclo para crear los TextFields dependiendo de la variable
        for(int fila=1;fila <= numVariables; fila++){
            for (int columna=0; columna <= numVariables; columna++){
                TextField txtField = new TextField();
                txtField.getStyleClass().add("text-success");
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
            txtAreaGaussJordan.appendText("X"+(i+1)+ "="+ escenas.numeroReducido(resultados[i])+"\n");
        }
    }


    EventHandler<KeyEvent> keyEventNumber = new EventHandler<KeyEvent>() {
        public void handle(KeyEvent event) {
            if(Character.isLetter(event.getCharacter().charAt(0)))
                event.consume();
        }
    };

    private void alertas(String mensaje, String titulo, String Subtitulo, Alert.AlertType tipoAlerta){
        Alert al = new Alert(tipoAlerta);
        al.setTitle(titulo);
        al.setHeaderText(Subtitulo);
        al.setContentText(mensaje);
        al.show();
    }
}
