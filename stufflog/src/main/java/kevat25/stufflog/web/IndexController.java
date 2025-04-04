package kevat25.stufflog.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import kevat25.stufflog.model.UserAccount;
import kevat25.stufflog.model.UserAccountRepository;

@Controller
public class IndexController {
    
    @Autowired
    private UserAccountRepository uaRepository;

    @GetMapping("/index")
    public String index( 
        Model model,
        HttpServletRequest request, HttpServletResponse response,
       Authentication authentication) throws IOException, ServletException
     {

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("authorities", userDetails.getAuthorities());
        System.out.println("username:"+userDetails.getUsername());
        UserAccount userAccount = uaRepository.findByUsername(userDetails.getUsername());
        Long kayttajalLong = userAccount.getUserId();

        // lähetetään user id request attributena  
        request.setAttribute("SaveduserId", kayttajalLong);

        model.addAttribute("Saveduserid", kayttajalLong);
        model.addAttribute("useraccounts", uaRepository.findAll());
        model.addAttribute("selectedUserAccount", new UserAccount());

        String url = "/stufflistuser/"+kayttajalLong;
        // Forward to the desired URL
       request.getRequestDispatcher(url).forward(request, response);
        System.out.println("TULLAANKO TÄHÄN KOHTAAN OLLENKAAN");
        return "redirect:/stufflistuser/" +kayttajalLong;
    }

}
