package kevat25.stufflog.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RentableModel {

    private Long itemIdLong;
    private String itemName;
    private String itemDescription;
    private Double rentalPrice;
    private Condition condition;
    private State state;
    private Long itemOwnerId;
    private String sizeOfString;
    private String categoryName;
    private String itemOwnerEmail;

    public RentableModel() {
    }

    public RentableModel(Long itemIdLong, String itemName, String itemDescription, Double rentalPrice,
            Long itemOwnerId) {
        this.itemIdLong = itemIdLong;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.rentalPrice = rentalPrice;
        this.itemOwnerId = itemOwnerId;
    }


    public RentableModel(Long itemIdLong, String itemName, String itemDescription, Double rentalPrice,
            Condition condition, State state, Long itemOwnerId, String sizeOfString, String categoryName,
            String itemOwnerEmail) {
        this.itemIdLong = itemIdLong;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.rentalPrice = rentalPrice;
        this.condition = condition;
        this.state = state;
        this.itemOwnerId = itemOwnerId;
        this.sizeOfString = sizeOfString;
        this.categoryName = categoryName;
        this.itemOwnerEmail = itemOwnerEmail;
    }

    public RentableModel(Long itemIdLong, String itemName, State state, Long itemOwnerId) {
        this.itemIdLong = itemIdLong;
        this.itemName = itemName;
        this.state = state;
        this.itemOwnerId = itemOwnerId;
    }

    public Long getItemIdLong() {
        return itemIdLong;
    }

    public void setItemIdLong(Long itemIdLong) {
        this.itemIdLong = itemIdLong;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(Double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Long getItemOwnerId() {
        return itemOwnerId;
    }

    public void setItemOwnerId(Long itemOwnerId) {
        this.itemOwnerId = itemOwnerId;
    }

    public String getSizeOfString() {
        return sizeOfString;
    }

    public void setSizeOfString(String sizeOfString) {
        this.sizeOfString = sizeOfString;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getItemOwnerEmail() {
        return itemOwnerEmail;
    }

    public void setItemOwnerEmail(String itemOwnerEmail) {
        this.itemOwnerEmail = itemOwnerEmail;
    }

    
    
    
}
