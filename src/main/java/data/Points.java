package data;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class Points {

    @Inject
    DataBaseSessionFactory sessionFactory;

    /**
     * Казалось бы надо хранить все точки тут в list,
     * но в интернетах сказали, что так делать нельзя и лучше все хранить сразу в БД :(
     * Ну хоть ответственности разделю этим классом.
     */

    public List<Point> getPoints(){


        List<Point> points = new ArrayList<>();

        try(Session session = sessionFactory.getSession();) {

            points = (List<Point>)session.createQuery("FROM Point").list();

        }catch (Exception e){

            System.out.println("Ошибка при выгрузке точек " + e.getMessage());

        }

        return points;

    }

    public void savePoint(Point point){

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

    }



}
