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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import herramientas.escenas;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class tercerParcial implements Initializable {
    @FXML
    MenuItem tblFunciones;

    @FXML
    TextField primerDerivadaXNewtonMulti,primerDerivadaYNewtonMulti,segundaDerivadaXNewtonMulti,segundaDerivadaYNewtonMulti, funcionUnoNewtonMulti, funcionDosNewtonMulti, valorX, valorY,errorPer;
    @FXML
    TextArea txtAreaNewtonMulti;
    @FXML
    Button resolverNewtonMulti;


    @FXML
    Button generarMatrizGaussSeidel, resolverGaussSeidel, limpiarGaussSeidel;
    @FXML
    GridPane gridPaneGaussSeidel, gridPaneGaussSeidelVar;
    @FXML
    TextArea txtAreaGaussSeidel;
    @FXML
    TextField txtErrorGaus;
    @FXML
    ComboBox<String> cmbVariablesSeidel;

    @FXML
    TextField txtErrorJacobi;
    @FXML
    Button solucionJacobi, generarMatrizJacobi, limpiarJacobi;
    @FXML
    GridPane gridPaneJacobi,gridPaneJacobiVar;
    @FXML
    TextArea txtAreaJacobi;
    @FXML
    ComboBox<String> cmbJacobi;


    @FXML
    TextField numeroDatosAproxFunc;
    @FXML
    TableView<InsercionValores> tblViewAproxFunci;
    @FXML
    JFXButton btnFilasAproxFunci;
    @FXML
    Button btnResolverAproxFuncio;
    @FXML
    TextArea txtAreaAproxFunc;

    ObservableList<String> list = FXCollections.observableArrayList("2","3","4","5","6");
    int numeroVariables;
    private ObservableList<InsercionValores> insVal;
    private OperacionesFuncionales sum;
    MetodoNewtonMulti mnm;
    Lineales lin;

    private DecimalFormat formatter = new DecimalFormat("00.000000");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Start();
    }

    private void Start(){
        cmbJacobi.setItems(list);
        cmbVariablesSeidel.setItems(list);

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

        tblFunciones.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File myfile = new File("C:/Users/perro/IdeaProjects/Metodos1/PDF/Como funciona - Jacobi.pdf");
                try {
                    Desktop.getDesktop().open(myfile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                try{
                    numeroVariables = Integer.parseInt(cmbJacobi.getValue());
                    creacionTextField(numeroVariables);
                    creacionTextFieldVariables(numeroVariables);
                }catch(Exception e){
                    alertas("CAMPO DE VARIABLES SIN SELECCIONAR, FAVOR DE SELECCIONAR UN VALOR","SIN VARIABLES","ERROR EN LAS VARIABLES", Alert.AlertType.ERROR);
                }
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
                try{
                    numeroVariables = Integer.parseInt(cmbVariablesSeidel.getValue());
                    creacionTextFieldGauss(numeroVariables);
                    creacionTextFieldGaussVar(numeroVariables);
                }catch (Exception e){}
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

        limpiarGaussSeidel.setOnAction(event -> clean2());

        /**
         * =======================================
         *      Acciones aproximacion funcional
         * =======================================
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
                /*try {
                    showGraphic();
                } catch (Exception e) {
                    System.out.println("Erro en la grafica");
                    e.printStackTrace();
                }*/
            }
        });

    }

    /**====================================================
     * Insercion de datos de Newton-Rhapson Multivariable
     * ====================================================
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

    /**
     * ==================
     *        Jacobi
     * ==================
     */

    private void Jacobi(double error){
        double info[][];
        double numVariables[];

        numVariables= obtenerMatrizTextFieldVar();
        info = obtenerMatrizTextField();
        lin.setMatriz(info);
        lin.setVariables(numVariables);
        lin.setNumeroVariables(numeroVariables);
        lin.setErrorPermitido(error);
        double[] resultado = lin.Jacobi();

        txtAreaJacobi.clear();
        txtAreaJacobi.setText(lin.getProcedimiento());
        lin.reiniciarProcedimiento();

        imprimirResultados(resultado);
    }

    private void GaussSeidel(double error){
        double info[][];
        double numVariables[];

        numVariables = obtenerMatrizTextFieldGaussVar();
        info = obtenerMatrizTextFieldGauss();
        lin.setMatriz(info);
        lin.setVariables(numVariables);
        lin.setNumeroVariables(numeroVariables);
        lin.setErrorPermitido(error);
        double resultado[] = lin.GaussSeidel();

        txtAreaGaussSeidel.clear();
        txtAreaGaussSeidel.setText(lin.getProcedimiento());

        if(resultado != null){
            imprimirResultadosGauss(resultado);
        }
        lin.reiniciarProcedimiento();
    }

    /**
     * ===================================================
     * Metodos para resolucion de aproximacion funcional
     * ===================================================
     */

    private void insertarDatos(){
        TableColumn<InsercionValores, TextField> columnaX = new TableColumn<>("X");
        TableColumn<InsercionValores, TextField> columnaY = new TableColumn<>("Y");


        columnaX.setCellValueFactory(new PropertyValueFactory<InsercionValores, TextField>("txtPuntoX"));
        columnaY.setCellValueFactory(new PropertyValueFactory<InsercionValores, TextField>("txtPuntoY"));


        columnaX.setSortable(false);
        columnaY.setSortable(false);

        tblViewAproxFunci.getColumns().addAll(columnaX,columnaY);
        tblViewAproxFunci.setItems(insVal);
    }

    private void filas(int nFilas) {
        insVal.clear();
        for (int i = 0; i < nFilas; i++)
            insVal.add(new InsercionValores(i, tblViewAproxFunci));

        tblViewAproxFunci.setDisable(false);
        btnResolverAproxFuncio.setDisable(false);
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

    /**
     * ===========================
     * Termina aprox Funcional
     * ===========================
     */

    private void clean2(){
        txtAreaGaussSeidel.clear();
    }

    private void clean(){
        txtAreaJacobi.clear();
    }

    private void imprimirResultados(double[] resultados){
        txtAreaJacobi.appendText("\n");
        for(int i=0; i<resultados.length; i++){
            txtAreaJacobi.appendText("X"+(i+1)+ " = "+ escenas.numeroReducido(resultados[i])+"\n");
        }
    }

    private void imprimirResultadosGauss(double[] resultados){
        txtAreaGaussSeidel.appendText("\n");
        for(int i=0; i<resultados.length; i++){
            txtAreaGaussSeidel.appendText("X"+(i+1)+" = "+ escenas.numeroReducido(resultados[i])+"\n");
        }
    }

    /**==============================================================
     * Crea los Text Field necesarios dependiendo de las variables
     * ==============================================================
     */
    private void creacionTextField(int numVariables){
        gridPaneJacobi.getChildren().clear();
        gridPaneJacobi.getColumnConstraints().clear();
        gridPaneJacobi.getStyleClass().add("p");
        solucionJacobi.setDisable(false);

        //Ciclo para crear los 'labels'
        for (int i=0; i<=numVariables; i++){
            if(i<numVariables){
                Label lbl = new Label("X" + (i+1));
                lbl.getStyleClass().add("lbl");
                lbl.getStyleClass().add("lbl-danger");
                gridPaneJacobi.add(lbl,i,0);
                //gridPaneJacobi.setAlignment(lbl, HPos.CENTER);
                gridPaneJacobi.setHalignment(lbl, HPos.CENTER);
            }
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

    private void creacionTextFieldVariables(int numeroVariables){
        gridPaneJacobiVar.getChildren().clear();
        gridPaneJacobiVar.getColumnConstraints().clear();
        gridPaneJacobiVar.getStyleClass().add("p");

        for(int k=0; k<numeroVariables; k++){
            Label lbl = new Label("Valor X"+(k+1));
            lbl.getStyleClass().add("lbl");
            lbl.getStyleClass().add("lbl-danger");
            gridPaneJacobiVar.add(lbl,k,0);
            gridPaneJacobiVar.setHalignment(lbl,HPos.CENTER);
        }
        for(int i=0; i<numeroVariables; i++) {
            TextField txtF = new TextField();
            txtF.setOnKeyTyped(keyEventNumber);
            gridPaneJacobiVar.add(txtF, i, 1);
            gridPaneJacobiVar.setHgap(5);
            gridPaneJacobiVar.setVgap(5);
            gridPaneJacobiVar.setPadding(new Insets(10, 10, 10, 10));

            for (int j = 0; j <= numeroVariables; j++) {
                ColumnConstraints restriccion = new ColumnConstraints(5, 10, Double.MAX_VALUE);
                restriccion.setHgrow(Priority.ALWAYS);
                //gridPaneGaussSeidel.getColumnConstraints().add(restriccion);
            }
        }
    }

    private double[] obtenerMatrizTextFieldVar(){
        double numerosVariables[] = new double[numeroVariables];
        int cantidadTxtField = numeroVariables;
        double numero;

        for(int columna=0; columna<numeroVariables; columna++, cantidadTxtField++){
                numero = Double.valueOf(((TextField)gridPaneJacobiVar.getChildren().get(cantidadTxtField)).getText());
                numerosVariables[columna] = numero;
            }
        return numerosVariables;
    }

    /**=========================================
     * Creacion de TextField para Gauss-Seidel
     * =========================================
     */
    private void creacionTextFieldGauss(int numVariables){
        gridPaneGaussSeidel.getChildren().clear();
        gridPaneGaussSeidel.getColumnConstraints().clear();
        gridPaneGaussSeidel.getStyleClass().add("p");
        resolverGaussSeidel.setDisable(false);

        //Ciclo para crear los 'labels'
        for (int i=0; i<=numVariables; i++){
            if(i<numVariables){
                Label lbl = new Label("X" + (i+1));
                lbl.getStyleClass().add("lbl");
                lbl.getStyleClass().add("lbl-danger");
                gridPaneGaussSeidel.add(lbl,i,0);
                //gridPaneGaussSeidel.setAlignment(lbl, HPos.CENTER);
                gridPaneGaussSeidel.setHalignment(lbl, HPos.CENTER);
            }
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

    private void creacionTextFieldGaussVar(int numeroVariables){
        gridPaneGaussSeidelVar.getChildren().clear();
        gridPaneGaussSeidelVar.getColumnConstraints().clear();
        gridPaneGaussSeidelVar.getStyleClass().add("p");

        for(int k=0; k<numeroVariables; k++){
            Label lbl = new Label("Valor X"+(k+1));
            lbl.getStyleClass().add("lbl");
            lbl.getStyleClass().add("lbl-danger");
            gridPaneGaussSeidelVar.add(lbl,k,0);
            gridPaneGaussSeidelVar.setHalignment(lbl,HPos.CENTER);
        }
        for(int i=0; i<numeroVariables; i++) {
            TextField txtF = new TextField();
            txtF.setOnKeyTyped(keyEventNumber);
            gridPaneGaussSeidelVar.add(txtF, i, 1);
            gridPaneGaussSeidelVar.setHgap(5);
            gridPaneGaussSeidelVar.setVgap(5);
            gridPaneGaussSeidelVar.setPadding(new Insets(10, 10, 10, 10));

            for (int j = 0; j <= numeroVariables; j++) {
                ColumnConstraints restriccion = new ColumnConstraints(5, 10, Double.MAX_VALUE);
                restriccion.setHgrow(Priority.ALWAYS);
                //gridPaneGaussSeidelVar.getColumnConstraints().add(restriccion);
            }
        }
    }

    private double[] obtenerMatrizTextFieldGaussVar(){
        double numerosVariables[] = new double[numeroVariables];
        int cantidadTxtField = numeroVariables;
        double numero;

        for(int columna=0; columna<numeroVariables; columna++, cantidadTxtField++){
            numero = Double.valueOf(((TextField)gridPaneGaussSeidelVar.getChildren().get(cantidadTxtField)).getText());
            numerosVariables[columna] = numero;
        }
        return numerosVariables;
    }

    /**======================================================
     * Metodo que transforma los TextField en un arreglo 2D
     * @return el numero en el arreglo 2D
     * =======================================================
     */
    private double[][] obtenerMatrizTextField(){
        double numeros[][] = new double[numeroVariables][numeroVariables+1];
        int cantidadTxtField = numeroVariables;
        double numero;

        for(int fila=0; fila<numeroVariables; fila++){
            for (int columna=0; columna<numeroVariables+1; columna++, cantidadTxtField++){
                numero = Double.valueOf(((TextField)gridPaneJacobi.getChildren().get(cantidadTxtField)).getText());
                numeros[fila][columna] = numero;
            }
        }
        return numeros;
    }

    private double[][] obtenerMatrizTextFieldGauss(){
        double numeros[][] = new double[numeroVariables][numeroVariables+1];
        int cantidadTxtField = numeroVariables;
        double numero;

        for(int fila=0; fila<numeroVariables; fila++){
            for (int columna=0; columna<numeroVariables+1; columna++, cantidadTxtField++){
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
