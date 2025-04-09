package kevat25.stufflog.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "sizeof")
public class SizeOf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sizeof_id")
    private Long sizeofId;

    @Column(name = "sizeof_name", nullable = true)
    @Size(min = 1, max = 20, message = "must be between 1 and 20 characers")
    private String sizeName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sizeof")
    @JsonIgnore
    private List<Item> items;

    // constructors, getters and setters

    public SizeOf() {
    }

    public SizeOf(String sizeName) {
        this.sizeName = sizeName;
    }

    public SizeOf(String sizeName, List<Item> items) {
        this.sizeName = sizeName;
        this.items = items;
    }

    public Long getSizeofId() {
        return sizeofId;
    }

    public void setSizeofId(Long sizeofId) {
        this.sizeofId = sizeofId;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }
    
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }


    
}
