package worldofbooks.listingreportingsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Entity
public class Location {

    @Id
    private UUID id;

    @Column(columnDefinition = "TEXT")
    private String managerName;

    @Column(columnDefinition = "TEXT")
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String addressPrimary;

    @Column(columnDefinition = "TEXT")
    private String addressSecondary;

    @Column(columnDefinition = "TEXT")
    private String country;

    @Column(columnDefinition = "TEXT")
    private String town;

    @Column(columnDefinition = "TEXT")
    private String postalCode;

    @OneToMany(mappedBy= "location")
    private List<Listing> listing;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getManagerName() {
        return managerName;
    }

    @JsonSetter("manager_name")
    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressPrimary() {
        return addressPrimary;
    }

    @JsonSetter("address_primary")
    public void setAddressPrimary(String addressPrimary) {
        this.addressPrimary = addressPrimary;
    }

    public String getAddressSecondary() {
        return addressSecondary;
    }

    @JsonSetter("address_secondary")
    public void setAddressSecondary(String addressSecondary) {
        this.addressSecondary = addressSecondary;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @JsonSetter("postal_code")
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public List<Listing> getListing() {
        return listing;
    }

    public void setListing(List<Listing> listing) {
        this.listing = listing;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", managerName='" + managerName + '\'' +
                ", phone='" + phone + '\'' +
                ", addressPrimary='" + addressPrimary + '\'' +
                ", addressSecondary='" + addressSecondary + '\'' +
                ", country='" + country + '\'' +
                ", town='" + town + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", listing=" + listing +
                '}';
    }
}
