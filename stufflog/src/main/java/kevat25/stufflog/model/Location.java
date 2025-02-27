package kevat25.stufflog.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "Location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private long locationId;

    @Column(name = "locationName")
    @NotEmpty(message = "Location has to have a name")
    private String locationName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "location")
    @JsonIgnore
    private List<Item> items;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "SubLocation_id")
    private SubLocation sublocation;

    // Constructors, setters and getters

    public Location() {
    }

    public Location(String locationName) {
        this.locationName = locationName;
    }

    public long getLocationId() {
        return locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public SubLocation getSublocation() {
        return sublocation;
    }

    public void setSublocation(SubLocation sublocation) {
        this.sublocation = sublocation;
    }

}
