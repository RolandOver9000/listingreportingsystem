package worldofbooks.listingreportingsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class ListingStatus {

    @Id
    private int id;

    @Column(columnDefinition = "TEXT")
    private String statusName;

    @OneToMany(mappedBy="listingStatus")
    private List<Listing> listing;

    public ListingStatus(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    @JsonSetter("status_name")
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public List<Listing> getListing() {
        return listing;
    }

    public void setListing(List<Listing> listing) {
        this.listing = listing;
    }

    @Override
    public String toString() {
        return "ListingStatus{" +
                "id=" + id +
                ", statusName='" + statusName + '\'' +
                ", listing=" + listing +
                '}';
    }
}
