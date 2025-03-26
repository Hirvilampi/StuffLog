package kevat25.stufflog.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "location_sublocation")
public class LocationSubLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "sublocation_id")
    private SubLocation sublocation;

    public LocationSubLocation() {
    }

    public LocationSubLocation(Location location, SubLocation sublocation) {
        this.location = location;
        this.sublocation = sublocation;
    }

    public long getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public SubLocation getSublocation() {
        return sublocation;
    }

    public void setSublocation(SubLocation sublocation) {
        this.sublocation = sublocation;
    }

    
}
