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
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "Condition")
public class Condition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "condition_id")
    private Long conditionId;

    private String condition;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "condition")
    @JsonIgnore
    private List<Item> items;

    // constructor, getters and setters

    public Condition() {

    }

    public Condition(String condition) {
        this.condition = condition;
    }

    public Long getConditionId() {
        return conditionId;
    }

    public String getCondition() {
        return condition;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

}
