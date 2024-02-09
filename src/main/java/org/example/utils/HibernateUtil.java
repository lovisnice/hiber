package org.example.utils;

import lombok.Getter;
import org.example.entities.Category;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration conf = new Configuration().configure();
            conf.addAnnotatedClass(Category.class);
            return conf.buildSessionFactory();
        }catch(Throwable ex) {
            System.out.println("Проблема ініціалізації БД "+ex.getMessage());
            return null;
        }
    }
}