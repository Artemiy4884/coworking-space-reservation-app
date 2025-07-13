package app.coworkingSpaceFactories;

import app.entities.CoworkingSpace;

import java.math.BigDecimal;

public interface CoworkingSpaceFactory {
    CoworkingSpace createSpace(String details, BigDecimal price);
}
