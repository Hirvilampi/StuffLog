package kevat25.stufflog.model;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    List<Category> findByCategoryName(String categoryName);

    Category findOneByCategoryName(String categoryName);


}
