package com.profapp.DAO;

import com.profapp.model.Alumno;
import com.profapp.model.Tutoria;
import com.profapp.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TutoriaDAO {
    private static final Logger logger = LoggerFactory.getLogger(TutoriaDAO.class);


    public static void createTutoria(Tutoria tutoria) {
        Session session = null;
        Transaction transaction = null;

        try {
            // Open a new session
            session = HibernateUtil.getSessionFactory().openSession();
            // Begin a new transaction
            transaction = session.beginTransaction();

            // Perform the persist operation
            session.persist(tutoria);

            // Commit the transaction
            transaction.commit();

            // Log the creation of the Alumno object
            logger.info("Tutoria created: {}", tutoria);
        } catch (Exception e) {
            // If there is any exception, roll back the transaction
            if (transaction != null) {
                transaction.rollback();
            }
            // Log the error
            logger.error("Failed to create Tutoria", e);
        } finally {
            // Ensure the session is closed
            if (session != null) {
                session.close();
            }
        }
    }

    public static void deleteTutoria(Tutoria tutoria) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(tutoria);
            transaction.commit();
            logger.info("Tutoria deleted: {}", tutoria);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Failed to delete Tutoria", e);
        }
    }

    public static void editTutoria(Tutoria tutoria) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Tutoria tutoriaDB = session.get(Tutoria.class, tutoria.getID());

            updateParams(tutoriaDB, tutoria);

            session.merge(tutoriaDB);

            transaction.commit();
            logger.debug("Tutoria updated: {} | {}", tutoria, tutoriaDB);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Failed to update Tutoria", e);
        }
    }

    private static void updateParams(Tutoria tutoriaDB, Tutoria tutoria){
        tutoriaDB.setFecha(tutoria.getFecha());
        tutoriaDB.setNotas(tutoria.getNotas());
        tutoriaDB.setAlumno(tutoria.getAlumno());
    }

    public static ObservableList<Tutoria> getAllTutorias() {
        List<Tutoria> tutoriaList;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tutoriaList = session.createQuery("FROM Tutoria", Tutoria.class).list();
        }
        return FXCollections.observableArrayList(tutoriaList);
    }
}
