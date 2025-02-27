package kevat25.stufflog;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import kevat25.stufflog.model.ConditionRepository;
import kevat25.stufflog.model.Condition;
import kevat25.stufflog.model.Location;
import kevat25.stufflog.model.LocationRepository;
import kevat25.stufflog.model.SizeOf;
import kevat25.stufflog.model.SizeOfRepository;
import kevat25.stufflog.model.Category;
import kevat25.stufflog.model.CategoryRepository;
import kevat25.stufflog.model.SubCategory;
import kevat25.stufflog.model.SubCategoryRepository;
import kevat25.stufflog.model.UserAccount;
import kevat25.stufflog.model.UserAccountRepository;
import kevat25.stufflog.model.Item;
import kevat25.stufflog.model.ItemRepository;

@SpringBootApplication
public class StufflogApplication {

	private static final Logger log = LoggerFactory.getLogger(StufflogApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StufflogApplication.class, args);
	}

	@Bean
	public CommandLineRunner sufflog(ConditionRepository condRepository, LocationRepository locRepository,
			SizeOfRepository sRepository, CategoryRepository catRepository, SubCategoryRepository subCatRepository,
			ItemRepository iRepository, UserAccountRepository uaRepository) {
		return (args) -> {
			log.info("create few conditions");

			UserAccount adminuser = new UserAccount("Admin", "Admin", "Timo", "Lampinen");
			uaRepository.save(adminuser);

			UserAccount regularuser = new UserAccount("NotAdmin", "NotAdmin", "Satu", "Lampinen");
			uaRepository.save(regularuser);

			Condition cond1 = new Condition("New");
			Condition cond2 = new Condition("Like new");
			Condition cond3 = new Condition("Unused");
			Condition cond4 = new Condition("Used");
			Condition cond5 = new Condition("Good");
			Condition cond6 = new Condition("Fair");
			Condition cond7 = new Condition("Poor");
			Condition cond8 = new Condition("Broken");

			condRepository.save(cond1);
			condRepository.save(cond2);
			condRepository.save(cond3);
			condRepository.save(cond4);
			condRepository.save(cond5);
			condRepository.save(cond6);
			condRepository.save(cond7);
			condRepository.save(cond8);

			Location loc1 = new Location("Storage");
			Location loc2 = new Location("Study");
			Location loc3 = new Location("Living room");
			Location loc4 = new Location("Master bedroom");
			Location loc5 = new Location("Hall");
			locRepository.save(loc1);
			locRepository.save(loc2);
			locRepository.save(loc3);
			locRepository.save(loc4);
			locRepository.save(loc5);

			List<String> sizeNames = Arrays.asList(
					"XXXL", "XXL", "XL", "L", "M", "S", "XS", "XXS", "XXXS");
			sizeNames.forEach(sizename -> sRepository.save(new SizeOf(sizename)));

			List<String> categories = Arrays.asList(
					"Clothes", "Hobbies", "Sports", "Tools", "Music");
			categories.forEach(categoryname -> catRepository.save(new Category(categoryname)));

			List<String> subCatNames = Arrays.asList(
					"Ice Hockey", "Soccer", "Tennis", "Squash", "Floor ball", "Skiing",
					"Mom's clothes", "Children", "Outdoor", "Dad's clothes",
					"Drawing", "Riding", "Bicycle",
					"Wood", "Iron", "Lawn", "Car",
					"Cd:s", "Instruments");
			subCatNames.forEach(subname -> subCatRepository.save(new SubCategory(subname)));

			log.info("save few items");
			iRepository.save(new Item("Soccer ball", adminuser));
			iRepository.save(new Item("Drill", adminuser, loc2));
			iRepository.save(new Item("Hockey Stick", "Farrow flex60", adminuser, loc1));
			iRepository.save(new Item("Vacuum cleaner", regularuser));
	/*			
			iRepository.save(new Item("Soccer ball", uaRepository.findByUserAccount(adminuser)));
			iRepository.save(new Item("Drill", uaRepository.findByUserAccount(adminuser), loc2));
			iRepository.save(new Item("Hockey Stick", "Farrow flex60", uaRepository.findByUserAccount(adminuser), loc1));
			iRepository.save(new Item("Vacuum cleaner", uaRepository.findByUserAccount(regularuser)));
			iRepository.save(new Item("Soccer ball", "1"));
			iRepository.save(new Item("Vacuum cleaner", "2")); */
			// iRepository.save(new Item("Farrow", "flex60", loc1));
			// iRepository.save(new Item("Tent", "4 people", loc3));

		};
	}
}