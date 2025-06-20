package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

public class CoworkingSpace implements Serializable {
    private static int idCounter = 1;

    private int id;
    private String type;
    private String roomDetails;
    private BigDecimal price;
    private boolean isAvailable;

    public CoworkingSpace(String type, String roomDetails, BigDecimal price) {
        this.id = idCounter++;
        this.type = type;
        this.roomDetails = roomDetails;
        this.price = price;
        this.isAvailable = true;
    }

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
