package app.services;

import app.dao.CoworkingSpaceRepository;
import app.dao.ReservationRepository;
import app.entities.CoworkingSpace;
import app.entities.Reservation;
import app.coworkingSpaceFactories.CoworkingSpaceFactory;
import app.coworkingSpaceFactories.CoworkingSpaceFactoryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import java.math.BigDecimal;

@Service
public class AdminService {

    private final CoworkingSpaceRepository spaceRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public AdminService(CoworkingSpaceRepository spaceRepository, ReservationRepository reservationRepository) {
        this.spaceRepository = spaceRepository;
        this.reservationRepository = reservationRepository;
    }

    public CoworkingSpace addSpace(String type, String details, BigDecimal price) {
        CoworkingSpaceFactory factory = CoworkingSpaceFactoryProvider.getFactory(type);
        CoworkingSpace space = factory.createSpace(details, price);
        return spaceRepository.save(space);
    }

    public boolean removeSpace(Integer id) {
        if (spaceRepository.existsById(id)) {
            spaceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }
}
