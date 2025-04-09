package kevat25.stufflog.model;

import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

 //   Long findByUserAccount(UserAccount useraccount);
 //   String findEmailById (UserAccount useraccount);
    UserAccount findByUsername(String username);

}
