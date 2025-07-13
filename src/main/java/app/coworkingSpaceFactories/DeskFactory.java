package app.coworkingSpaceFactories;

import app.entities.CoworkingSpace;
import java.math.BigDecimal;

public class DeskFactory implements CoworkingSpaceFactory{
    @Override
    public CoworkingSpace createSpace(String details, BigDecimal price) {
        return new CoworkingSpace("desk", details, price);
    }
}
