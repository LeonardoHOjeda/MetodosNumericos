package sample;

import Funcion.Function;
import FuncionNewton.FuncionNewton;
import SolucionMetodos.TercerParcial.InsercionValores;
import SolucionMetodos.TercerParcial.Lineales;
import SolucionMetodos.TercerParcial.MetodoNewtonMulti;
import SolucionMetodos.TercerParcial.OperacionesFuncionales;
import com.jfoenix.controls.JFXButton;
import herramientas.escenas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import herramientas.escenas;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class tercerParcial implements Initializable {
    @FXML
    TextField primerDerivadaXNewtonMulti,primerDerivadaYNewtonMulti,segundaDerivadaXNewtonMulti,segundaDerivadaYNewtonMulti, funcionUnoNewtonMulti, funcionDosNewtonMulti, valorX, valorY,errorPer;
    @FXML
    TextArea txtAreaNewtonMulti;
    @FXML
    Button resolverNewtonMulti;

    @FXML
    Button generarMatrizGaussSeidel, resolverGaussSeidel, limpiarGaussSeidel;
    @FXML
    GridPane gridPaneGaussSeidel;
    @FXML
    TextArea txtAreaGaussSeidel;
    @FXML
    TextField txtErrorGaus;

    @FXML
    TextField txtErrorJacobi;
    @FXML
    Button solucionJacobi, generarMatrizJacobi, limpiarJacobi;
    @FXML
    GridPane gridPaneJacobi;
    @FXML
    TextArea txtAreaJacobi;


    @FXML
    TextField numeroDatosAproxFunc;
    @FXML
    private Label lblXm, lblYm, lblXSum, lblYSum, lblXYSum, lblSquareXSum, lblSrSum, lblStSum, lblEcuation, lblR;
    @FXML
    TableView<InsercionValores> tblViewAproxFunci;
    @FXML
    LineChart Grafica;
    @FXML
    JFXButton btnFilasAproxFunci;
    @FXML
    Button btnResolverAproxFuncio;
    @FXML
    TextArea txtAreaAproxFunc;


    private ObservableList<InsercionValores> insVal;
    private OperacionesFuncionales sum;
    int numVariables;
    MetodoNewtonMulti mnm;
    Lineales lin;

    private DecimalFormat formatter = new DecimalFormat("00.000000");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Start();
    }

    private void Start(){
        insVal = FXCollections.observableArrayList();
        mnm = new MetodoNewtonMulti();
        lin = new Lineales();
        solucionJacobi.setDisable(true);
        limpiarJacobi.setDisable(true);

        resolverNewtonMulti.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                NewtonMulti();
            }
        });
        /**
         * ==================
         *  Acciones Jacobi
         *  =================
         */

        generarMatrizJacobi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                creacionTextField(4);
            }
        });

        limpiarJacobi.setOnAction(event -> clean());

        solucionJacobi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    Jacobi(Double.parseDouble(txtErrorJacobi.getText()));
                    limpiarJacobi.setDisable(false);
                }catch (Exception e) {
                    System.out.println(e);
                    alertas("INTRODUCE LOS VALORES CORRECTOS","VALORES INCORRECTOS","ERROR EN LOS VALORES", Alert.AlertType.ERROR);
                }

            }
        });

        /**
         * ================================
         *      Acciones Gauss Seidel
         * ================================
         */

        generarMatrizGaussSeidel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                creacionTextFieldGauss(4);
            }
        });

        resolverGaussSeidel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    GaussSeidel(Double.parseDouble(txtErrorGaus.getText()));
                    limpiarGaussSeidel.setDisable(false);
                }catch (Exception e){
                    System.out.println(e);
                    alertas("INTRODUCE LOS VALORES CORRECTOS","VALORES NO VALIDOS","ERROR EN VALORES", Alert.AlertType.ERROR);
                }
            }
        });

        /**
         * Acciones aproximacion funcional
         */
        insertarDatos();
        btnFilasAproxFunci.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    filas(Integer.parseInt(numeroDatosAproxFunc.getText()));
                }catch (Exception e){
                    alertas("NUMERO DE FILAS INCORRECTO! FAVOR DE INTRODUCIR UN NUMERO VALIDO","ERROR EN LAS VARIABLES","FILAS INCORRECTAS", Alert.AlertType.ERROR);
                }

            }
        });

        btnResolverAproxFuncio.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                solveModel();
                try {
                    showGraphic();
                } catch (Exception e) {
                    System.out.println("Erro en la grafica");
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Insercion de datos de Newton-Rhapson Multivariable
     */
    private void NewtonMulti(){
        try{
            double vX = Double.parseDouble(valorX.getText());
            double vY = Double.parseDouble(valorY.getText());
            double error = Double.parseDouble(errorPer.getText());

            String funcion1 = funcionUnoNewtonMulti.getText().trim();
            String funcion2 = funcionDosNewtonMulti.getText().trim();
            String derfun1x = primerDerivadaXNewtonMulti.getText().trim();
            String derfun1y = primerDerivadaYNewtonMulti.getText().trim();
            String derfun2x = segundaDerivadaXNewtonMulti.getText().trim();
            String derfun2y = segundaDerivadaYNewtonMulti.getText().trim();

            FuncionNewton fun1 = new FuncionNewton(funcion1);
            FuncionNewton fun2 = new FuncionNewton(funcion2);
            FuncionNewton derf1 = new FuncionNewton(derfun1x);
            FuncionNewton derf1y = new FuncionNewton(derfun1y);
            FuncionNewton derf2 = new FuncionNewton(derfun2x);
            FuncionNewton derf2y = new FuncionNewton(derfun2y);

            mnm.setX(vX);
            mnm.setY(vY);
            mnm.setFuncion1(fun1);
            mnm.setFuncion2(fun2);
            mnm.setFuncion1x(derf1);
            mnm.setFuncion1y(derf1y);
            mnm.setFuncion2x(derf2);
            mnm.setFuncion2y(derf2y);
            mnm.setErrorPermitido(error);
            mnm.NewtonMulti();


            txtAreaNewtonMulti.setText(mnm.getProcedimiento());
            txtAreaNewtonMulti.appendText("\n\nRaiz X: "+mnm.getFormato(mnm.getXiPlusOne())+"\nRaiz Y: "+mnm.getFormato(mnm.getYiPlusOne()));
            mnm.reiniciarProcedimiento();
        }catch (Exception e){
            alertas("Asegurate de meter los valores correctos","ERROR!","Intervalo fallido", Alert.AlertType.WARNING);
        }
    }

    private void Jacobi(double error){
        double info[][];

        info = obtenerMatrizTextField();
        lin.setMatriz(info);
        lin.setNumeroVariables(4);
        lin.setErrorPermitido(error);
        double[] resultado = lin.Jacobi();

        txtAreaJacobi.clear();
        txtAreaJacobi.setText(lin.getProcedimiento());
        lin.reiniciarProcedimiento();

        imprimirResultados(resultado);
    }

    private void GaussSeidel(double error){
        double info[][];

        info = obtenerMatrizTextFieldGauss();
        lin.setMatriz(info);
        lin.setNumeroVariables(4);
        lin.setErrorPermitido(error);
        double resultado[] = lin.GaussSeidel();

        txtAreaGaussSeidel.clear();
        txtAreaGaussSeidel.setText(lin.getProcedimiento());

        if(resultado != null){
            imprimirResultados(resultado);
        }
        lin.reiniciarProcedimiento();
    }

    /**
     * Metodos para resolucion de aproximacion funcional
     */

    private void insertarDatos(){
        TableColumn<InsercionValores, TextField> columnaX = new TableColumn<>("X");
        TableColumn<InsercionValores, TextField> columnaY = new TableColumn<>("Y");
        TableColumn<InsercionValores, String> columnaXiYi = new TableColumn<InsercionValores, String>("XiYi");
        TableColumn<InsercionValores, String> columnaX2 = new TableColumn<InsercionValores, String>("X²");
        TableColumn<InsercionValores, String> columnaY2 = new TableColumn<>("Y²");
        TableColumn<InsercionValores, String> columnaSt = new TableColumn<InsercionValores, String>("ST");
        TableColumn<InsercionValores, String> columnaSr = new TableColumn<InsercionValores, String>("SR");

        columnaX.setCellValueFactory(new PropertyValueFactory<InsercionValores, TextField>("txtPuntoX"));
        columnaY.setCellValueFactory(new PropertyValueFactory<InsercionValores, TextField>("txtPuntoY"));
        columnaXiYi.setCellValueFactory(new PropertyValueFactory<InsercionValores, String>("XiYi"));
        columnaX2.setCellValueFactory(new PropertyValueFactory<InsercionValores, String>("X2"));
       columnaY2.setCellValueFactory(new PropertyValueFactory<InsercionValores, String>("Y2"));
        columnaSt.setCellValueFactory(new PropertyValueFactory<InsercionValores, String>("St"));
        columnaSr.setCellValueFactory(new PropertyValueFactory<InsercionValores, String>("Sr"));

        columnaX.setSortable(false);
        columnaY.setSortable(false);
        columnaX2.setSortable(false);
        columnaY2.setSortable(false);

        columnaXiYi.setEditable(true);

        tblViewAproxFunci.getColumns().addAll(columnaX,columnaY,columnaXiYi,columnaX2,columnaY2,columnaSt,columnaSr);
        tblViewAproxFunci.setItems(insVal);
    }

    private void filas(int nFilas) {
        insVal.clear();
        for (int i = 0; i < nFilas; i++)
            insVal.add(new InsercionValores(i, tblViewAproxFunci));

        tblViewAproxFunci.setDisable(false);
        btnResolverAproxFuncio.setDisable(false);
    }

    private void showGraphic() throws Exception {
        Grafica.getData().clear();

        String def = sum.a0() + " + " + sum.a1() + "x";
        Function function = new Function(def);

        double xValues[] = new double[insVal.size()];
        double yValues[];

        for (int i = 0; i < insVal.size(); i++)
            xValues[i] = insVal.get(i).getX();

        yValues = function.evaluateFrom(xValues);

        XYChart.Series pointsSerie = getSerie();
        XYChart.Series linearSerie = getSerie(def, xValues, yValues);

        Grafica.getData().add(pointsSerie);
        Grafica.getData().add(linearSerie);
    }

    private void solveModel() {
        double a1, a0;
        sum = new OperacionesFuncionales(insVal);



        txtAreaAproxFunc.clear();
        txtAreaAproxFunc.setText(sum.getProcedimiento());
        txtAreaAproxFunc.setText("Sum'X': \t"+escenas.numeroReducido(sum.getX())+"\n"+"Sum'Y': \t"+escenas.numeroReducido(sum.getY())+"\n"+"X²: \t\t"+escenas.numeroReducido(sum.getXcua())+"\n"+
                "XiYi: \t"+escenas.numeroReducido(sum.getXiyi())+"\n"+"Media 'X': "+escenas.numeroReducido(sum.getMediaX())+"\n"+"Media 'Y': "+escenas.numeroReducido(sum.getMediaY())+"\n");
        a1 = Double.valueOf(escenas.numeroReducido(sum.a1()));
        a0 = Double.valueOf(escenas.numeroReducido(sum.a0()));

        for (InsercionValores xyPoint : insVal) {
            xyPoint.setSR(a0, a1);
            xyPoint.setST(sum.getMediaY());
        }

        tblViewAproxFunci.refresh();

        sum.sumatoriaSR();
        sum.sumatoriaST();





        txtAreaAproxFunc.appendText("\ny = "+a0 + " + "+ a1+"x"+"\n"+"ST: "+Double.valueOf(escenas.numeroReducido(sum.getSt()))+"\nSR: "+Double.valueOf(escenas.numeroReducido(sum.getSr()))+"\nR: "+
                Double.valueOf(escenas.numeroReducido(sum.coeficiente())+"\n"));
        //txtAreaAproxFunc.setText("Media 'Y': "+sum.getMediaY());
    }

    private XYChart.Series getSerie() {
        XYChart.Series<Double, Double> serie = new XYChart.Series<Double, Double>();
        serie.setName("Grafica Original");

        for (InsercionValores xyPoint : insVal)
            serie.getData().add(new XYChart.Data<Double, Double>((double) xyPoint.getX(), (double) xyPoint.getY()));

        return serie;
    }

    private XYChart.Series getSerie(String name, double[] xValues, double[] yValues) {
        XYChart.Series<Double, Double> serie = new XYChart.Series<Double, Double>();
        serie.setName("y = " + name);

        for (int i = 0; i < xValues.length; i++)
            serie.getData().add(new XYChart.Data<Double, Double>(xValues[i], yValues[i]));

        return serie;
    }

    private void clean2(){
        txtAreaGaussSeidel.clear();
    }

    private void clean(){
        txtAreaJacobi.clear();
    }

    private void resultados(double[] resultados){
        for (int i=0; i<resultados.length; i++){
            txtAreaJacobi.appendText("X"+(i+1) + " = " + escenas.numeroReducido(resultados[i])+ "\n");
        }
    }


    private void imprimirResultados(double[] resultados){
        txtAreaJacobi.appendText("\n");
        for(int i=0; i<resultados.length; i++){
            txtAreaJacobi.appendText("X"+(i+1)+ " = "+ escenas.numeroReducido(resultados[i])+"\n");
        }
    }
    /**
     * Crea los Text Field necesarios dependiendo de las variables
     */
    private void creacionTextField(int numVariables){
        gridPaneJacobi.getChildren().clear();
        gridPaneJacobi.getColumnConstraints().clear();
        gridPaneJacobi.getStyleClass().add("p");
        solucionJacobi.setDisable(false);


        //Ciclo para crear los 'labels'
        for (int i=0; i<=numVariables; i++){
            if(i<4){
                Label lbl = new Label("X" + (i+1));
                lbl.getStyleClass().add("lbl");
                lbl.getStyleClass().add("lbl-danger");
                gridPaneJacobi.add(lbl,i,0);
                //gridPaneJacobi.setAlignment(lbl, HPos.CENTER);
                gridPaneJacobi.setHalignment(lbl, HPos.CENTER);
            } /*else{
                Label lbl = new Label("Res");
                lbl.getStyleClass().add("lbl");
                lbl.getStyleClass().add("lbl-danger");
                gridPaneJacobi.add(lbl,i,0);
                //gridPaneJacobi.setAlignment(lbl, HPos.CENTER);
                gridPaneJacobi.setHalignment(lbl, HPos.CENTER);
            }*/


        }

        //Ciclo para crear los TextFields dependiendo de la variable
        for(int fila=1;fila <= numVariables; fila++){
            for (int columna=0; columna <= numVariables; columna++){
                TextField txtField = new TextField();
                txtField.getStyleClass().add("text-success");
                txtField.setOnKeyTyped(keyEventNumber);
                gridPaneJacobi.add(txtField,columna,fila);
                gridPaneJacobi.setHgap(5);
                gridPaneJacobi.setVgap(15);
                gridPaneJacobi.setPadding(new Insets(10,10,10,10));
                gridPaneJacobi.setHalignment(txtField,HPos.CENTER);
            }
        }

        for(int i=0; i<= numVariables; i++){
            ColumnConstraints restriccion = new ColumnConstraints(5,10, Double.MAX_VALUE);
            restriccion.setHgrow(Priority.ALWAYS);
            gridPaneJacobi.getColumnConstraints().add(restriccion);
        }
    }//Fin creacionTextField

    private void creacionTextFieldGauss(int numVariables){
        gridPaneGaussSeidel.getChildren().clear();
        gridPaneGaussSeidel.getColumnConstraints().clear();
        gridPaneGaussSeidel.getStyleClass().add("p");
        resolverGaussSeidel.setDisable(false);


        //Ciclo para crear los 'labels'
        for (int i=0; i<=numVariables; i++){
            if(i<4){
                Label lbl = new Label("X" + (i+1));
                lbl.getStyleClass().add("lbl");
                lbl.getStyleClass().add("lbl-danger");
                gridPaneGaussSeidel.add(lbl,i,0);
                //gridPaneGaussSeidel.setAlignment(lbl, HPos.CENTER);
                gridPaneGaussSeidel.setHalignment(lbl, HPos.CENTER);
            } /*else{
                Label lbl = new Label("Res");
                lbl.getStyleClass().add("lbl");
                lbl.getStyleClass().add("lbl-danger");
                gridPaneGaussSeidel.add(lbl,i,0);
                //gridPaneGaussSeidel.setAlignment(lbl, HPos.CENTER);
                gridPaneGaussSeidel.setHalignment(lbl, HPos.CENTER);
            }*/


        }

        //Ciclo para crear los TextFields dependiendo de la variable
        for(int fila=1;fila <= numVariables; fila++){
            for (int columna=0; columna <= numVariables; columna++){
                TextField txtField = new TextField();
                txtField.getStyleClass().add("text-success");
                txtField.setOnKeyTyped(keyEventNumber);
                gridPaneGaussSeidel.add(txtField,columna,fila);
                gridPaneGaussSeidel.setHgap(5);
                gridPaneGaussSeidel.setVgap(15);
                gridPaneGaussSeidel.setPadding(new Insets(10,10,10,10));
                gridPaneGaussSeidel.setHalignment(txtField,HPos.CENTER);
            }
        }

        for(int i=0; i<= numVariables; i++){
            ColumnConstraints restriccion = new ColumnConstraints(5,10, Double.MAX_VALUE);
            restriccion.setHgrow(Priority.ALWAYS);
            gridPaneGaussSeidel.getColumnConstraints().add(restriccion);
        }
    }//Fin creacionTextField

    /**
     * Metodo que transforma los TextField en un arreglo 2D
     * @return el numero en el arreglo 2D
     */
    private double[][] obtenerMatrizTextField(){
        double numeros[][] = new double[4][5];
        int cantidadTxtField = 4;
        double numero;

        for(int fila=0; fila<4; fila++){
            for (int columna=0; columna<5; columna++, cantidadTxtField++){
                numero = Double.valueOf(((TextField)gridPaneJacobi.getChildren().get(cantidadTxtField)).getText());
                numeros[fila][columna] = numero;
            }
        }
        return numeros;
    }

    private double[][] obtenerMatrizTextFieldGauss(){
        double numeros[][] = new double[4][5];
        int cantidadTxtField = 4;
        double numero;

        for(int fila=0; fila<4; fila++){
            for (int columna=0; columna<5; columna++, cantidadTxtField++){
                numero = Double.valueOf(((TextField)gridPaneGaussSeidel.getChildren().get(cantidadTxtField)).getText());
                numeros[fila][columna] = numero;
            }
        }
        return numeros;
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
