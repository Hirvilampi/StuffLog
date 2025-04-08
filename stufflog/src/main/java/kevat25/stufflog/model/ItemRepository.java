package kevat25.stufflog.model;

import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface ItemRepository extends CrudRepository<Item, Long> {


  List<Item> findAllByUserAccount (UserAccount userAccount); 

  Item findByUserAccount (UserAccount userAccount);

  List<Item> findByItemName(String itemName);



}
