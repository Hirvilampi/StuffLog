package kevat25.stufflog.model;
/*
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    public CustomUserDetailsService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("CustomUserDetailService -- ladataan username");
        UserAccount userAccount = userAccountRepository.findByUsername(username);
   //         .orElseThrow(() -> new UsernameNotFoundException("User not found"));
   System.out.println("CustomUserDetailService -- ladattiin username -- sit paluu");
        return new CustomUserDetails(userAccount);
    }
}
 */