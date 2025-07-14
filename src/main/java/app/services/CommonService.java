package app.services;

import app.dao.CoworkingSpaceRepository;
import app.dao.ReservationRepository;
import app.entities.CoworkingSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonService {

    private final CoworkingSpaceRepository spaceRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public CommonService(CoworkingSpaceRepository spaceRepository, ReservationRepository reservationRepository) {
        this.spaceRepository = spaceRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<CoworkingSpace> getAllCoworkingSpaces() {
        return spaceRepository.findAll();
    }
}
