package pl.connectis.cinemareservationsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;

@NoRepositoryBean
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID> {

    default T findOrThrow(ID id) {
        return findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(entityName() + " {id=" + id + "} was not found"));
    }

    default void existsOrThrow(ID id) {
        if (!existsById(id)) {
            throw new ResourceNotFoundException(entityName() + " {id=" + id + "} was not found");
        }
    }

    default String entityName() {
        return "resource";
    }

}
