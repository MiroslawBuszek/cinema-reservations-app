package pl.connectis.cinemareservationsapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.connectis.cinemareservationsapp.model.Place;

@Repository
public interface PlaceRepository extends CrudRepository<Place, Long> {
}
