package worldofbooks.listingreportingsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Marketplace {

    @Id
    private int id;

    @Column(columnDefinition = "TEXT")
    private String marketplaceName;

    @OneToOne(mappedBy="marketplace")
    private Listing listing;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarketplaceName() {
        return marketplaceName;
    }

    @JsonSetter("marketplace_name")
    public void setMarketplaceName(String marketplaceName) {
        this.marketplaceName = marketplaceName;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    @Override
    public String toString() {
        return "Marketplace{" +
                "id=" + id +
                ", marketplaceName='" + marketplaceName + '\'' +
                ", listing=" + listing +
                '}';
    }
}
