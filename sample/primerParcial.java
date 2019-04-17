package sample;

import Funcion.Function;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class primerParcial implements Initializable {
    @FXML
    TextField funcionFx, valorAFx, valorBFx, errorFx;
    @FXML
    TextField funcionReglaFx, valorAReglaFx, valorBReglaFx, errorReglaFx;
    @FXML
    Button resolverFx;
    @FXML
    Button resolverReglaFx;
    @FXML
    TextArea txtAreaFx;
    @FXML
    TextArea txtAreaReglaFx;

    Metodosprimero metodosprimero;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        metodosprimero = new Metodosprimero();

        resolverFx.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Biseccion();
            }
        });

        resolverReglaFx.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ReglaFalsa();
            }
        });

    }

    private void Biseccion(){
        try{
            String def = funcionFx.getText().trim();
            double puntoA =Double.parseDouble(valorAFx.getText());
            double puntoB = Double.parseDouble(valorBFx.getText());
            double error = Double.parseDouble(errorFx.getText());
            Function function = new Function(def);

            metodosprimero.setPuntoA(puntoA);
            metodosprimero.setPuntoB(puntoB);
            metodosprimero.setErrorPermitido(error);
            metodosprimero.setFuncion(function);
            metodosprimero.Biseccion();

            txtAreaFx.setText(metodosprimero.getProcedimiento());
            txtAreaFx.appendText("\n\n Raiz: "+metodosprimero.getFormato(metodosprimero.getXr()));
            metodosprimero.reiniciarProcedimiento();

        }catch (NumberFormatException e){
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("ERROR!");
            al.setContentText("Asegurate de meter los vaores correctos");
            al.setHeaderText("Fail interval!");
            al.show();
        }
    }

    private void ReglaFalsa(){
        try{
            String def = funcionReglaFx.getText().trim();
            double puntoA = Double.parseDouble(valorAReglaFx.getText());
            double puntoB = Double.parseDouble(valorBReglaFx.getText());
            double error = Double.parseDouble(errorReglaFx.getText());
            Function fun = new Function(def);

            metodosprimero.setPuntoA(puntoA);
            metodosprimero.setPuntoB(puntoB);
            metodosprimero.setErrorPermitido(error);
            metodosprimero.setFuncion(fun);
            metodosprimero.ReglaFalsa();
            txtAreaReglaFx.setText(metodosprimero.getProcedimiento());
            txtAreaReglaFx.appendText("\n\n Raiz: "+metodosprimero.getFormato(metodosprimero.getXr()));
            metodosprimero.reiniciarProcedimiento();
        }catch (NumberFormatException e){
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("ERROR!");
            al.setContentText("Asegurate de meter los vaores correctos");
            al.setHeaderText("Fail interval!");
            al.show();
        }
    }
}
