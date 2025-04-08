package kevat25.stufflog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import kevat25.stufflog.model.ItemRepository;
import kevat25.stufflog.model.Item;
import kevat25.stufflog.model.UserAccountRepository;

@SpringBootTest
class StufflogApplicationTests {

	 @Autowired
	 private ItemRepository itemRepository;

	 @Autowired
	 private UserAccountRepository userAccountRepository;

	@Test
	void contextLoads() {
	}

	//testataan yhteys tietokantaan itemRepository
	@Test
	public void TestDataBaseConnectionItemRepository(){
		assertThat(itemRepository).isNotNull();
		assertThat(itemRepository.count()).isNotNull();
	}

	//testataan yhteys tietokantaan userAccountRepository
	@Test
	public void TestDataBaseConnectionUserAccountRepository(){
		assertThat(userAccountRepository).isNotNull();
		assertThat(userAccountRepository.count()).isNotNull();
	}
}
