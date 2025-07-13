package app.coworkingSpaceFactories;

public class CoworkingSpaceFactoryProvider {
    public static CoworkingSpaceFactory getFactory(String type) {
        return switch (type.toLowerCase()) {
            case "desk" -> new DeskFactory();
            case "meeting room" -> new RoomFactory();
            default -> throw new IllegalArgumentException("Unknown space type: " + type);
        };
    }
}
