package app.services;

import app.dao.CoworkingSpaceRepository;
import app.dao.ReservationRepository;
import app.entities.CoworkingSpace;
import app.entities.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.utils.CustomExceptions.wrongTimeInputException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CoworkingSpaceRepository spaceRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public CustomerService(CoworkingSpaceRepository spaceRepository, ReservationRepository reservationRepository) {
        this.spaceRepository = spaceRepository;
        this.reservationRepository = reservationRepository;
    }

    public Reservation makeReservation(String username, Integer spaceId, LocalDateTime start, LocalDateTime end) throws wrongTimeInputException {
        if (!end.isAfter(start)) {
            throw new wrongTimeInputException("End time must be after start time.");
        }

        Optional<CoworkingSpace> optionalSpace = spaceRepository.findById(spaceId);
        if (optionalSpace.isEmpty()) {
            throw new IllegalArgumentException("Space not found");
        }

        CoworkingSpace space = optionalSpace.get();
        if (!space.isAvailable()) {
            throw new IllegalStateException("Space is not available");
        }

        space.setAvailable(false);
        spaceRepository.save(space);

        Reservation reservation = new Reservation(username, spaceId, start, end);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservations(String username) {
        return reservationRepository.findByUsernameIgnoreCase(username);
    }

    public boolean cancelReservation(String username, Integer reservationId) {
        Optional<Reservation> resOpt = reservationRepository.findById(reservationId);
        if (resOpt.isPresent() && resOpt.get().getUsername().equalsIgnoreCase(username)) {
            CoworkingSpace space = spaceRepository.findById(resOpt.get().getSpaceId()).orElse(null);
            if (space != null) {
                space.setAvailable(true);
                spaceRepository.save(space);
            }
            reservationRepository.deleteById(reservationId);
            return true;
        }
        return false;
    }

    public List<CoworkingSpace> getAvailableSpaces() {
        return spaceRepository.findByIsAvailableTrue();
    }
}
