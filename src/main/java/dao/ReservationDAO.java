package dao;

import entities.Reservation;
import jakarta.persistence.EntityManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationDAO extends BaseDAO {

    public static Map<Integer, Reservation> getAllReservations() {
        try (EntityManager entityManager = entityManager()) {
            List<Reservation> reservationList = entityManager.createQuery("SELECT r FROM Reservation r", Reservation.class).getResultList();
            Map<Integer, Reservation> reservations = new HashMap<>();
            for (Reservation reservation : reservationList) {
                reservations.put(reservation.getReservationId(), reservation);
            }
            return reservations;
        }
    }

    public static void addReservation(Reservation reservation) {
        try (EntityManager entityManager = entityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(reservation);
            entityManager.getTransaction().commit();
        }
    }

    public static void removeReservation(int id) {
        try (EntityManager entityManager = entityManager()) {
            Reservation reservation = entityManager.find(Reservation.class, id);
            if (reservation != null) {
                entityManager.getTransaction().begin();
                entityManager.remove(reservation);
                entityManager.getTransaction().commit();
            }
        }
    }
}
