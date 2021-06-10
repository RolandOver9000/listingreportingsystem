package worldofbooks.listingreportingsystem.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ListingStatus {

    @Id
    private int id;

    private String statusName;

    @OneToOne(mappedBy="listingStatus")
    private Listing listing;
}
