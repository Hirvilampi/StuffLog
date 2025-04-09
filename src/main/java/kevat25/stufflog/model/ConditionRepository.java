package kevat25.stufflog.model;

import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface ConditionRepository extends CrudRepository<Condition, Long> {

    /*
    Condition findOneByConditionName (String condition);
    
 List<Condition> findByCondition(String condition);
 */
}
