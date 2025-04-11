package kevat25.stufflog;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.junit.jupiter.api.Test;

import kevat25.stufflog.model.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ItemTests {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ConditionRepository conditionRepository;

    @Autowired
    private SizeOfRepository sizeOfRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Test
    public void findByItemName() {
        List<Item> items = itemRepository.findByItemName("Märkäimuri");
        assertThat(items).hasSize(1);
        assertThat(items.get(0).getDescription()).isEqualTo("Hyvä laite. Kärcher");
    }

    @Test
    public void createItem() {
        Category category = new Category("Childrens Movies");
        categoryRepository.save(category);
        Long userid = (long) 1;
        Optional<UserAccount> userAccountOptional = userAccountRepository.findById(userid);
        if (userAccountOptional.isPresent()) {
            UserAccount userAccount = userAccountOptional.get();
            Item item = new Item("Muumit DVD", userAccount, category);
            itemRepository.save(item);
            assertThat(item.getItemId()).isNotNull();
        }
        assertThat(userAccountOptional.get()).isNotNull();
    }

    @Test
    public void deleteItem() {
        List<Item> items = itemRepository.findByItemName("Märkäimuri");
        Item item = items.get(0);
        itemRepository.delete(item);
        List<Item> newItems = itemRepository.findByItemName("Märkäimuri");
        assertThat(newItems).hasSize(0);
    }

    @Test
    public void createNewCategory() {
        Category category = new Category("Childrens Movies");
        categoryRepository.save(category);
        Long userid = (long) 1;
        Optional<UserAccount> userAccountOptional = userAccountRepository.findById(userid);
        if (userAccountOptional.isPresent()) {
            UserAccount userAccount = userAccountOptional.get();
            Item item = new Item("Muumit BLUERAY", userAccount, category);
            itemRepository.save(item);
            assertThat(item.getCategory().getCategoryName()).isEqualTo("Childrens Movies");
        }
        assertThat(userAccountOptional.get()).isNotNull();
    }

    @Test
    public void getCorrectData() {
        Long userid = (long) 2;
        Optional<UserAccount> userAccountOptional = userAccountRepository.findById(userid);
        if (userAccountOptional.isPresent()) {
            UserAccount userAccount = userAccountOptional.get();
            Item item = itemRepository.findOneByItemName("Sewing machine");
            assertThat(item.getItemName()).isEqualTo("Sewing machine");
            assertThat(item.getDescription()).isEqualTo("Simens");
            assertThat(item.getRentalprice()).isEqualTo(20.0);

        }
    }


    @Test
    public void createNewUser(){
        UserAccount newuser = new UserAccount("testuser","$2y$10$I1FjJ9VCyYE9Qq3Tvox19.dCzgRxRypln27ueXf8YTC7s67qftx3i","BestName","UserSurname","noemail@email.no","TEST");
        userAccountRepository.save(newuser);
        UserAccount userAccount = userAccountRepository.findByUsername("testuser");
        assertThat(userAccount.getEmail()).isEqualTo("noemail@email.no");
        assertThat(userAccount.getFirstname()).isEqualTo("BestName");
        assertThat(userAccount.getRole()).isEqualTo("TEST");
    }

    @Test
    public void deleteNewUser(){
        UserAccount newuser = new UserAccount("testuser","$2y$10$I1FjJ9VCyYE9Qq3Tvox19.dCzgRxRypln27ueXf8YTC7s67qftx3i","BestName","UserSurname","noemail@email.no","TEST");
        userAccountRepository.save(newuser);
        UserAccount userAccount = userAccountRepository.findByUsername("testuser");
        assertThat(userAccount.getEmail()).isEqualTo("noemail@email.no");
        userAccountRepository.deleteById(userAccount.getUserId());
        assertThat(userAccountRepository.findByUsername("testuser")).isNull();

    }

}
