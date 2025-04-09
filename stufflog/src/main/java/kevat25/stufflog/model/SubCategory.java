package kevat25.stufflog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subcategory")
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subcategory_id")
    private Long subCategoryId;

    @Column(name = "subcategory_name", length = 50, nullable = false)
    @NotEmpty(message = "Subcategory has to have a name")
    private String subCategoryName;
/* 
    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Category category;
*/
    @ManyToMany(mappedBy = "subcategories", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Category> categories;

    /* 
@ManyToMany
@JoinTable(
    name = "item_subcategory",
    joinColumns = @JoinColumn(name = "subcategory_id"),
    inverseJoinColumns = @JoinColumn(name = "item_id")
)
@JsonIgnore
private List<Item> items = new ArrayList<>();
*/

    public SubCategory() {
    }

    public SubCategory(@NotEmpty(message = "Subcategory has to have a name") String subcategoryName) {
        this.subCategoryName = subcategoryName;
    }

    public SubCategory(@NotEmpty(message = "Subcategory has to have a name") String subcategoryName,
            List<Category> categories) {
        this.subCategoryName = subcategoryName;
        this.categories = categories;
    }

    public SubCategory(List<Category> categories){
        this.categories=categories;
    }

    public Long getSubCategoryId() {
        return subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subcategoryName) {
        this.subCategoryName = subcategoryName;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
/* 
    public List<Item> getItems() {
        return items;
    }
    
    public void setItems(List<Item> items) {
        this.items = items;
    }
   */ 
}