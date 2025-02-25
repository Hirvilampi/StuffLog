package kevat25.stufflog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

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

    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Category> categories;

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

    public Long getSubcategoryId() {
        return subCategoryId;
    }

    public String getSubcategoryName() {
        return subCategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subCategoryName = subcategoryName;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}