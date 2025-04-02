package kevat25.stufflog.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {

    Location findByLocationName(String locationName);
   

}
