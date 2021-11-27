package ru.job4j.can;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun {
    public static void main(String[] args) {
        Candidate str = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Candidate one = Candidate.of("Mikhail", "10 year", 100_000);
            session.save(one);

            BaseVacancies vacancies = BaseVacancies.of("Список вакансий");
            session.save(vacancies);

            Vacancy vacancy = Vacancy.of("Junior");
            session.save(vacancy);

            str = session.createQuery(
                    "select distinct cn from Candidate cn "
                    + "join fetch cn.baseVacancies b "
                    + "join fetch b.vacancies v "
                    + "where cn.id = :cid", Candidate.class
            ).setParameter("cid", 1).uniqueResult();


            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        System.out.println(str);
    }
}
