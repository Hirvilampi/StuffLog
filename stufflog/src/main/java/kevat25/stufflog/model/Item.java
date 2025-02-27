package kevat25.stufflog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "Item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_name", length = 30)
    @NotEmpty(message = "Item has to have a name. Miksi tätä kutsutaan")
    private String itemName;

    @Column(name = "description", length = 500)
    private String description;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userAccountId")
    private UserAccount userAccount;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "sizeof_id")
    private SizeOf sizeof;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "condition_id")
    private Condition condition;

    @Column(name = "price")
    private int price;

    // constructors, getters and setters

    public Item() {
    }

    public Item(@NotEmpty(message = "Item to have name. Miksi tätä kutsutaan") String itemName) {
        this.itemName = itemName;
    }

    public Item(@NotEmpty(message = "Item has to have a name. Miksi tätä kutsutaan") String itemName, UserAccount userAccount) {
        this.itemName = itemName;
        this.userAccount = userAccount;
    }

    public Item(@NotEmpty(message = "Item has to have a name. Miksi tätä kutsutaan") String itemName, UserAccount userAccount, Category category) {
        this.itemName = itemName;
        this.userAccount = userAccount;
        this.category = category;
    }

    public Item(@NotEmpty(message = "Item has to have a name. Miksi tätä kutsutaan") String itemName, String description) {
        this.itemName = itemName;
        this.description = description;
    }

    public Item(@NotEmpty(message = "Item has to have a name. Miksi tätä kutsutaan") String itemName, Location location) {
        this.itemName = itemName;
        this.location = location;
    }

    public Item(@NotEmpty(message = "Item has to have a name. Miksi tätä kutsutaan") String itemName, UserAccount userAccount, Location location) {
        this.itemName = itemName;
        this.userAccount = userAccount;
        this.location = location;
    }

    public Item(@NotEmpty(message = "Item has to have a name. Miksi tätä kutsutaan") String itemName, String description, Location location) {
        this.itemName = itemName;
        this.description = description;
        this.location = location;
    }

    public Item(@NotEmpty(message = "Item has to have a name. Miksi tätä kutsutaan") String itemName, String description, UserAccount userAccount, Location location) {
        this.itemName = itemName;
        this.description = description;
        this.userAccount = userAccount;
        this.location = location;
    }

    public Item(Location location) {
        this.location = location;
    }

    public Item(SizeOf sizeof) {
        this.sizeof = sizeof;
    }

    public Item(Condition condition) {
        this.condition = condition;
    }

    public Item(int price) {
        this.price = price;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public SizeOf getSizeof() {
        return sizeof;
    }

    public void setSizeof(SizeOf sizeof) {
        this.sizeof = sizeof;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

   
}