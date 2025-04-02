package kevat25.stufflog.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_name", length = 30)
    @NotEmpty(message = "Item has to have name")
    private String itemName;

    @Column(name = "description", length = 500, nullable = true)
    private String description;

    @Column(name = "purchase_price", nullable = true)
    private Double purchaseprice;

    @Column(name = "selling_price", nullable = true)
    private Double price;

    @Column(name = "rental_price", nullable = true)
    private Double rentalprice;

    @Column(name ="locationinfo, nullable = true")
    private String locationinfo;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userAccountId")
    private UserAccount userAccount;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "location_id", nullable = true)
    private Location location;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "sizeof_id", nullable = true)
    private SizeOf sizeof;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "condition_id", nullable = true)
    private Condition condition;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "state_id", nullable = true)
    private State state;

    @ManyToMany
    @JoinTable(name = "item_subcategory", joinColumns = @JoinColumn(name = "item_id"), inverseJoinColumns = @JoinColumn(name = "subcategory_id"))
    private List<SubCategory> subCategories;

    // constructors, getters and setters

    public Item() {
    }

    public Item(@NotEmpty(message = "Item to have name. Miksi tätä kutsutaan") String itemName) {
        this.itemName = itemName;
    }

    public Item(@NotEmpty(message = "Item has to have a name. Miksi tätä kutsutaan") String itemName,
            UserAccount userAccount) {
        this.itemName = itemName;
        this.userAccount = userAccount;
    }

    public Item(@NotEmpty(message = "Item has to have a name. Miksi tätä kutsutaan") String itemName,
            UserAccount userAccount, Category category) {
        this.itemName = itemName;
        this.userAccount = userAccount;
        this.category = category;
    }

    public Item(@NotEmpty(message = "Item has to have a name. Miksi tätä kutsutaan") String itemName,
            String description) {
        this.itemName = itemName;
        this.description = description;
    }

    public Item(@NotEmpty(message = "Item has to have a name. Miksi tätä kutsutaan") String itemName,
            Location location) {
        this.itemName = itemName;
        this.location = location;
    }

    public Item(@NotEmpty(message = "Item has to have a name. Miksi tätä kutsutaan") String itemName,
            UserAccount userAccount, Location location) {
        this.itemName = itemName;
        this.userAccount = userAccount;
        this.location = location;
    }

    public Item(@NotEmpty(message = "Item has to have a name. Miksi tätä kutsutaan") String itemName,
            String description, Location location) {
        this.itemName = itemName;
        this.description = description;
        this.location = location;
    }

    public Item(@NotEmpty(message = "Item has to have a name. Miksi tätä kutsutaan") String itemName,
            String description, UserAccount userAccount, Location location) {
        this.itemName = itemName;
        this.description = description;
        this.userAccount = userAccount;
        this.location = location;
    }

    

    public Item(@NotEmpty(message = "Item has to have name") String itemName, String description, Double rentalprice,
            UserAccount userAccount, State state) {
        this.itemName = itemName;
        this.description = description;
        this.rentalprice = rentalprice;
        this.userAccount = userAccount;
        this.state = state;
    }

    public Item(@NotEmpty(message = "Item has to have a name. Miksi tätä kutsutaan") String itemName,
            String description, Double purchaseprice, Double price, Double rentalprice, UserAccount userAccount,
            Category category, Location location, SizeOf sizeof, Condition condition, State state) {
        this.itemName = itemName;
        this.description = description;
        this.purchaseprice = purchaseprice;
        this.price = price;
        this.rentalprice = rentalprice;
        this.userAccount = userAccount;
        this.category = category;
        this.location = location;
        this.sizeof = sizeof;
        this.condition = condition;
        this.state = state;
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

    public Item(Double price) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Double getPurchaseprice() {
        return purchaseprice;
    }

    public void setPurchaseprice(Double purchaseprice) {
        this.purchaseprice = purchaseprice;
    }

    public Double getRentalprice() {
        return rentalprice;
    }

    public void setRentalprice(Double rentalprice) {
        this.rentalprice = rentalprice;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }
    
    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public String getLocationinfo() {
        return locationinfo;
    }

    public void setLocationinfo(String locationinfo) {
        this.locationinfo = locationinfo;
    }

    

}