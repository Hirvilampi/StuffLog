package kevat25.stufflog.web;

import java.util.List;

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

import kevat25.stufflog.model.LocationRepository;
import kevat25.stufflog.model.SubCategory;
import kevat25.stufflog.model.SubCategoryRepository;
import kevat25.stufflog.model.CategoryRepository;
import kevat25.stufflog.model.UserAccount;
import kevat25.stufflog.model.UserAccountRepository;
import kevat25.stufflog.model.Item;
import kevat25.stufflog.model.ItemRepository;

import jakarta.validation.Valid;

@Configuration
@Controller
public class ItemController {

    @Autowired
    private ItemRepository iRepository;

    public ItemController(ItemRepository iRepository) {
        this.iRepository = iRepository;
    }

    @Autowired
    private CategoryRepository cRepository;

    @Autowired
    private SubCategoryRepository subCatRepository;

    @Autowired
    private LocationRepository lRepository;

    @Autowired
    private UserAccountRepository uaRepository;

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

    @RequestMapping(value = { "/stufflistuser/{id}" }, method = RequestMethod.GET)
    public String showUsersStuff(@PathVariable("id") Long userId, Model model) {
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

}
