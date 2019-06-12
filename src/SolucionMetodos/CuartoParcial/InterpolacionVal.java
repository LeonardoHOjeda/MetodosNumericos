package SolucionMetodos.CuartoParcial;

public class InterpolacionVal {
    private float xValue, yValue;
    private int pivote;

    public InterpolacionVal(int pivote){
        xValue = 0;
        yValue = 0;
        this.pivote = pivote;
    }

    public float getxValue() {
        return xValue;
    }

    public void setxValue(float xValue) {
        this.xValue = xValue;
    }

    public float getyValue() {
        return yValue;
    }

    public void setyValue(float yValue) {
        this.yValue = yValue;
    }

    public int getPivote() {
        return pivote;
    }

    public void setPivote(int pivote) {
        this.pivote = pivote;
    }
}
