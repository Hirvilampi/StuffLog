package kevat25.stufflog.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import kevat25.stufflog.domain.SubLocation;
import kevat25.stufflog.domain.SubLocationRepository;
import kevat25.stufflog.model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Configuration
@Controller
public class ItemController {

    @Autowired
    private SizeOfRepository sizeOfRepository;

    @Autowired
    private SubLocationRepository subLocationRepository;

    @Autowired
    private ItemRepository iRepository;

    public ItemController(ItemRepository iRepository, SubLocationRepository subLocationRepository,
            SizeOfRepository sizeOfRepository) {
        this.iRepository = iRepository;
        this.subLocationRepository = subLocationRepository;
        this.sizeOfRepository = sizeOfRepository;
    }

    @Autowired
    private CategoryRepository cRepository;

    @Autowired
    private SubCategoryRepository subCatRepository;

    @Autowired
    private LocationRepository locRepository;

    @Autowired
    private UserAccountRepository uaRepository;

    @Autowired
    private ConditionRepository conditionRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CatSubCatRepository catSubCatRepository;

    // login
    @RequestMapping(value = { "/", "login" })
    public String login() {
        return "login";
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value = "/selectuser", method = RequestMethod.POST)
    public String selectUser(@Valid @ModelAttribute("selectedUserAccount") UserAccount userAccount, Model model) {
        UserAccount selectedUser = uaRepository.findById(userAccount.getUserId()).orElse(null);
        model.addAttribute("selectedUser", selectedUser);
        return "redirect:/stufflistuser/" + userAccount.getUserId();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = { "/admin" })
    public String showUsers(@Valid @ModelAttribute("selectedUserAccount") UserAccount userAccount, Model model) {
        System.out.println("saaddaanko USERACCOUTN ladattua");
        model.addAttribute("useraccounts", uaRepository.findAll());
        System.err.println("Kaikki saatiin ladattua");
        return "admin";
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','USER','TEST')")
    @GetMapping(value = { "/stufflistuser/{userid}" })
    public String showUsersStuff(@PathVariable("userid") Long userId, Model model, HttpServletRequest request) {
        // haetaan käyttäjän id requestattributesta
        /*
         * Long requestuserId = (Long) request.getAttribute("SaveduserId");
         * System.out.println("requestin kautta tullut id "+requestuserId);
         * if (requestuserId == userId){
         * System.out.println("OIKEA KÄYTTÄJÄ!!");
         * }
         */
        UserAccount userAccount = uaRepository.findById(userId).orElse(null);
        model.addAttribute("userId", userId);
        model.addAttribute("items", iRepository.findAllByUserAccount(userAccount));
        model.addAttribute("categories", cRepository.findAll());
        model.addAttribute("locations", locRepository.findAll());
        model.addAttribute("useraccount", uaRepository.findById(userId).orElse(null));

        System.out.println("-- NÄYTETÄÄN SUN STUFFLIST -- " + userId);
        String currentkayttaja = getCurrentUser();
        System.out.println("--- CURRENT USER IS:"+getCurrentUser());
        return "stufflistuser";
    }

    // vanha
    @RequestMapping(value = { "/stufflist" })
    public String showStuff(Model model) {
        System.out.println("STUFFLIST IT IS!!!");
        model.addAttribute("items", iRepository.findAll());
        model.addAttribute("categories", cRepository.findAll());
        model.addAttribute("locations", locRepository.findAll());
        model.addAttribute("useraccount", uaRepository.findAll());
        System.err.println("Kaikki saatiin ladattua");
        return "stufflist";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @RequestMapping("/additem/{id}")
    public String addItemForm(@Valid @PathVariable("id") Long userId, Model model) {
        model.addAttribute("item", new Item());
        model.addAttribute("categories", cRepository.findAll());
        model.addAttribute("subcategories", subCatRepository.findAll());
        model.addAttribute("locations", locRepository.findAll());
        model.addAttribute("sizeofs", sizeOfRepository.findAll());
        model.addAttribute("states", stateRepository.findAll());
        model.addAttribute("conditions", conditionRepository.findAll());
        model.addAttribute("useraccount", uaRepository.findById(userId).orElse(null));
        model.addAttribute("userId", userId);
        return "additem";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @RequestMapping(value = "/save/{id}", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("item") Item item, @PathVariable("id") Long userId,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("item", item);
            model.addAttribute("categories", cRepository.findAll());
            model.addAttribute("subcategories", subCatRepository.findAll());
            model.addAttribute("locations", locRepository.findAll());
            model.addAttribute("useraccount", uaRepository.findById(userId).orElse(null));
            model.addAttribute("userId", userId);
            return "additem";
        } else {
            UserAccount userAccount = uaRepository.findById(userId).orElse(null);
            item.setUserAccount(userAccount);
            iRepository.save(item);
            model.addAttribute("userId", userId);
            return "redirect:/stufflistuser/" + userAccount.getUserId();
        }
    }

    @GetMapping("/edititem/{id}")
    public String editItem(@Valid @PathVariable("id") Long itemId, Model model) {
        Item item = iRepository.findById(itemId).orElse(null);
        if (item == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }
        model.addAttribute("item", item);
        model.addAttribute("locations", locRepository.findAll());
        model.addAttribute("conditions", conditionRepository.findAll());
        model.addAttribute("states", stateRepository.findAll());
        model.addAttribute("sizeofs", sizeOfRepository.findAll());
        model.addAttribute("categories", cRepository.findAll());
        model.addAttribute("subcategories", subCatRepository.findAll());
        model.addAttribute("userId", item.getUserAccount().getUserId());
        System.out.println("useid " + item.getUserAccount().getUserId());
        return "edititem";
    }

    @GetMapping("/showitem/{id}/{userId}")
    public String showItem(@Valid @PathVariable("id") Long itemId, @PathVariable("userId") Long userId, Model model) {
        Item item = iRepository.findById(itemId).orElse(null);
        if (item == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }
        model.addAttribute("item", item);
        model.addAttribute("yourId", userId);
        return "showitem";
    }


    @Transactional
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @PostMapping("putitem/{userId}/{id}")
    public String putitem(@Valid @ModelAttribute Item item,
            @RequestParam(value = "location.sublocation.sublocationId", required = false) Long sublocationId,
            @PathVariable("userId") Long kayttLong,
            @PathVariable("id") Long itemId, BindingResult bindingResult,
            HttpServletRequest request, Model model) {

        if (bindingResult.hasErrors()) {
          model.addAttribute("item", item);
            model.addAttribute("categories", cRepository.findAll());
            model.addAttribute("subcategories", subCatRepository.findAll());
            model.addAttribute("locations", locRepository.findAll());
            model.addAttribute("conditions", conditionRepository.findAll());
            model.addAttribute("states", stateRepository.findAll());
            model.addAttribute("sizeofs", sizeOfRepository.findAll());
            model.addAttribute("useraccount", uaRepository.findById(kayttLong).orElse(null));
            model.addAttribute("userId", kayttLong);
            return "edititem";
        } else {
            // load item details
            item.setItemId(itemId);
            Optional<UserAccount> useraccountoptional = uaRepository.findById(kayttLong);
            UserAccount userAccount = useraccountoptional.get();
            item.setUserAccount(userAccount);
            iRepository.save(item);
            System.out.println("iRepo save OK");
        }
        return "redirect:/stufflistuser/" + kayttLong;
    }

    @Transactional
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @RequestMapping(value = "/delete/{userId}/{id}", method = RequestMethod.GET)
    public String deleteItem(@Valid @PathVariable("id") Long itemId, @PathVariable("userId") Long kayttajalLong, Item item,
            BindingResult bindingResult, HttpServletRequest request, Model model) {
        System.out.println("Ollaan DELETEN MAAILMASSSA. Haluamme tuhota itemin, jonka id: " + itemId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("item", item);
            model.addAttribute("categories", cRepository.findAll());
            model.addAttribute("subcategories", subCatRepository.findAll());
            model.addAttribute("locations", locRepository.findAll());
            model.addAttribute("conditions", conditionRepository.findAll());
            model.addAttribute("states", stateRepository.findAll());
            model.addAttribute("sizeofs", sizeOfRepository.findAll());
            model.addAttribute("useraccount", uaRepository.findById(kayttajalLong).orElse(null));
            model.addAttribute("userId", kayttajalLong);
            return "redirect:/stufflistuser/" + kayttajalLong;
        } else {
            Optional<Item> itemopt = iRepository.findById(itemId);
            Item itemi = itemopt.get();
            itemi.getSubCategories().clear();
            iRepository.save(itemi);
            iRepository.deleteById(itemId);
            iRepository.flush();
            // seuraava vain tarkistusta varten, että delete onnistui tai ei - oli hiukan ongelmia many to many suhteen kanssa
            Optional<Item> delItem = iRepository.findById(itemId);
            if (delItem.isPresent()) {
                System.out.println("Delete ei onnistunut:");
            } else {
                itemopt = iRepository.findById(itemId);
                if (itemopt.isPresent()) {
                    itemi = itemopt.get();
                    System.out.println("jos tää on null, niin sitä ei löydy tietokannasta: " + itemi.getItemName());
                }
            }
            return "redirect:/stufflistuser/" + kayttajalLong;
        }
    }
    
    @GetMapping(value = { "/rentlist/{userid}" })
    public String showRentables(@PathVariable("userid") Long userId, Model model, HttpServletRequest request) {
        /*
         * Long requestuserId = (Long) request.getAttribute("SaveduserId");
         * System.out.println("requestin kautta tullut id "+requestuserId);
         * if (requestuserId == userId){
         * System.out.println("OIKEA KÄYTTÄJÄ!! -- rentlist");
         * }
         * UserAccount userAccount = uaRepository.findById(userId).orElse(null);
         */
        model.addAttribute("useraccount", uaRepository.findById(userId).orElse(null));
        model.addAttribute("userId", userId);
        Iterable<UserAccount> uaList = uaRepository.findAll();
        List<UserAccount> accountList = new ArrayList<>();
        for (UserAccount uAccount : uaList){
            accountList.add(uAccount);
        }
        List<RentableModel> rentableModels = new ArrayList<>();

        for (int i = 0; i < accountList.size(); i++) {
            // haetaan yhden henkilön lista kerrallaan
            List<Item> items = iRepository.findAllByUserAccount(accountList.get(i));
            for (int x = 0; x < items.size(); x++) { // testataaan yksi item kerrallaan
                Item thisitem = items.get(x);
                Long ownerid = (long) i;
                // verrataan ensin ettei oo null ja jos ei ole katsotaan onko "For rent", jolloin mennään etiäpäin
                if (thisitem.getState() != null && thisitem.getState().getStateName() != null) {
                    if (thisitem.getState().getStateName().equals("For rent")) {
                        // item on For rent, joten haetaan kaikki RentableModels:n tiedot ja lisätään lopuksi listaan
                        RentableModel rModel = new RentableModel(
                                thisitem.getItemId(), thisitem.getItemName(),
                                thisitem.getState(), ownerid);
                        if (thisitem.getDescription() != null) {
                             rModel.setItemDescription(thisitem.getDescription());}
                        if (thisitem.getRentalprice() != null) {
                            rModel.setRentalPrice(thisitem.getRentalprice()); }
                        if (thisitem.getCondition() != null) {
                            rModel.setCondition(thisitem.getCondition()); }
                        if (thisitem.getSizeof() != null) {
                            rModel.setSizeOfString(thisitem.getSizeof().getSizeName()); }
                        if (thisitem.getCategory() != null && thisitem.getCategory().getCategoryName() != null) {
                            rModel.setCategoryName(thisitem.getCategory().getCategoryName()); }
                        rModel.setItemOwnerEmail(accountList.get(i).getEmail());
                        rentableModels.add(rModel);
                    }
                } else {
                    System.out.println("state oli null");
                }
            }
        }
        model.addAttribute("rentItems", rentableModels);
        return "rentitems";
    }

    @GetMapping(value = { "/salelist/{userid}" })
    public String showSaleable(@PathVariable("userid") Long userId, Model model, HttpServletRequest request) {
        model.addAttribute("useraccount", uaRepository.findById(userId).orElse(null));
        model.addAttribute("userId", userId);

        // eli haetaan kaikkien käyttäjien item listat ja poimitaan niistä oikealla statuksella olevat
        Iterable<UserAccount> uaList = uaRepository.findAll();
        List<UserAccount> accountList = StreamSupport.stream(uaList.spliterator(), false)
                .collect(Collectors.toList());
        List<RentableModel> rentableModels = new ArrayList<>();
        System.out.println("aletaan tsekkaamaan listaa"); 
        
        for (int i = 0; i < accountList.size(); i++) {
            System.out.println("henkilö (normlist) nro: " + i);
            // haetaan yhden henkilön lista kerrallaan
            List<Item> items = iRepository.findAllByUserAccount(accountList.get(i));
            for (int x = 0; x < items.size(); x++) {// haetaan yksi item
                Item thisitem = items.get(x);
                Long ownerid = (long) i;
                if (thisitem.getState() != null && thisitem.getState().getStateName() != null) {
                    if (thisitem.getState().getStateName().equals("For sale")) {
                        System.out.println("löytyi For rent");
                        // item on For rent, joten haetaan kaikki RentableModels:n tiedot
                        RentableModel rModel = new RentableModel(
                                thisitem.getItemId(),
                                thisitem.getItemName(),
                                thisitem.getState(),
                                ownerid);
                        if (thisitem.getDescription() != null) {
                            rModel.setItemDescription(thisitem.getDescription()); }
                        if (thisitem.getPrice() != null) {
                            rModel.setSalePrice(thisitem.getPrice());}
                        if (thisitem.getCondition() != null) {
                            rModel.setCondition(thisitem.getCondition());}
                        if (thisitem.getSizeof() != null) {
                            rModel.setSizeOfString(thisitem.getSizeof().getSizeName());}
                        if (thisitem.getCategory() != null && thisitem.getCategory().getCategoryName() != null) {
                            rModel.setCategoryName(thisitem.getCategory().getCategoryName());}
                        rModel.setItemOwnerEmail(accountList.get(i).getEmail());
                        rentableModels.add(rModel);
                    }
                } else {
                    System.out.println("state oli null");
                }
            }
        }
        model.addAttribute("rentItems", rentableModels);
        return "saleitems";
    }

    @PostMapping("/addCategory")
    public ResponseEntity<?> addCategory(@RequestBody String categoryName) {
        System.out.println("nimi cat:" + categoryName);
        String parsenimi = categoryName;

        // category täytyy parsea, että sadaan sieltä pelkkä nimi ulos - category on json muotoa
        if (categoryName.length() > 20) {
            parsenimi = categoryName.substring(17, (categoryName.length() - 2));
            System.out.println("lyhennetty nimi suluissa:(" + parsenimi + ")");
        } else {
        }

        // tsekataan, ettei lisätä samaa nimeä montaa kertaa
        Category cat = cRepository.findOneByCategoryName(parsenimi);
        Category savedcat;
        if (cat == null) {
            Category catname = new Category(parsenimi);
            savedcat = cRepository.save(catname);
            System.out.println("tallenneetiin uusi kategoria:" + parsenimi);
            return ResponseEntity.ok(savedcat);
        } else {
            System.out.println("NIMI ON JO KÄYTÖSSÄ");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No duplicate names allowed");
        }
    }

    @PostMapping("/addSubCategory")
    public ResponseEntity<?> addSubCategory(@RequestBody String subcategoryName) {
        System.out.println("nimi subcat:" + subcategoryName);
        String parsenimi = subcategoryName;

        // subcategory täytyy parsea, että sadaan sieltä pelkkä nimi ulos
        if (subcategoryName.length() > 20) {
            parsenimi = subcategoryName.substring(20, (subcategoryName.length() - 2));
            System.out.println("lyhennetty nimi:" + parsenimi);
        } else {
        }

        // tsekataan, ettei lisätä samaa nimeä montaa kertaa
        SubCategory subc = subCatRepository.findBySubCategoryName(parsenimi);
        SubCategory savedSub;
        if (subc == null) {
            SubCategory subcat = new SubCategory(parsenimi);
            savedSub = subCatRepository.save(subcat);
            System.out.println("tallenneetiin uusi sub kategoria:" + parsenimi);
            return ResponseEntity.ok(savedSub);
        } else {
            System.out.println("NIMI ON JO KÄYTÖSSÄ");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No duplicate names allowed");
        }
        
    }

    @PostMapping("/addLocation")
    public ResponseEntity<?> addLocation(@RequestBody String locationName) {
        System.out.println("nimi loc:" + locationName);
        String parsenimi = locationName;

        // category täytyy parsea, että sadaan sieltä pelkkä nimi ulos
        if (locationName.length() > 20) {
            parsenimi = locationName.substring(17, (locationName.length() - 2));
            System.out.println("lyhennetty nimi:(" + parsenimi + ")");
        } else {
        }

        // tsekataan, ettei lisätä samaa nimeä montaa kertaa
        Location loc = locRepository.findByLocationName(parsenimi);
        Location savedloc;
        if (loc == null) {
            Location locname = new Location(parsenimi);
            savedloc = locRepository.save(locname);
            System.out.println("tallenneetiin uusi kategoria:" + parsenimi);
            return ResponseEntity.ok(savedloc);
        } else {
            System.out.println("NIMI ON JO KÄYTÖSSÄ");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No duplicate names allowed");
        }
        
    }


    
     


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleInvalidJson(HttpMessageNotReadableException ex) {

        return ResponseEntity.badRequest().body(Map.of("virhe",
                "Tieto väärässä muodossa, tarkasta syötteiden arvot. IDn tulee olla kokonaislukuja. Mahdollisen hinnan tulee olla joko kokonaisluku tai liukuluku. Päivämäärän ja ajan tulee olla muodossa yyyy-MM-dd'T'HH:mm:ss.   "
                        + ex.getMessage()));

    }


    //current userin testausta varten
    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            return authentication.getName(); // Returns the username of the authenticated user
        }
        return null; // Return null if no user is authenticated
    }


