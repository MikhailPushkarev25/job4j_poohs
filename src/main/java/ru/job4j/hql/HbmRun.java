package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;


public class HbmRun {
    public static void main(String[] args) {
        Student rsl = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            rsl = session.createQuery(
                    "select distinct st from Student st "
                    + "join fetch st.account a "
                    + "join fetch a.books b "
                    + "where st.id = :sid", Student.class
            )
                    .setParameter("sid", 1)
                    .uniqueResult();

            Student one = Student.of("Иванов Иван", 20, "Москва");
            session.save(one);

            Account two = Account.of("root");
            session.save(two);

            Book book1 = Book.of("Двенадцать стульев", "ACT");
            Book book2 = Book.of("Одноэтажная Америка", "текст");
            session.save(book1);
            session.save(book2);



            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        System.out.println(rsl);
    }
}
