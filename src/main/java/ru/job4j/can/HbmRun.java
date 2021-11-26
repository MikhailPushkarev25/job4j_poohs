package ru.job4j.can;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Candidate one = Candidate.of("Mikhail", "10 year", 100_000);
            Candidate two = Candidate.of("Roman", "5 year", 80_000);
            Candidate three = Candidate.of("Oleg", "3 year", 50_000);

            session.save(one);
            session.save(two);
            session.save(three);

            Query query = session.createQuery("from ru.job4j.can.Candidate");
            for (Object can : query.list()) {
                System.out.println(can);
            }

            Query query1 = session.createQuery("from Candidate c where c.id = 7");
            System.out.println(query1.uniqueResult());

            Query quer = session.createQuery("from Candidate c where c.name = 'Oleg'");
            System.out.println(quer.getResultList());

            Query query2 = session.createQuery(
                    "update Candidate c set c.name = :newName, c.experience = :newExperience," +
                            "c.salary = :newSalary where c.id = :fid"
            );
            query2.setParameter("newName", "Vasy");
            query2.setParameter("newExperience", "20 year");
            query2.setParameter("newSalary", 200_000);
            query2.setParameter("fid", 15);
            query2.executeUpdate();

            session.createQuery("delete from Candidate where id = :fid")
                    .setParameter("fid", 15)
                    .executeUpdate();

            session.createQuery("insert into Candidate (name, experience, salary) "
                    + "select concat(c.name, 'NEW'), c.experience, c.salary + 10000 "
                    + "from Candidate c where c.id = :fid")
                    .setParameter("fid", 25)
                    .executeUpdate();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
