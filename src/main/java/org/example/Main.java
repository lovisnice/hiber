package org.example;

import org.example.entities.Category;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Меню:");
            System.out.println("1. Показати всі категорії");
            System.out.println("2. Додати нову категорію");
            System.out.println("3. Видалити категорію");
            System.out.println("4. Оновити інформацію про категорію");
            System.out.println("5. Вийти з програми");
            System.out.print("Виберіть опцію: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистити буфер введення

            switch (choice) {
                case 1:
                    showCategories();
                    break;
                case 2:
                    insertCategory();
                    break;
                case 3:
                    System.out.print("Введіть ID категорії для видалення: ");
                    int categoryIdToDelete = scanner.nextInt();
                    scanner.nextLine(); // Очистити буфер введення
                    deleteCategory(categoryIdToDelete);
                    break;
                case 4:
                    System.out.print("Введіть ID категорії для оновлення: ");
                    int categoryIdToUpdate = scanner.nextInt();
                    scanner.nextLine(); // Очистити буфер введення
                    System.out.print("Введіть нову назву категорії: ");
                    String newName = scanner.nextLine();
                    System.out.print("Введіть нове зображення категорії: ");
                    String newImage = scanner.nextLine();
                    updateCategory(categoryIdToUpdate, newName, newImage);
                    break;
                case 5:
                    exit = true;
                    System.out.println("Програма завершує роботу.");
                    break;
                default:
                    System.out.println("Некоректний вибір. Спробуйте ще раз.");
            }
        }

        scanner.close();
    }

    private static void deleteCategory(int categoryId) {
        var sf = HibernateUtil.getSessionFactory();
        try(Session session = sf.openSession()) {
            session.beginTransaction();

            Category category = session.get(Category.class, categoryId);
            if (category != null) {
                session.delete(category);
                System.out.println("Категорія успішно видалена");
            } else {
                System.out.println("Категорію з таким ID не знайдено");
            }

            session.getTransaction().commit();
        }
    }

    private static void updateCategory(int categoryId, String newName, String newImage) {
        var sf = HibernateUtil.getSessionFactory();
        try(Session session = sf.openSession()) {
            session.beginTransaction();

            Category category = session.get(Category.class, categoryId);
            if (category != null) {
                category.setName(newName);
                category.setImage(newImage);
                session.update(category);
                System.out.println("Категорія успішно оновлена");
            } else {
                System.out.println("Категорію з таким ID не знайдено");
            }

            session.getTransaction().commit();
        }
    }


    private static void insertCategory() {
        Scanner scanner = new Scanner(System.in);
        Calendar calendar = Calendar.getInstance();
        var sf = HibernateUtil.getSessionFactory();
        try(Session session = sf.openSession()) {
            session.beginTransaction();
            Category category = new Category();
            System.out.println("Вкажіть назву");
            category.setName(scanner.nextLine());
            System.out.println("Вкажіть фото");
            category.setImage(scanner.nextLine());
            category.setDateCreated(calendar.getTime());
            session.save(category);
            session.getTransaction().commit();
        }
    }

    private static void showCategories() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        try (Session session = sf.openSession()) {
            session.beginTransaction();

            List<Category> list = session.createQuery("from Category", Category.class)
                    .getResultList();

            if (list.isEmpty()) {
                System.out.println("У базі даних немає жодної категорії.");
            } else {
                System.out.println("Список категорій:");
                for (Category category : list) {
                    System.out.println("ID: " + category.getId());
                    System.out.println("Назва: " + category.getName());
                    System.out.println("Зображення: " + category.getImage());
                    System.out.println("Дата створення: " + category.getDateCreated());
                    System.out.println();
                }
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Помилка під час виведення категорій: " + e.getMessage());
        }
    }
}