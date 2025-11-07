package data;
import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.math.BigDecimal;


//ПРЕДУСЛОВИЯ БОГУ ПРЕДУСЛОВИЙ
//ЧЕКИ ДЛЯ ТРОНА ЧЕКОВ!!!!
@Entity
@Table(name = "points")
public class Point implements Serializable {


    @Column(name = "x", precision = 19, scale = 15)
    private BigDecimal x;


    @Column(name = "y", precision = 19, scale = 15)
    private BigDecimal y;

    @Column(name = "r")
    @DecimalMin(value = "0", inclusive = false)
    private double r;

    @Column(name = "is_hit")
    private boolean isHit;


    //@DecimalMin(value = "0", inclusive = false)
    @Column(name = "exec_time")
    private long execTime;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //Эта штука будет генерить ключи для РБД
    private Long id;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getX() { return x.stripTrailingZeros(); }
    public void setX(BigDecimal x) { this.x = x; }

    public BigDecimal getY() { return y.stripTrailingZeros(); }
    public void setY(BigDecimal y) { this.y = y; }

    public Double getR() { return r; }
    public void setR(Double r) { this.r = r; }

    public Boolean getIsHit() { return isHit; }
    public void setHit(Boolean isHit) { this.isHit = isHit; }

    public Long getExecTime() { return execTime; }
    public void setExecTime(Long execTime) { this.execTime = execTime; }


}
