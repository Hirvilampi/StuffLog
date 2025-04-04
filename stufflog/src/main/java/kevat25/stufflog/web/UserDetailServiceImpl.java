package kevat25.stufflog.web;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kevat25.stufflog.model.UserAccount;
import kevat25.stufflog.model.UserAccountRepository;



@Service
public class UserDetailServiceImpl implements UserDetailsService {

    UserAccountRepository repository;


    public UserDetailServiceImpl(UserAccountRepository userAccountRepository){
        this.repository =userAccountRepository;
    }
   
  
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserAccount currentuser = repository.findByUsername(username);
        UserDetails user = new org.springframework.security.core.userdetails
        .User(username, currentuser.getPasswordHash(),
        AuthorityUtils.createAuthorityList(currentuser.getRole()));
    
        return user;

    }
}

