package SolucionMetodos.TercerParcial;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import herramientas.escenas;

import static herramientas.escenas.numeroReducido;

public class InsercionValores implements EventHandler<KeyEvent>{
    private double x, y, SR, ST;
    private TextField txtPuntoX, txtPuntoY;
    private int valor;

    public InsercionValores(int valor, TableView tableView){
        txtPuntoX = new TextField();
        txtPuntoY = new TextField();
        this.valor = valor;
        x = 0;
        y = 0;
        ST = 0;
        SR = 0;

        campotxt(tableView);
    }

    private void campotxt(final TableView tableView){
        txtPuntoX.focusedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if (newValue) //focused
                    tableView.getSelectionModel().select(valor);
                else {        //unfocused
                    if (txtPuntoX.getText().length() > 0)
                        x = Float.valueOf(txtPuntoX.getText());
                    tableView.refresh();
                }
            }
        });

        txtPuntoY.focusedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    tableView.getSelectionModel().select(valor);
                } else if(txtPuntoY.getText().length() > 0){
                    y = Float.valueOf(txtPuntoY.getText());
                }
                tableView.refresh();
            }
        });

        txtPuntoX.setOnKeyTyped(this);
        txtPuntoY.setOnKeyTyped(this);
    }

    /**
     * Getters
     */
    public TextField getTxtPuntoX() {
        return txtPuntoX;
    }

    public TextField getTxtPuntoY() {
        return txtPuntoY;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSR() {
        return SR;
    }

    public double getST() {
        return ST;
    }

    public String getXiYi(){
        return (x*y == 0) ? "" : escenas.numeroReducido(x*y);
    }

    public String getX2(){
        return (x*x == 0) ? "" : escenas.numeroReducido(x*x);
    }

    public String getY2(){
        return  (y*y == 0) ? "" : escenas.numeroReducido(y*y);
    }

    public String getSr(){
        return (SR == 0 ? "" : escenas.numeroReducido(SR));
    }

    public String getSt(){
        return (ST == 0 ? "" : escenas.numeroReducido(ST));
    }

    /**
     *Setters
     */
    public void setSR(double a0, double a1) {
        SR = (double)Math.pow(y- a0 -a1*x,2);
    }

    public void setST(double mediaY) {
        ST = (double)Math.pow(y - mediaY,2);
    }

    @Override
    public void handle(KeyEvent event) {
        if (Character.isLetter(event.getCharacter().charAt(0)))
            event.consume();
    }
}
