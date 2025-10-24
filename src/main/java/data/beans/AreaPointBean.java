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
public class AreaPointBean extends PointBean {

    @NotNull(message = "Y не может быть пустым")
    private BigDecimal x;

    @NotNull(message = "Y не может быть пустым")
    private BigDecimal y;

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

        boolean hasErrors = false;

        if(x == null){
            hasErrors = true;
        }
        if(y == null){
            hasErrors = true;
        }
        if(getR() < 1){
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
        point.setR(getR());
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
