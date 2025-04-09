package kevat25.stufflog.model;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface StateRepository extends CrudRepository<State, Long> {
    
    List<State> findByStateName(String stateName);
}
