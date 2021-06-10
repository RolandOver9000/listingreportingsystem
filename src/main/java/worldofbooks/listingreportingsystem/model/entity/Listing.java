package worldofbooks.listingreportingsystem.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
public class Listing {

    @Id
    private UUID id;

    private String title;
    private String description;
    private BigDecimal listingPrice;
    private String currency;
    private int quantity;
    private String ownerEmailAddress;
    private Date uploadTime;

    @OneToOne(cascade = {CascadeType.ALL})
    private Location inventoryItemLocationId;

    @OneToOne(cascade = {CascadeType.ALL})
    private ListingStatus listingStatus;

    @OneToOne(cascade = {CascadeType.ALL})
    private Marketplace marketplace;
}
