package worldofbooks.listingreportingsystem.model.dto;

import java.util.UUID;

public class ListingIncomingDTO {

    private UUID id;
    private String title;
    private String description;
    private double listing_price;
    private String currency;
    private int quantity;
    private String owner_email_address;
    private String upload_time;
    private UUID location_id;
    private int listing_status;
    private int marketplace;

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

    public double getListing_price() {
        return listing_price;
    }

    public void setListing_price(double listing_price) {
        this.listing_price = listing_price;
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

    public String getOwner_email_address() {
        return owner_email_address;
    }

    public void setOwner_email_address(String owner_email_address) {
        this.owner_email_address = owner_email_address;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public UUID getLocation_id() {
        return location_id;
    }

    public void setLocation_id(UUID location_id) {
        this.location_id = location_id;
    }

    public int getListing_status() {
        return listing_status;
    }

    public void setListing_status(int listing_status) {
        this.listing_status = listing_status;
    }

    public int getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(int marketplace) {
        this.marketplace = marketplace;
    }

    @Override
    public String toString() {
        return "ListingIncomingDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", listing_price=" + listing_price +
                ", currency='" + currency + '\'' +
                ", quantity=" + quantity +
                ", owner_email_address='" + owner_email_address + '\'' +
                ", upload_time='" + upload_time + '\'' +
                ", location_id=" + location_id +
                ", listing_status=" + listing_status +
                ", marketplace=" + marketplace +
                '}';
    }
}
