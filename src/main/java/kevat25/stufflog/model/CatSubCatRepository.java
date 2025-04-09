package kevat25.stufflog.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import kevat25.stufflog.model.CatSubCat;

public interface CatSubCatRepository extends CrudRepository<CatSubCat, Long>{
    
    
}