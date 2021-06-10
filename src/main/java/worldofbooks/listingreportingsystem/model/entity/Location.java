package worldofbooks.listingreportingsystem.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
public class Location {

    @Id
    private UUID id;

    private String managerName;
    private String phone;
    private String addressPrimary;
    private String addressSecondary;
    private String country;
    private String town;
    private String postalCode;

    @OneToOne(mappedBy="inventoryItemLocationId")
    private Listing listing;
}
