package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    JFXButton primerParcial,segundoParcial,tercerParcial, cuartoParcial;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        primerParcial.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primerParcial();
            }
        });

        segundoParcial.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                segundoParcial();
            }
        });

        tercerParcial.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tercerParcial();
            }
        });

        cuartoParcial.setOnAction(event -> cuartoParcial());

    }

    public void primerParcial(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/primerParc.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Primer Parcial");

            Scene sc = new Scene(root,800,400);

            sc.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");

            stage.setScene(sc);
            stage.show();
            //primerParcial.getScene().getWindow().hide();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void segundoParcial(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/segundoParc.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Segundo Parcial");

            Scene sc = new Scene(root,800,400);

            //sc.getStylesheets().add("segundoParcial.css");
            sc.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");

            stage.setScene(sc);
            stage.show();
            //segundoParcial.getScene().getWindow().hide();

        }catch(IOException e){
            System.out.println(e.getMessage());
            System.out.println("No se puede abrir");
        }
    }

    public void tercerParcial(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/tercerParc.fxml"));
            //
            Stage stage = new Stage();
            stage.setTitle("Tercer Parcial");

            Scene sc = new Scene(root,800,1000);

            sc.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");

            stage.setScene(sc);
            stage.show();

        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void cuartoParcial(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/cuartoParc.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Cuaro Parcial");

            Scene sc = new Scene(root,950,877);

            sc.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");

            stage.setScene(sc);
            stage.show();

        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }


    public void cerrar(javafx.scene.input.MouseEvent mouseEvent) {
        Stage s = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        s.close();
    }

    public void max(javafx.scene.input.MouseEvent mouseEvent) {
       /* Stage s = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        s.setFullScreen(true);*/
    }

    public void min(javafx.scene.input.MouseEvent mouseEvent) {
        Stage s = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        s.setIconified(true);
    }

    public void boton(MouseEvent mouseEvent) {
       /* Stage s = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        s.setX(mouseEvent.getSceneX());
        s.setY(mouseEvent.getSceneY());*/
    }

    public void presion(javafx.scene.input.MouseEvent mouseEvent){
       /* xmouse = mouseEvent.getSceneX();
        ymouse = mouseEvent.getSceneY();
        */
    }
}
