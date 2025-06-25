package entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "coworking_spaces")
public class CoworkingSpace{
    private static int idCounter = 1;

    @Id
    @Column(nullable = false, unique = true)
    private int id;

    private String type;

    @Column(name = "room_details")
    private String roomDetails;

    private BigDecimal price;

    @Column(name = "is_available")
    private boolean isAvailable;

    public CoworkingSpace(String type, String roomDetails, BigDecimal price) {
        this.id = idCounter++;
        this.type = type;
        this.roomDetails = roomDetails;
        this.price = price;
        this.isAvailable = true;
    }

    public CoworkingSpace() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRoomDetails() {
        return roomDetails;
    }

    public void setRoomDetails(String roomDetails) {
        this.roomDetails = roomDetails;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public static void initializeNextId(Map<Integer, CoworkingSpace> spaces) {
        idCounter = spaces.values().stream()
                .mapToInt(CoworkingSpace::getId)
                .max()
                .orElse(0) + 1;
    }



    @Override
    public String toString() {
        return "ID: " + id +
                "\nType: " + type +
                "\nRoom: " + roomDetails +
                "\nPrice: " + price + "$" +
                "\nAvailable: " + (isAvailable ? "Yes" : "No");
    }
}
