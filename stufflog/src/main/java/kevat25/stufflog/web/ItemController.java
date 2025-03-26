package kevat25.stufflog.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import kevat25.stufflog.model.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

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

    @RequestMapping(value = { "/", "index" })
    public String userSelection(Model model) {
        model.addAttribute("useraccounts", uaRepository.findAll());
        model.addAttribute("selectedUserAccount", new UserAccount());
        return "index";
    }

    @RequestMapping(value = "/selectuser", method = RequestMethod.POST)
    public String selectUser(@ModelAttribute("selectedUserAccount") UserAccount userAccount, Model model) {
        UserAccount selectedUser = uaRepository.findById(userAccount.getUserId()).orElse(null);
        model.addAttribute("selectedUser", selectedUser);
        return "redirect:/stufflistuser/" + userAccount.getUserId();
    }

    @RequestMapping(value = { "/stufflistuser/{userid}" }, method = RequestMethod.GET)
    public String showUsersStuff(@PathVariable("userid") Long userId, Model model) {
        UserAccount userAccount = uaRepository.findById(userId).orElse(null);
        model.addAttribute("items", iRepository.findAllByUserAccount(userAccount));
        model.addAttribute("categories", cRepository.findAll());
        model.addAttribute("locations", locRepository.findAll());
        model.addAttribute("useraccount", uaRepository.findById(userId).orElse(null));
        model.addAttribute("userId", userId);
        return "stufflistuser";
    }

    /*
     * @RequestMapping(value = { "/stufflistuser/{id}" }, method =
     * RequestMethod.POST)
     * public String showUsersStuff(@Valid Item item, @PathVariable("id") Long
     * userId, BindingResult bindingResult,
     * Model model) {
     * if (bindingResult.hasErrors()) {
     * return "index";
     * } else {
     * model.addAttribute("items", iRepository.findById(userId).orElse(null));
     * model.addAttribute("categories", cRepository.findAll());
     * model.addAttribute("locations", lRepository.findAll());
     * model.addAttribute("useraccount", uaRepository.findAll());
     * 
     * return "redirect:/stufflistuser";
     * }
     * 
     * }
     */

    @RequestMapping(value = { "stufflist" })
    public String showStuff(Model model) {
        model.addAttribute("items", iRepository.findAll());
        model.addAttribute("categories", cRepository.findAll());
        model.addAttribute("locations", locRepository.findAll());
        model.addAttribute("useraccount", uaRepository.findAll());
        return "stufflist";
    }

    @RequestMapping("/additem/{id}")
    public String addItemForm(@PathVariable("id") Long userId, Model model) {
        model.addAttribute("item", new Item());
        model.addAttribute("subcategories", subCatRepository.findAll());
        model.addAttribute("categories", cRepository.findAll());
        model.addAttribute("subcategories", subCatRepository.findAll());
        model.addAttribute("locations", locRepository.findAll());
        model.addAttribute("useraccount", uaRepository.findById(userId).orElse(null));
        model.addAttribute("userId", userId);

        return "additem";
    }

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

    @GetMapping("/showitem/{id}")
    public String showItem(@PathVariable("id") Long itemId, Model model) {
        Item item = iRepository.findById(itemId).orElse(null);
        if (item == null) {
            // tähän errorin käsittely - ehkä kokonaan vaan ResponseEntity<?> jne
            return "error";
        }
        model.addAttribute("item", item);
        model.addAttribute("locations", locRepository.findAll());
        model.addAttribute("sublocations", subLocationRepository.findAll());
        // ladataan location tiedot tai asetaan oletusarvo, jos location tietoa ei ole
        if (item.getLocation() == null) {
            Long longlocid = Long.valueOf(1);
            Optional<Location> locOptional = locRepository.findById(longlocid);
            if (locOptional.isPresent()) {
                Location locloc = locOptional.get();
                item.setLocation(locloc);
            }
        }
        // ladataan reposta sublocation tiedot, jos niitä ei ole. tai asetetaan
        // oletusarvo
        if (item.getLocation().getSublocation() == null) {
            System.out.println(("sublocation on kyllä NULLA"));
            item.getLocation().setSublocation(subLocationRepository.findBySublocationName("No sublocation"));
        }
        // onko tämä allaoleva tarpeellinen?? luetaanko sitä ollenkaan
        model.addAttribute("subloc", item.getLocation().getSublocation());

        model.addAttribute("conditions", conditionRepository.findAll());
        model.addAttribute("states", stateRepository.findAll());
        model.addAttribute("sizeofs", sizeOfRepository.findAll());
        model.addAttribute("categories", cRepository.findAll());

        model.addAttribute("subcategories", subCatRepository.findAll());
        System.out.println("testi alkaa");
        if (item.getCategory() == null) {
            Long longcatid = Long.valueOf(1);
            Optional<Category> catoptional = cRepository.findById(longcatid);
            if (catoptional.isPresent()) {
                Category cat = catoptional.get();
                item.setCategory(cat);
            }
        }
        if (item.getCategory().getSubCategory() == null) {
            item.getCategory().setSubCategory(subCatRepository.findBySubCategoryName("No subcategory"));
        }
        model.addAttribute("subcat", item.getCategory().getSubCategory());
        System.out.println(("ei se tähän tyssännyt"));

        model.addAttribute("userId", item.getUserAccount().getUserId());
        System.out.println("useid " + item.getUserAccount().getUserId());
        // model.addAttribute("useraccount",
        // uaRepository.findById(item.getUserAccount().getUserId()).orElse(null));
        return "showitem";
    }

    @Transactional
    @PostMapping("putitem/{userId}/{id}")
    public String putitem(@Valid @ModelAttribute Item item,
            @RequestParam(value = "location.sublocation.sublocationId", required = false) Long sublocationId,
            @RequestParam(value = "category.subCategory.subCategoryId", required = false) Long subcategoryId,
            @PathVariable("userId") Long kayttLong,
            @PathVariable("id") Long itemId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            // errorhändling tähän

            System.out.println("vituiks män.. ");
            return "showitem/" + itemId;
        } else {
            // load item details
            System.out.println("itemid: " + item.getItemId());

            System.out.println("name: " + item.getItemName());
            item.setItemId(itemId);
            System.out.println("description: " + item.getDescription());
            System.out.println("category:" + item.getCategory().getCategoryName());
            System.out.println("location:" + item.getLocation().getLocationName());
            System.out.println("size:" + item.getSizeof().getSizeName());
            System.out.println("state:" + item.getState().getStateName());
            System.out.println("condition:" + item.getCondition().getCondition());
            System.out.println("purhace price:"+item.getPurchaseprice());
            System.out.println("price:"+item.getPrice());
            System.out.println("rental price:"+item.getRentalprice());
            System.out.println("subcategory id:"+subcategoryId);
            Optional<SubCategory> subcatopt =  subCatRepository.findById(subcategoryId);
            if (subcatopt.isPresent()){
                SubCategory subcat =  subcatopt.get();
                System.out.println("!!!! subcategory name:"+subcat.getSubCategoryName());
                Category category = item.getCategory();
                category.setSubCategory(subcat);
                cRepository.save(category);

            }

            // load user_account info if it exits - we have to save the userinfo at the same
            // time, otherwise we save the info without user and the item disappears
            boolean userexists = uaRepository.findById(kayttLong).isPresent();
            if (userexists) {

            }
            Optional<UserAccount> useraccountoptional = uaRepository.findById(kayttLong);
            UserAccount userAccount = useraccountoptional.get();
            item.setUserAccount(userAccount);

            System.out.println("kayttlong on " + kayttLong + " username " + userAccount.getUsername());

            System.out.println("sublocid " + sublocationId);
            System.out.println("locationid " + item.getLocation().getLocationId() + " locationname "
                    + item.getLocation().getLocationName());
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


            /*
             * // jos sublocationId on annettu, tallennetaan sublocation item.locationin
             * alle
             * 
             * if (sublocationId != null) {
             * System.out.println("meillä on sublocid");
             * Optional<SubLocation> subLocationoOptional =
             * subLocationRepository.findById(sublocationId);
             * if (subLocationoOptional.isPresent()) {
             * SubLocation subloc = subLocationoOptional.get();
             * System.out.println("Haettiin sublocation: " + subloc.getSublocationName());
             * 
             * // haetaan location
             * Optional<Location> locOptional =
             * locRepository.findById(item.getLocation().getLocationId());
             * if (locOptional.isPresent()) {
             * System.out.println("Nyt haetaan location ja sitte tallennetaan "+locOptional.
             * get());
             * if (!subLocationRepository.existsById(subloc.getSublocationId())) {
             * subloc = subLocationRepository.save(subloc); // Tallenna subloc, jos sitä ei
             * ole
             * }
             * Location loc = locOptional.get();
             * System.out.println("subloc on:"+subloc+" loc on:"+loc);
             * loc.setSublocation(subloc);
             * item.setLocation(loc);
             * if (loc.getLocationId() == null) {
             * System.out.println("⚠️ Virhe: Location ID on null!");
             * } else if (loc.getLocationName() == null) {
             * System.out.println("⚠️ Virhe: Location Name on null!");
             * } else {
             * System.out.println("✅ Location näyttää hyvältä: " + loc);
             * }
             * System.out.println("locrepository save seuraavaksi");
             * locRepository.save(loc);
             * System.out.println("Sublocation tallennettu locationille.");
             * } else {
             * // tähän virheilmoitus REsponseentity tms
             * System.out.println("subloc ei löytynyt");
             * }
             * } else {
             * System.out.println("ei oo sublocid:tä palautettu");
             * }
             * }
             * 
             * // tarkastetaan kagetoria ja sub kategoriaa
             * // jos ei ole annettu kategoriaa, laitetaan ei kategoiraa paikalle
             * System.out.println("SEURAAVAKSI --- CATEGORIA JA SUBCATEGORIA");
             * Optional<Category> catOptional =
             * cRepository.findById(item.getCategory().getCategoryId());
             * Category cat;
             * 
             * if (catOptional.isEmpty()) {
             * cat = cRepository.findOneByCategoryName("No category");
             * if (cat == null) {
             * cat = new Category();
             * cat.setCategoryName("No category");
             * cRepository.save(cat);
             * }
             * item.setCategory(cat);
             * } else {
             * cat = catOptional.get();
             * item.setCategory(cat);
             * }
             * 
             * // 🔹 **Haetaan tai tallennetaan SubCategory**
             * if (subcategoryId != null) {
             * Optional<SubCategory> subcatOptional =
             * subCatRepository.findById(subcategoryId);
             * SubCategory subcat = subcatOptional.orElse(null);
             * 
             * if (subcat == null) {
             * System.out.println("SubCategory ei löytynyt, joten se luodaan.");
             * subcat = new SubCategory();
             * subCatRepository.save(subcat);
             * }
             * 
             * cat.setSubCategory(subcat);
             * cRepository.save(cat); // Tallennetaan, koska muokattiin viittauksia
             * }
             */
            /*
             * boolean catexists =
             * cRepository.findById(item.getCategory().getCategoryId()).isPresent();
             * if (!catexists) {
             * System.out.println("ei oo subcatid:tä palautettu");
             * Category cat = cRepository.findOneByCategoryName("No category");
             * item.setCategory(cat);
             * System.out.println("subcat id - no category asetettu");
             * catexists = true;
             * } else {
             * System.out.println("categoria on olemassa jo, joten ladataan se");
             * Optional<Category> catoptional =
             * cRepository.findById(item.getCategory().getCategoryId());
             * if (catoptional.isPresent()) {
             * Category cat = catoptional.get();
             * item.setCategory(cat);
             * System.out.println("Kategoria ladatiin");
             * }
             * }
             * 
             * // jos subcategoryid on annettu tallennetaan tiedot subcategoriaan ja
             * categorian
             * // alle
             * 
             * if (subcategoryId != null) {
             * System.out.println("subcategoryId tuli annettuna");
             * Optional<SubCategory> subcatoptional =
             * subCatRepository.findById(subcategoryId);
             * if (subcatoptional.isPresent()) {
             * System.out.println("löytyi myös sen sub kategoria");
             * SubCategory subcat = subcatoptional.get();
             * Optional<Category> catoptional =
             * cRepository.findById(item.getCategory().getCategoryId());
             * if (catoptional.isPresent()) {
             * System.out.println("löytyi kategoria");
             * Category cat = catoptional.get();
             * cat.setSubCategory(subcat);
             * item.setCategory(cat);
             * cRepository.save(cat);
             * System.out.
             * println("kategoria, sub, kategoriaja kaiki tallennettu item ja repot");
             * } else {
             * // tähän virheilmoitus
             * System.out.println("sub cat ei löytynyt");
             * }
             * } else {
             * // virheilmo tähän
             * System.out.println("Ei ole subcategoryId:tä palautettu");
             * }
             * }
             */
            System.out.println("iRepo save");
            iRepository.save(item);
            System.out.println("iRepo save OK");
        }
        return "redirect:/stufflistuser/" + kayttLong;
    }

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

    @PostMapping("/addSubCategory")
    public ResponseEntity<SubCategory> addSubCategory(@RequestBody String subcategoryName) {
        System.out.println("nimi subcat:" + subcategoryName);
        String parsenimi = subcategoryName;

        // sublocationname täytyy parsea, että sadaan sieltä pelkkä nimi ulos
        if (subcategoryName.length() > 20) {
            parsenimi = subcategoryName.substring(20, (subcategoryName.length() - 2));
            System.out.println("lyhennetty nimi:" + parsenimi);
        } else {
        }

        SubCategory subcat = new SubCategory(parsenimi);
        SubCategory savedSub = subCatRepository.save(subcat);
        return ResponseEntity.ok(savedSub);
    }

}
