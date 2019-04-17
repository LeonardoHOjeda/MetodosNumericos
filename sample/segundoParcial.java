package sample;

import Funcion.Function;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

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
    GridPane gridPane;
    @FXML
    Button generarMatriz, solucionGauss, limpiarGauss;





    MetodoPuntoFijo metodoPuntoFijo;
    MetodoNewtonRhapson metodoNewtonRhapson;
    MetodoSecante metodoSecante;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbVariables.setItems(list);

        generarMatriz.setOnAction(e -> GaussJordan());


        metodoPuntoFijo = new MetodoPuntoFijo();
        metodoNewtonRhapson = new MetodoNewtonRhapson();
        metodoSecante = new MetodoSecante();

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
    }

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
        /**
         * columnIndez = columnas
         * rowIndex = filass
         */
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
    }
}
