package data.beans;
import data.Point;
import data.PointsService;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;


//Тут уже пошли аннотации для JSF
//Почему Bean и просто Point разные классы?
//Один Entity для Hibernate, а другой Bean для JSF, и смешивать их вроде как нельзя
@Named
@SessionScoped
public class FormPointBean extends PointBean {

    @NotNull(message = "X не может быть пустым")
    @DecimalMin(value = "-5.0", message = "X не может быть меньше -3")
    @DecimalMax(value = "3.0", message = "X не может быть больше 5")
    private BigDecimal x;

    @NotNull(message = "Y не может быть пустым")
    @DecimalMin(value = "-3.0", message = "Y не может быть меньше -3")
    @DecimalMax(value = "5.0", message = "Y не может быть больше 5")
    private BigDecimal y;

    @DecimalMin(value = "1", message = "R не может быть пустым")
    @DecimalMax(value = "4", message = "Y не может быть больше 5")
    private double r;

    private boolean isHit = true;

    @DecimalMin(value = "0", message = "Время не может быть меньше 0")
    private long execTime = 1;

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
        System.out.println("X setted = " + this.x);
    }

    public BigDecimal getY() {
        return y;
    }


    public void setY(BigDecimal y){

        this.y = y;
        System.out.println("Y setted = " + this.y);
    }
    public double getR(){
        return r;
    }


    public void setR(double r){
        this.r = r;
        System.out.println("R setted = " + this.r);
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

        FacesContext facesContext = FacesContext.getCurrentInstance();

        boolean hasErrors = false;

        if(x == null){
            hasErrors = true;
        }
        if(y == null){
            hasErrors = true;
        }
        if(r < 1){
            hasErrors = true;
        }

        if (hasErrors) {
            System.out.println("Error found");
            return;
        }

        calcR();

        Point point = new Point();
        point.setX(x);
        point.setY(y);
        point.setR(r);
        point.setHit(isHit);
        point.setExecTime(execTime);

        System.out.println("Point created");

        pointsService.savePoint(point);

    }

    public String getDisplayX() {
        return x != null ? "X = " + x : "X";
    }

    public String getDisplayY() {
        return y != null ? "Y = " + y : "Y";
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


    private void calcR(){

        long startTime = System.currentTimeMillis();
        BigDecimal zero = BigDecimal.ZERO;
        BigDecimal two = new BigDecimal(2);
        BigDecimal rPowTwo = new BigDecimal(getR()*getR()/4);
        BigDecimal rBig = new BigDecimal(getR());

        if (x.compareTo(zero) < 0 && y.compareTo(zero) < 0)
            isHit = false;
        else if (x.compareTo(zero) > 0 && y.compareTo(zero) > 0)
            isHit = y.multiply(y).add((x.multiply(x)) ).compareTo(rPowTwo) <= 0;
        else if (x.compareTo(zero) >= 0 && y.compareTo(zero) <= 0)
            isHit = x.compareTo(rBig.divide(two)) <= 0 && y.compareTo(rBig.negate()) >= 0;
        else
            isHit = y.compareTo(x.add(rBig)) <= 0;

        execTime = System.currentTimeMillis() - startTime;

        System.out.println(execTime + " " + isHit);

    }

}
