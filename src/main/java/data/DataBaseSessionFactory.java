package data;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

@Named
@ApplicationScoped
public class DataBaseSessionFactory {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {

        throw new UnsupportedOperationException("Don`t touch my connection");

    }

    public Session getSession(){
        try {
            return sessionFactory.openSession();
        } catch (HibernateException e) {
            System.err.println("Невозможно открыть соединение с БД");
            throw e;
        }

    }


    //Кодик из доки хибернейта
    @PostConstruct
    private void init(){

        final StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                        .build();
        try {
            sessionFactory =
                    new MetadataSources(registry)
                            .addAnnotatedClass(Point.class)
                            .buildMetadata()
                            .buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }

    }

    @PreDestroy
    private void destroy(){

        sessionFactory.close();

    }


}
