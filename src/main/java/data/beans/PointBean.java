package data.beans;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;

public class PointBean implements Serializable {

    @DecimalMin(value = "0.5", message = "R не может быть пустым")
    @DecimalMax(value = "4", message = "R не может быть больше 4")
    private double r;

    public double getR(){
        return r;
    }

    public void setR(double r){
        this.r = r;
        System.out.println("R setted = " + this.r);
    }

    public String getDisplayR() {
        return r != 0 ? "R = " + r : "R";
    }
    public double getDoubleR(){
        return r*2;
    }

    public void setDoubleR(double r){
        this.r = r/2;
        System.out.println("R setted = " + this.r);
    }

}
