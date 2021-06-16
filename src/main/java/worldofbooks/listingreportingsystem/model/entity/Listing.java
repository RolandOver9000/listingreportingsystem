package worldofbooks.listingreportingsystem.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
public class Listing {

    @Id
    private UUID id;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "DECIMAL")
    private BigDecimal listingPrice;

    @Column(columnDefinition = "TEXT")
    private String currency;

    @Column(columnDefinition = "INT")
    private int quantity;

    @Column(columnDefinition = "TEXT")
    private String ownerEmailAddress;

    @Temporal(TemporalType.DATE)
    private Date uploadTime;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Location location;

    @ManyToOne(cascade = {CascadeType.ALL})
    private ListingStatus listingStatus;

    @OneToOne(cascade = {CascadeType.ALL})
    private Marketplace marketplace;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getListingPrice() {
        return listingPrice;
    }

    public void setListingPrice(BigDecimal listingPrice) {
        this.listingPrice = listingPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOwnerEmailAddress() {
        return ownerEmailAddress;
    }

    public void setOwnerEmailAddress(String ownerEmailAddress) {
        this.ownerEmailAddress = ownerEmailAddress;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location inventoryItemLocationId) {
        this.location = inventoryItemLocationId;
    }

    public ListingStatus getListingStatus() {
        return listingStatus;
    }

    public void setListingStatus(ListingStatus listingStatus) {
        this.listingStatus = listingStatus;
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    @Override
    public String toString() {
        return "Listing{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", listingPrice=" + listingPrice +
                ", currency='" + currency + '\'' +
                ", quantity=" + quantity +
                ", ownerEmailAddress='" + ownerEmailAddress + '\'' +
                ", uploadTime=" + uploadTime +
                ", location=" + location +
                ", listingStatus=" + listingStatus +
                ", marketplace=" + marketplace +
                '}';
    }
}
