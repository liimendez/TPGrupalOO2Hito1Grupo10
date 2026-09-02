package test;

import dao.HibernateUtil;

public class TestConexion {

    public static void main(String[] args) {
        try {
            HibernateUtil.getSessionFactory();
            System.out.println("Hibernate iniciado correctamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}