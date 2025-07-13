package app.coworkingSpaceFactories;

import app.entities.CoworkingSpace;

import java.math.BigDecimal;

public class RoomFactory implements CoworkingSpaceFactory{
    @Override
    public CoworkingSpace createSpace(String details, BigDecimal price) {
        return new CoworkingSpace("room", details, price);
    }
}
