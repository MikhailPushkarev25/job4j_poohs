package ru.job4j.cars;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Driver d1 = Driver.of("driver1");
            Driver d2 = Driver.of("driver2");
            Driver d3 = Driver.of("driver3");
            session.save(d1);
            session.save(d2);
            session.save(d3);

            Engine e1 = Engine.of("2.0");
            Engine e2 = Engine.of("2.5");
            Engine e3 = Engine.of("3.0");
            session.save(e1);
            session.save(e2);
            session.save(e3);

            Car car1 = Car.of("Toyota", e1);
            Car car2 = Car.of("Mazda", e2);
            Car car3 = Car.of("BMW", e3);
            session.save(car1);
            session.save(car2);
            session.save(car3);

            session.getTransaction().commit();
            session.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