    // ei käytössä
    @PostMapping("/addSublocation")
    public ResponseEntity<SubLocation> addSublocation(@RequestBody String sublocationName) {
        System.out.println("nimi sublo:" + sublocationName);
        String parsenimi = sublocationName;

        // sublocationname täytyy parsea, että sadaan sieltä pelkkä nimi ulos
        if (sublocationName.length() > 20) {
            parsenimi = sublocationName.substring(20, (sublocationName.length() - 2));
            System.out.println("lyhennetty nimi:" + parsenimi);
        } else {
        }

        SubLocation subloc = new SubLocation(parsenimi);
        SubLocation savedSub = subLocationRepository.save(subloc);
        return ResponseEntity.ok(savedSub);
    }


    public SubCategory getSubCategory(Category category) {
        List<SubCategory> subCategories = category.getSubCategories();
        if (!subCategories.isEmpty()) {
            // oletetaan, etä ensimmäinen subCategoriesiin tallentunut on oikea subcategory
            return subCategories.get(0);
        } else {
        }
        return null;
    }

    public boolean findSubCategory(Category category, SubCategory subcategory) {
        for (SubCategory subcat : category.getSubCategories()) {
            if (subcat.getSubCategoryId().equals(subcategory.getSubCategoryId())) {
                return true;
            }
        }
        return false;
    }

/* edititem/{id} osiosta
        // model.addAttribute("sublocations", subLocationRepository.findAll());
        // ladataan location tiedot tai asetaan oletusarvo, jos location tietoa ei ole
        if (item.getLocation() == null) {
            Long longlocid = Long.valueOf(1);
            Optional<Location> locOptional = locRepository.findById(longlocid);
            if (locOptional.isPresent()) {
                Location locloc = locOptional.get();
                item.setLocation(locloc);
            }
        }

        System.out.println("category haku ja tallennus alkaa");
        System.out.println("Sisältö: item.getCategory: " + item.getCategory());
        if (item.getCategory() == null) {
            Long longcatid = Long.valueOf(1);
            Optional<Category> catoptional = cRepository.findById(longcatid);
            if (catoptional.isPresent()) {
                Category cat = catoptional.get();
                item.setCategory(cat);
                System.out.println("mitä tallenttettiin categoryyn");
                System.out.println(item.getCategory());
                System.out.println(item.getCategory().getCategoryName());
            }
        }

        SubCategory saveSubCategory = null;
        // Tarkistetaan onko gategory asetettu ja onko sub categoria tyhjä
        System.out.println("subcategory haku ja tallennus alkaa");
        // System.out.println(item.getCategory().getSubCategories().get(0));
        if (item.getCategory().getSubCategories() == null) {
            System.out.println("lista on tyhjä");
        }
        if (item.getCategory().getSubCategories() != null) {
            System.out.println("mitä catin alla olevat subcagoriat on syöny");
            System.out.println("Listan koko: " + item.getCategory().getSubCategories().size());
            for (SubCategory subCat : item.getCategory().getSubCategories()) {
                System.out.println("näytä subcat");
                System.out.println(subCat.getSubCategoryName());
            }
            System.out.println("se lista käytiin läpi");
        }
        System.out.println("näitkö subcategorian paikan 0 tiedot??");

        if (item.getCategory() != null) {
            System.out.println("meillä siis on categoria");
            for (SubCategory subCat : item.getCategory().getSubCategories()) {
                saveSubCategory = subCat;
                System.out.println("näytä subcat");
                System.out.println(subCat);
                break;
            }
        }

        // System.out.println("!!! subcatiin tuleva arvo, siis
        // nimi:"+saveSubCategory.getSubCategoryName());
        model.addAttribute("subcat", saveSubCategory);
        System.out.println(("ei se tähän tyssännyt"));
        */




        /* putitem kohdasta ennen irepository tallennusta
            // check location and save the new sublocation -
            // if there is no location given, we put No location to it
            boolean locexists = locRepository.findById(item.getLocation().getLocationId()).isPresent();
            if (!locexists) {
                Location loc = locRepository.findByLocationName("No location");
                item.setLocation(loc);
                locexists = true;
            } else {
                Optional<Location> locoptional = locRepository.findById(item.getLocation().getLocationId());
                Location loc = locoptional.get();
                item.setLocation(loc);
            }
 */

}
