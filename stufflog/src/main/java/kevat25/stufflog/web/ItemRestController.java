package kevat25.stufflog.web;

import kevat25.stufflog.model.*;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class ItemRestController  {

    ItemRepository itemRepository;
    UserAccountRepository userAccountRepository;

  // get items for specific user
  @GetMapping( value ={"api/items/{userId}"})
  public ResponseEntity<Iterable<Item>> getItems(@PathVariable Long userIdLong){
    try {
        if (userAccountRepository.findById(userIdLong).isPresent()){
            Optional<UserAccount> userAccountopt = userAccountRepository.findById(userIdLong);
            UserAccount userAccount = userAccountopt.get();
            Iterable<Item> itemsiterable = itemRepository.findAllByUserAccount(userAccount);
            List<Item> items = new ArrayList<>();
            itemsiterable.forEach(items::add);
            if (items.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(items);
        } else return ResponseEntity.noContent().build(); 
    } catch (DataAccessException e) {
    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Tietokantavirhe: ei voitu hakea lippuja", e);
}
  }

  // get single item
  

    // add new item - post


    // edit item -- put


    // delete item - delete


    // get all rentable items from all users



    // get all items for sale from all users









  
    
}
