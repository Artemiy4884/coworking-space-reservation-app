package entities;

import java.io.Serializable;
import java.util.List;

public class CoworkingSpace implements Serializable {
    private static int idCounter = 1;

    private int id;
    private String type;
    private String roomDetails;
    private double price;
    private boolean isAvailable;

    public CoworkingSpace(String type, String roomDetails, double price) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public static void initializeNextId(List<CoworkingSpace> spaces) {
        int maxId = 0;
        for (CoworkingSpace space : spaces) {
            if (space.getId() > maxId) {
                maxId = space.getId();
            }
        }
        idCounter = maxId + 1;
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
