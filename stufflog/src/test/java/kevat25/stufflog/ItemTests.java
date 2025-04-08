package kevat25.stufflog;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

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
            List<Item> item = itemRepository.findAllByUserAccount(userAccount);
            assertThat(item.get(2).getItemName()).isEqualTo("Sewing machine");
            assertThat(item.get(2).getDescription()).isEqualTo("Simens");
            assertThat(item.get(2).getRentalprice()).isEqualTo(20.0);

        }
    }

}
