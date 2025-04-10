package kevat25.stufflog.web;

import kevat25.stufflog.model.*;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@EnableMethodSecurity(securedEnabled = true)
public class ItemRestController {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserAccountRepository userAccountRepository;

    // get items for specific user
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping(value = { "api/items/{userId}" })
    public ResponseEntity<Iterable<Item>> getItems(@PathVariable Long userId) {
        try {
            Optional<UserAccount> userAccountopt = userAccountRepository.findById(userId);
            if (userAccountopt.isPresent()) {
                UserAccount userAccount = userAccountopt.get();
                Iterable<Item> itemsiterable = itemRepository.findAllByUserAccount(userAccount);
                List<Item> items = new ArrayList<>();
                itemsiterable.forEach(items::add);
                if (items.isEmpty()) {
                    return ResponseEntity.noContent().build();
                }
                return ResponseEntity.ok(items);
            } else
                return ResponseEntity.noContent().build();
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Tietokantavirhe: ei voitu hakea tavaroita", e);
        }
    }

    // get single item
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','TEST')")
    @GetMapping(value = { "item/{itemId}" })
    public ResponseEntity<Item> getItem(@PathVariable Long itemId) {
        try {
            Item item = itemRepository.findById(itemId).orElse(null);
            if (item == null) {
                // tähän errorin käsittely - ehkä kokonaan vaan ResponseEntity<?> jne
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(item);
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Tietokantavirhe: ei voitu hakea tavaraa", e);
        }
    }

    // add new item - post
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping(value = { "items/{userId}" })
    public ResponseEntity<Item> newItem(@PathVariable Long userId, @RequestBody Item newItem) {
        try {
            Optional<UserAccount> userAccountopt = userAccountRepository.findById(userId);
            if (userAccountopt.isPresent()){
                UserAccount userAccount = userAccountopt.get();
                newItem.setUserAccount(userAccount);
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Käyttäjää ei löytynyt");
            itemRepository.save(newItem);

            return ResponseEntity.ok(newItem);
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Tietokantavirhe: ei voitu tallentaa tavaraa", e);

        }
    }

    // edit item -- put
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping(value = {"items/{userId}/{itemId}"})
    public ResponseEntity<Item> putItem(@PathVariable("userId") Long userId, @PathVariable("itemId") Long itemId, @RequestBody Item item){
        try {
            Optional<UserAccount> userAccountopt = userAccountRepository.findById(userId);
            if (userAccountopt.isPresent()){
                UserAccount userAccount = userAccountopt.get();
                item.setUserAccount(userAccount);
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Käyttäjää ei löytynyt");
            item.setItemId(itemId);
            return ResponseEntity.ok(item);
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Tietokantavirhe: ei voitu tallentaa muokattua tavaraa", e);
        }
    }

    // delete item - delete
    @PreAuthorize("hasAnyAuthority('ADMIN')")
     @DeleteMapping(value = {"items/{itemId}"})
     public ResponseEntity<Iterable<Item>> deleteItem(@PathVariable Long itemId){
        try { 
            itemRepository.deleteById(itemId);
            return ResponseEntity.ok(itemRepository.findAll());
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Tietokantavirhe: ei voitu poistaa muokattua tavaraa", e);
        }
     } 

    // get all rentable items from all users
    @GetMapping(value = { "/api/rentitems" })
    public ResponseEntity<List<RentableModel>> rentableItems(){
        try {
            List<RentableModel> rentableModels = new ArrayList<>();
            //haetaan kaikkien usercounttien kaikki tiedot
            Iterable<UserAccount> userAccountIter = userAccountRepository.findAll();
            // käydään yksi usercount kerrallaan kaikki läpi
            for (UserAccount account : userAccountIter){
                List<Item> items = itemRepository.findAllByUserAccount(account);
                for (Item item : items) {
                    if (item.getState() != null && item.getState().getStateName() != null ){
                    if (item.getState().getStateName().equals("For rent")){
                 //       UserAccount ownerid = itemRepository.findOwnerByItem(item);
                        RentableModel rModel = new RentableModel(
                            item.getItemId(),
                            item.getItemName(),
                            item.getState(),
                            account.getUserId());
                            if (item.getDescription() != null) {
                                rModel.setItemDescription(item.getDescription()); }
                            if (item.getRentalprice() != null) {
                                rModel.setRentalPrice(item.getRentalprice());}
                            if (item.getCondition() != null) {
                                rModel.setCondition(item.getCondition()); }
                            if (item.getSizeof() != null) {
                                rModel.setSizeOfString(item.getSizeof().getSizeName());}
                            if (item.getCategory() != null && item.getCategory().getCategoryName() != null) {
                                rModel.setCategoryName(item.getCategory().getCategoryName()); }
                        rModel.setItemOwnerEmail(account.getEmail());
                        rentableModels.add(rModel);
                    }
                } else {
                  //  System.out.println("state oli null,ei tarvitse etsiä for renttiä");
                 //   throw new ResponseStatusException(HttpStatus.NOT_FOUND,"State ei löytynyt");
                }
                } 
            }
            // ne itemit, joista löytyy "For rent" lisätään osaksi uutta listaa

            // palautetaan uusi lista
            return ResponseEntity.ok(rentableModels);
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Tietokantavirhe: vuokrattavia tavaroita ei voi näyttää", e);
    }
}

    // get all items for sale from all users
    @GetMapping(value = { "/api/saleitems" })
    public ResponseEntity<List<RentableModel>> saleableItems(){
        try {
            List<RentableModel> rentableModels  = new ArrayList<>();
         //   State state = stateRepository.findOneByStateName("For sale");
            Iterable<UserAccount> userAccountIter = userAccountRepository.findAll();
            for (UserAccount account : userAccountIter){
                List<Item> items = itemRepository.findAllByUserAccount(account);
                for (Item item : items) {
                    if (item.getState() != null && item.getState().getStateName() != null ){
                    if (item.getState().getStateName().equals("For sale")){
                        RentableModel rModel = new RentableModel(
                            item.getItemId(),
                            item.getItemName(),
                            item.getState(),
                            account.getUserId());
                            if (item.getDescription() != null) {
                                rModel.setItemDescription(item.getDescription()); }
                            if (item.getRentalprice() != null) {
                                rModel.setRentalPrice(item.getRentalprice());}
                            if (item.getCondition() != null) {
                                rModel.setCondition(item.getCondition()); }
                            if (item.getSizeof() != null) {
                                rModel.setSizeOfString(item.getSizeof().getSizeName());}
                            if (item.getCategory() != null && item.getCategory().getCategoryName() != null) {
                                rModel.setCategoryName(item.getCategory().getCategoryName()); }
                        rModel.setItemOwnerEmail(account.getEmail());
                        rentableModels.add(rModel);
                    }
                } else {
                    //  System.out.println("state oli null,ei tarvitse etsiä for renttiä");
                   //   throw new ResponseStatusException(HttpStatus.NOT_FOUND,"State ei löytynyt");
                  }
                }
            }
            return ResponseEntity.ok(rentableModels);
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Tietokantavirhe: vuokrattavia tavaroita ei voi näyttää", e);
        }
    }

}
