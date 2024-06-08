package com.profapp.DAO;

import com.profapp.model.Alumno;
import com.profapp.util.HibernateUtil;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javafx.collections.FXCollections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AlumnoDAO {
    private static final Logger logger = LoggerFactory.getLogger(AlumnoDAO.class);

    /**
     * Create a new Alumno record in the database.
     * @param alumno The Alumno object to be saved.
     */
    public static void createAlumno(Alumno alumno) {
        Session session = null;
        Transaction transaction = null;

        try {
            // Open a new session
            session = HibernateUtil.getSessionFactory().openSession();
            // Begin a new transaction
            transaction = session.beginTransaction();

            // Perform the persist operation
            session.persist(alumno);

            // Commit the transaction
            transaction.commit();

            // Log the creation of the Alumno object
            logger.info("Alumno created: {}", alumno);
        } catch (Exception e) {
            // If there is any exception, roll back the transaction
            if (transaction != null) {
                transaction.rollback();
            }
            // Log the error
            logger.error("Failed to create Alumno", e);
        } finally {
            // Ensure the session is closed
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Read all Alumno records from the database.
     * @return An ObservableList of Alumno objects.
     */
    public static ObservableList<Alumno> getAllAlumnos() {
        List<Alumno> alumnosList;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            alumnosList = session.createQuery("FROM Alumno", Alumno.class).list();
        }
        return FXCollections.observableArrayList(alumnosList);
    }

    /**
     * Update an existing Alumno record in the database.
     * @param alumno The Alumno object with updated data.
     */
    public static void editAlumno(Alumno alumno) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Alumno alumnoDB = session.get(Alumno.class, alumno.getID());

            alumnoDB.setNIA(alumno.getNIA());
            alumnoDB.setName(alumno.getName());
            alumnoDB.setLastName(alumno.getLastName());

            session.merge(alumnoDB);

            transaction.commit();
            RetriveAll();
            logger.debug("Alumno updated: {} | {}", alumno.getNIA(), alumnoDB.getNIA());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Failed to update Alumno", e);
        }
    }

    /**
     * Delete an Alumno record from the database.
     * @param alumno The Alumno object to be deleted.
     */
    public static void deleteAlumno(Alumno alumno) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(alumno);
            transaction.commit();
            logger.info("Alumno deleted: {}", alumno);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Failed to delete Alumno", e);
        }
    }

    public static void RetriveAll() {

        List<Alumno> alumnosList;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            alumnosList = session.createQuery("FROM Alumno", Alumno.class).getResultList();
            alumnosList.forEach(System.out::println);
        }
    }
}
