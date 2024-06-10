package com.profapp.DAO;

import com.profapp.CalificarPage;
import com.profapp.model.Alumno;
import com.profapp.model.Asignatura;
import com.profapp.model.Calificar;
import com.profapp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalificarDAO {
    private static final Logger logger = LoggerFactory.getLogger(CalificarDAO.class);


    public static void createCalificar(Calificar calificar) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();

            transaction = session.beginTransaction();

            session.persist(calificar);

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            // Ensure the session is closed
            if (session != null) {
                session.close();
            }
        }
    }

    public static Calificar getCalificacion(Alumno alumno, Asignatura asignatura) {
        Calificar calificacion = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Calificar WHERE alumno = :alumno AND asignatura = :asignatura";
            Query<Calificar> query = session.createQuery(hql, Calificar.class);
            query.setParameter("alumno", alumno);
            query.setParameter("asignatura", asignatura);
            calificacion = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calificacion;
    }

    public static void updateCalificar(Calificar calificacion) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Calificar calificacionDB = session.get(Calificar.class, calificacion.getId());

            calificacionDB.setAlumno(calificacion.getAlumno());
            calificacionDB.setAsignatura(calificacion.getAsignatura());
            calificacionDB.setEstado(calificacion.getEstado());

            session.merge(calificacionDB);

            transaction.commit();
            logger.debug("calificacion updated: {} | {}", calificacion, calificacionDB);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Failed to update calificacion", e);
        }
    }
}
