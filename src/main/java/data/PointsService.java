package data;

import com.google.gson.Gson;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;


@Named
@ApplicationScoped
public class PointsService {


    //Вместо инжекта бина DataBaseSessionFactory теперь тут этот entityManager
    @PersistenceContext(unitName = "myPersistenceUnit")
    private EntityManager entityManager;

    /**
     * Казалось бы надо хранить все точки тут в list,
     * но в интернетах сказали, что так делать нельзя и лучше все хранить сразу в БД :(
     * Ну хоть ответственности разделю этим классом.
     */

    private static final Gson GSON = new Gson();

    public List<Point> getPoints(){

        /*
                List<Point> points = new ArrayList<>();

        try(Session session = sessionFactory.getSession();) {

            points = (List<Point>)session.createQuery("FROM Point").list();

        }catch (Exception e){

            System.out.println("Ошибка при выгрузке точек " + e.getMessage());

        }

        return points;
         */



        List<Point> resultList = entityManager
                                .createQuery("SELECT p FROM Point p ORDER BY p.id DESC", Point.class)
                                .setMaxResults(20)
                                .getResultList();

        return resultList;
    }

    public String getPointsAsJson(){

        List<Point> resultList = entityManager
                .createQuery("SELECT p FROM Point p ORDER BY p.id DESC", Point.class)
                .setMaxResults(20)
                .getResultList();

        return GSON.toJson(resultList);

    }

    public void savePoint(Point point){

        /*
        try( Session session = sessionFactory.getSession();) {

            Transaction transaction = null;
            transaction = session.beginTransaction();
            try {
                session.persist(point);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new IllegalArgumentException(e);
            }

        }catch (Exception e){

            System.out.println("Ошибка при сохранении точки точек " + e.getMessage());

        }
         */


        entityManager.persist(point);

    }



}
