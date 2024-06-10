package com.profapp.DAO;

import com.profapp.model.Asignatura;
import com.profapp.model.Tutoria;
import com.profapp.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AsignaturaDAO {
    private static final Logger logger = LoggerFactory.getLogger(AsignaturaDAO.class);


    public static void createAsignatura(Asignatura asignatura) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();

            transaction = session.beginTransaction();

            session.persist(asignatura);

            transaction.commit();

            logger.info("Asignatura created: {}", asignatura);
        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Failed to create Asignatura", e);
        } finally {
            // Ensure the session is closed
            if (session != null) {
                session.close();
            }
        }
    }

    public static ObservableList<Asignatura> getAllAsignaturas() {
        List<Asignatura> asignaturaList;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            asignaturaList = session.createQuery("FROM Asignatura", Asignatura.class).list();
        }
        return FXCollections.observableArrayList(asignaturaList);
    }

    public static void deleteAsignatura(Asignatura asignatura) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(asignatura);
            transaction.commit();
            logger.info("Tutoria deleted: {}", asignatura);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Failed to delete Tutoria", e);
        }
    }

    public static void editAsignatura(Asignatura asignatura) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Asignatura asignaturaDB = session.get(Asignatura.class, asignatura.getId());

            asignaturaDB.setNombre(asignatura.getNombre());

            session.merge(asignaturaDB);

            transaction.commit();
            logger.debug("Asignatura updated: {} | {}", asignatura, asignaturaDB);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Failed to update Asignatura", e);
        }
    }
}
