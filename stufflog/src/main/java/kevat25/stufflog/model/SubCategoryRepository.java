package kevat25.stufflog.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface SubCategoryRepository extends CrudRepository<SubCategory, Long> {

    SubCategory findBySubCategoryName(String subCategoryName);


}
