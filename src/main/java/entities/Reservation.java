package entities;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Reservation implements Serializable {
    private static int idCounter = 1;

    private int reservationId;
    private String username;
    private int spaceId;
    private String startTime;
    private String endTime;

    public Reservation(String username, int spaceId, String startTime, String endTime) {
        this.reservationId = idCounter++;
        this.username = username;
        this.spaceId = spaceId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getReservationId() {
        return reservationId;
    }

    public String getUsername() {
        return username;
    }

    public int getSpaceId() {
        return spaceId;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
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
