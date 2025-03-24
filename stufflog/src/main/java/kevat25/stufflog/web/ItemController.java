package kevat25.stufflog.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@Configuration
@Controller
public class ItemController {

    private final SizeOfRepository sizeOfRepository;

    private final SubLocationRepository subLocationRepository;

    @Autowired
    private ItemRepository iRepository;

    public ItemController(ItemRepository iRepository, SubLocationRepository subLocationRepository, SizeOfRepository sizeOfRepository) {
        this.iRepository = iRepository;
        this.subLocationRepository = subLocationRepository;
        this.sizeOfRepository = sizeOfRepository;
    }

    @Autowired
    private CategoryRepository cRepository;

    @Autowired
    private SubCategoryRepository subCatRepository;

    @Autowired
    private LocationRepository lRepository;

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
        model.addAttribute("locations", lRepository.findAll());
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
        model.addAttribute("locations", lRepository.findAll());
        model.addAttribute("useraccount", uaRepository.findAll());
        return "stufflist";
    }

    @RequestMapping("/additem/{id}")
    public String addItemForm(@PathVariable("id") Long userId, Model model) {
        model.addAttribute("item", new Item());
        model.addAttribute("subcategories", subCatRepository.findAll());
        model.addAttribute("categories", cRepository.findAll());
        model.addAttribute("subcategories", subCatRepository.findAll());
        model.addAttribute("locations", lRepository.findAll());
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
            model.addAttribute("locations", lRepository.findAll());
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
        model.addAttribute("locations",lRepository.findAll());
        model.addAttribute("sublocations", subLocationRepository.findAll());
        model.addAttribute("conditions", conditionRepository.findAll());
        model.addAttribute("states", stateRepository.findAll());
        model.addAttribute("sizeofs", sizeOfRepository.findAll());
        model.addAttribute("categories", cRepository.findAll());
        model.addAttribute("subcategories", subCatRepository.findAll());
        model.addAttribute("userId", item.getUserAccount().getUserId());
        System.out.println("useid "+item.getUserAccount().getUserId());
 //       model.addAttribute("useraccount", uaRepository.findById(item.getUserAccount().getUserId()).orElse(null));
        return "showitem";
    }

    @PostMapping("putitem/{userId}/{id}")
    public String putitem(@Valid @ModelAttribute Item item, @PathVariable("userId") Long kayttLong, @PathVariable("id") Long itemId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("vituiks män.. ");            
            return "showitem/"+itemId;
        } else {
            // load item details
            item.setItemId(itemId);
            // load user_account info if it exits
            boolean userexists = uaRepository.findById(kayttLong).isPresent();
            if (userexists) {

            }
            Optional<UserAccount> useraccountoptional = uaRepository.findById(kayttLong);
            UserAccount userAccount = useraccountoptional.get();
            item.setUserAccount(userAccount);
            iRepository.save(item);
            System.out.println("kayttlong on "+kayttLong+ " username "+userAccount.getUsername());
        }
        return "redirect:/stufflistuser/"+kayttLong;
    }

    
    

}
