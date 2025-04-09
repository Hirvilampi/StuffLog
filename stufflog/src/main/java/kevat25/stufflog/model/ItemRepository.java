package kevat25.stufflog.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long> {


  List<Item> findAllByUserAccount (UserAccount userAccount); 

  Item findByUserAccount (UserAccount userAccount);

  List<Item> findByItemName(String itemName);



}
