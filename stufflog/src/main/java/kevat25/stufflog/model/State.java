package kevat25.stufflog.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "state")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "state_id")
    private Long stateId;

    @Column(name = "state_name")
    @NotNull
    @Size(max = 20)
    private String stateName;

    @Column(name = "entry_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime date;

    @JsonIgnoreProperties("item")
    @OneToMany(mappedBy = "state")
    private List<Item> items;



    public State() {
    }

    public State(@NotNull @Size(max = 20) String state, LocalDateTime date) {
        this.stateName = state;
        this.date = date;
    }

    public State(@NotNull @Size(max = 20) String state) {
        this.stateName = state;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getState() {
        return stateName;
    }

    public void setState(String state) {
        this.stateName = state;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "State [stateId=" + stateId + ", state=" + stateName + ", date=" + date + "]";
    }

}
