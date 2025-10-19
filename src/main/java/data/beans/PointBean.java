package data.beans;
import data.Point;
import data.PointsService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;

import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.inject.Named;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;


//Тут уже пошли аннотации для JSF
//Почему Bean и просто Point разные классы?
//Один Entity для Hibernate, а другой Bean для JSF, и смешивать их вроде как нельзя
@Named
@SessionScoped
public class PointBean implements Serializable {

    @DecimalMin(value = "-5.0", message = "X не может быть меньше -3")
    @DecimalMax(value = "3.0", message = "X не может быть больше 5")
    private BigDecimal x;

    @DecimalMin(value = "-3.0", message = "Y не может быть меньше -3")
    @DecimalMax(value = "5.0", message = "Y не может быть больше 5")
    private BigDecimal y;

    @DecimalMin(value = "0", message = "R не может быть меньше 0")
    @DecimalMax(value = "4", message = "Y не может быть больше 5")
    private double r;

    private boolean isHit;

    @DecimalMin(value = "0", message = "Время не может быть меньше 0")
    private long execTime;

    @Inject
    PointsService pointsService;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getX() {
        return x;
    }


    public void setX(BigDecimal x) {
        this.x = x;
        System.out.println("X settd = " + this.x);
    }

    public BigDecimal getY() {
        return y;
    }


    public void setY(BigDecimal y){

        this.y = y;
        System.out.println("Y settd = " + this.y);
    }

    public double getR(){
        return r;
    }


    public void setR(double r){
        this.r = r;
    }

    public boolean getIsHit(){
        return isHit;
    }

    public void setIsHit(boolean isHit){
        this.isHit = isHit;
    }

    public long getExecTime(){
        return execTime;
    }

    public void setExecTime(long execTime){
        this.execTime = execTime;
    }

    public void save(){

        Point point = new Point();
        point.setX(x);
        point.setY(y);
        point.setR(r);
        point.setHit(isHit); // Вычисляем попадание
        point.setExecTime(execTime);

        pointsService.savePoint(point);

    }

    public String getDisplayX() {
        return x != null ? "X = " + x : "X";
    }

    public String getDisplayY() {
        return y != null ? "Y = " + y : "Y";
    }

    public String getDisplayR() {
        return r == 0 ? "R = " + r : "R";
    }

}
