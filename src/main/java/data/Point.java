package data;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import org.hibernate.annotations.Check;
import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.inject.Named;

//БОЛЬШЕ ВАЛДИАЦИИ БОГУ ВАЛИДАЦИИ
//ПРЕДУСЛОВИЯ БОГУ ПРЕДУСЛОВИЙ
//ЧЕКИ ДЛЯ ТРОНА ЧЕКОВ!!!!
@Entity
@Table(name = "Point")
@Check(name = "x_range_check", constraints = "x > -5 AND x < 5")
@Check(name = "y_range_check", constraints = "y > -5 AND y < 5")
@Check(name = "r_check", constraints = "r > 0")
@Check(name = "execTime_check", constraints = "execTime > 0")
//тут уже пошли аннотации для JSF
@Named
@RequestScoped
public class Point implements Serializable {

    @Column(name = "x")
    private BigDecimal x;
    @Column(name = "y")
    private BigDecimal y;
    @Column(name = "r")
    private double r;
    @Column(name = "isHit")
    private boolean isHit;
    @Column(name = "execTime")
    private long execTime;

    @Inject
    Points points;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //Эта штука будет генерить ключи для РБД
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
        if( x.compareTo(new BigDecimal(5)) > 0 ||  x.compareTo(new BigDecimal(-5)) < 0 )
            throw new IllegalArgumentException("Значение X превышает заданный диапазон");
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        if( y.compareTo(new BigDecimal(5)) > 0 ||  y.compareTo(new BigDecimal(-5)) < 0 )
            throw new IllegalArgumentException("Значение Y превышает заданный диапазон");
        this.y = y;
    }

    public double getR(){
        return r;
    }

    public void setR(double r){
        if( r <= 0 )
            throw new IllegalArgumentException("Значение R должно быть положительным");
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
        if( execTime < 0 )
            throw new IllegalArgumentException("Время исполнения не может быть отрицательным");
        this.execTime = execTime;
    }

}
