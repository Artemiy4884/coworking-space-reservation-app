package entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "reservations")
public class Reservation{
    private static int idCounter = 1;

    @Id
    @Column(name = "reservation_id",nullable = false, unique = true)
    private int reservationId;

    private String username;

    @Column(name = "space_id")
    private int spaceId;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    public Reservation(String username, int spaceId, LocalDateTime startTime, LocalDateTime endTime) {
        this.reservationId = idCounter++;
        this.username = username;
        this.spaceId = spaceId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Reservation() {}

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public String getUsername() {
        return username;
    }

    public int getSpaceId() {
        return spaceId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public static void initializeNextId(Map<Integer, Reservation> reservations) {
        idCounter = reservations.values().stream()
                .mapToInt(Reservation::getReservationId)
                .max()
                .orElse(0) + 1;
    }



    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                "\nCustomer: " + username +
                "\nSpace ID: " + spaceId +
                "\nFrom: " + startTime +
                "\nTo: " + endTime;
    }
}
