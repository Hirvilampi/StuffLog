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
import kevat25.stufflog.model.SubLocation;
import kevat25.stufflog.model.SubLocationRepository;
import kevat25.stufflog.model.UserAccount;
import kevat25.stufflog.model.UserAccountRepository;
import kevat25.stufflog.model.Item;
import kevat25.stufflog.model.ItemRepository;
import kevat25.stufflog.model.State;
import kevat25.stufflog.model.StateRepository;

@SpringBootApplication
public class StufflogApplication {

    private final StateRepository stateRepository;

    private final SubLocationRepository subLocationRepository;

	private static final Logger log = LoggerFactory.getLogger(StufflogApplication.class);

    StufflogApplication(SubLocationRepository subLocationRepository, StateRepository stateRepository) {
        this.subLocationRepository = subLocationRepository;
        this.stateRepository = stateRepository;
    }

	public static void main(String[] args) {
		SpringApplication.run(StufflogApplication.class, args);
	}

	@Bean
	public CommandLineRunner sufflog(ConditionRepository condRepository, LocationRepository locRepository,
			SizeOfRepository sRepository, CategoryRepository catRepository, SubCategoryRepository subCatRepository,
			ItemRepository iRepository, UserAccountRepository uaRepository, SubLocationRepository subLocRepository,
			StateRepository stateRepository) {
		return (args) -> {
			log.info("create few conditions");

			UserAccount adminuser = new UserAccount("admin", "admin", "Timo", "Lampinen","lampinen.timo@gmail.com");
			UserAccount regularuser = new UserAccount("user", "user", "Satu", "Lampinen","satu.lampinen81@gmail.com");
			UserAccount testuser = new UserAccount("test","test","Test","User","noemail@email.no");
			if (uaRepository.count() == 0)  {
				uaRepository.save(adminuser);
				uaRepository.save(regularuser);
				uaRepository.save(testuser);
			};
		
			Condition cond1 = new Condition("New");
			Condition cond2 = new Condition("Like new");
			Condition cond3 = new Condition("Unused");
			Condition cond4 = new Condition("Used");
			Condition cond5 = new Condition("Good");
			Condition cond6 = new Condition("Fair");
			Condition cond7 = new Condition("Poor");
			Condition cond8 = new Condition("Broken");

			if (condRepository.count() == 0 ){
				condRepository.save(cond1);
				condRepository.save(cond2);
				condRepository.save(cond3);
				condRepository.save(cond4);
				condRepository.save(cond5);
				condRepository.save(cond6);
				condRepository.save(cond7);
				condRepository.save(cond8);
			}

			// SubLocation table ei ole käytössä 
			SubLocation sublocnone = new SubLocation("No Sub-location");
			SubLocation sublocnone2 = new SubLocation("Blue chest");	
			List<String> subLocNames = Arrays.asList("cabinet", "chest", "desk", "trunk", "wall", "wardrobe");	
			if (subLocRepository.count() == 0) {
				subLocRepository.save(sublocnone);
				subLocRepository.save(sublocnone2);
				subLocNames.forEach(sublname -> subLocRepository.save(new SubLocation(sublname)));
			}

			Location loc1 = new Location("No location");
			Location loc2 = new Location("Study");
			Location loc3 = new Location("Living room");
			Location loc4 = new Location("Master bedroom");
			Location loc5 = new Location("Hall");
			Location loc6 = new Location("Storage");

			if (locRepository.count() == 0) {
				locRepository.save(loc1);
				locRepository.save(loc2);
				locRepository.save(loc3);
				locRepository.save(loc4);
				locRepository.save(loc5);
				locRepository.save(loc6);
			}

			List<String> sizeNames = Arrays.asList(
					 "NONE", "XXXS", "XXS","XS","S","M", "L",  "XL", "XXL","XXXL", "20", "21", "22", "23",
					 "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", 
					 "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53",
					 "54", "55", "56", "57" );
			if (sRepository.count() == 0){
				sizeNames.forEach(sizename -> sRepository.save(new SizeOf(sizename)));
			}


			List<String> categories = Arrays.asList(
					"No category","Art", "Baby", "Cars", "Clothes", "Collectibles & Antiques","Games", "Hobbies","Home & Living", "Music", "Maternity", "Outdoor", "Sports",
					"Travel", "Tools");
			if (catRepository.count() == 0) {
				categories.forEach(categoryname -> catRepository.save(new Category(categoryname)));
			}


			List<String> subCatNames = Arrays.asList(
					"No subcategory","Ice Hockey", "Soccer", "Tennis", "Squash", "Floor ball", "Skiing",
					"Mom's clothes", "Children", "Outdoor", "Dad's clothes",
					"Drawing", "Riding", "Bicycle",
					"Wood", "Iron", "Lawn", "Car",
					"Cd:s", "Instruments");
			if (subCatRepository.count() == 0){
				subCatNames.forEach(subname -> subCatRepository.save(new SubCategory(subname)));
			}
			
			List<String> conditions = Arrays.asList("New","Like new","Good","Used","Poor","Broken");
			if (condRepository.count() == 0){
				conditions.forEach(cond -> condRepository.save(new Condition(cond)));
			}

			
			List<String> statesnew = Arrays.asList(
					"In use", "For sale", "Sold", "For rent");
			if (stateRepository.count() == 0) {
				statesnew.forEach(statename -> stateRepository.save(new State(statename)));			
			}

			log.info("save few items");

			Category cattosave = catRepository.save(new Category(categories.get(11)));
			SizeOf sizeOf = sRepository.save(new SizeOf(sizeNames.get(0)));
			Condition condition = condRepository.save(new Condition(conditions.get(1)));
			State state = stateRepository.save(new State(statesnew.get(3)));
			State stateforsale = stateRepository.save(new State(statesnew.get(1)));
			System.out.println("tässä kohti vielä toimii 3");



			if (iRepository.count() == 0 ){
				iRepository.save(new Item("Märkäimuri","Hyvä laite. Kärcher", 200.0, 100.0, 30.0, adminuser, cattosave, loc6, sizeOf, condition, state ));
				System.out.println("tässä kohti vielä toimii - siis saatiin tää uus märkäimuri tallennettua");
				iRepository.save(new Item("Soccer ball", adminuser));
				iRepository.save(new Item("Drill", adminuser, loc2));
				iRepository.save(new Item("Hockey Stick", "Farrow flex60", adminuser, loc1));
				iRepository.save(new Item("Vacuum cleaner", "Quiet hoover",30.0,regularuser,state));
				iRepository.save(new Item("Sewing machine", "Simens",20.0,regularuser,state));
				iRepository.save(new Item("Full Face bicycle helmet", "Bell, kids size",70.0,regularuser,stateforsale));
				iRepository.save(new Item("Tent","4 people, all weather",35.0,testuser,state));
				iRepository.save(new Item("Packraft","2 people, MRS-packraft with 2 paddles",830.0,testuser,stateforsale));
				iRepository.save(new Item("Ice Skates","size 36",testuser,loc6));
				iRepository.save(new Item("Ice Skates","size 37",testuser,loc6));
				iRepository.save(new Item("Ice Skates","size 38",testuser,loc6));
				iRepository.save(new Item("Ice Skates","size 39",testuser,loc6));
				iRepository.save(new Item("Ice Skates","size 40",testuser,loc6));
			}


			System.out.println(" --- ITEM TIEDOT LADATTU ONNISTUNEESTI ---");
			/*
			 * iRepository.save(new Item("Soccer ball",
			 * uaRepository.findByUserAccount(adminuser)));
			 * iRepository.save(new Item("Drill", uaRepository.findByUserAccount(adminuser),
			 * loc2));
			 * iRepository.save(new Item("Hockey Stick", "Farrow flex60",
			 * uaRepository.findByUserAccount(adminuser), loc1));
			 * iRepository.save(new Item("Vacuum cleaner",
			 * uaRepository.findByUserAccount(regularuser)));
			 * iRepository.save(new Item("Soccer ball", "1"));
			 * iRepository.save(new Item("Vacuum cleaner", "2"));
			 */
			// iRepository.save(new Item("Farrow", "flex60", loc1));
			// iRepository.save(new Item("Tent", "4 people", loc3));

		};
	}
}