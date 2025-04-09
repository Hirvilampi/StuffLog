package kevat25.stufflog.model;

import org.springframework.data.repository.CrudRepository;
import java.util.List;
import kevat25.stufflog.model.SizeOf;


public interface SizeOfRepository extends CrudRepository<SizeOf, Long> {

   
    SizeOf findOneBySizeName(String sizeName);

}
