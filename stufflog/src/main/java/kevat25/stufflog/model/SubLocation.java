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
@Table(name = "SubLocation")
public class SubLocation {

    @Id
    @Column(name="sublocation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sublocationId;

    @Column(name = "sublocationName")
    private String sublocationName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sublocation")
    @JsonIgnore
    private List<Location> locations;

    // constructors
    public SubLocation() {
    }

    public SubLocation(String sublocationName) {
        this.sublocationName = sublocationName;
    }

    public SubLocation(List<Location> locations) {
        this.locations = locations;
    }

    public SubLocation(String sublocationName, List<Location> locations) {
        this.sublocationName = sublocationName;
        this.locations = locations;
    }

    public Long getSublocationId() {
        return sublocationId;
    }

    public String getSublocationName() {
        return sublocationName;
    }

    public void setSublocationName(String sublocationName) {
        this.sublocationName = sublocationName;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

}