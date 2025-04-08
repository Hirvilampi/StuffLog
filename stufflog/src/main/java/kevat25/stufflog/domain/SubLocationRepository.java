package kevat25.stufflog.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface SubLocationRepository extends CrudRepository<SubLocation, Long> {

    SubLocation findBySublocationName(String sublocationName);

}
