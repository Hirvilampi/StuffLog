package kevat25.stufflog.web;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kevat25.stufflog.model.UserAccount;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    
        @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Haetaan autentikoidut tiedot
        UserAccount userAccount = (UserAccount) authentication.getPrincipal();
        Long userId = userAccount.getUserId();

        // asetetaan userid request attributeksi
        request.setAttribute("userId", userId);
        System.out.println("täällä on käyty - CUSTOMAUTHENTSUCCESSHANDLER");
        // sit varsinaiselle sivulle
  //      request.getRequestDispatcher("/stufflistuser/"+userId).forward(request, response);
   response.sendRedirect("/stufflistuser/" + userId);
    }
}
