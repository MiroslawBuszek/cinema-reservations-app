package pl.connectis.cinemareservationsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;

@NoRepositoryBean
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID> {

    default T findByIdOrThrow(ID id, String objectName) {
        return findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(objectName + " {id=" + id + "} was not found"));
    }

    default void existsByIdOrThrow(ID id, String objectName) {
        if (!existsById(id)) {
            throw new ResourceNotFoundException(objectName + " {id=" + id + "} was not found");
        }
    }

}
