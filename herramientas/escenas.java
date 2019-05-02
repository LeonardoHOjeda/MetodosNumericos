package herramientas;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DecimalFormat;

public class escenas {

    MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE);

    public static void escenaInfo(String label, double ancho, double alto){
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(5);
        vbox.getStyleClass().setAll("alert","alert-success");

        Label lbl = new Label(label);
        Button btn = new Button("Cerrar");
        btn.getStyleClass().setAll("btn","btn-info");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ((Stage)btn.getScene().getWindow()).close();
            }
        });

        vbox.getChildren().setAll(lbl,btn);
        Scene sc = new Scene(vbox,ancho,alto);
        sc.getStylesheets().add("herramientas/color.css");
        sc.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");

        Stage st = new Stage();
        st.setScene(sc);
        st.show();
    }

    private static DecimalFormat formato = new DecimalFormat("0.000000");

    public static String numeroReducido(double numero){
        return formato.format(numero);
    }
}
