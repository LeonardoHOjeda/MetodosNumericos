package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class tablaFunciones implements Initializable {
    @FXML
    Button btnCerrar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GUI();
    }

    private void GUI(){
        btnCerrar.setOnAction(event -> eventoCerrar());
    }

    private void eventoCerrar(){
        ((Stage)btnCerrar.getScene().getWindow()).close();
    }
}
