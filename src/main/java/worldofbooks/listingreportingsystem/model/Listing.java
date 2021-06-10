package worldofbooks.listingreportingsystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
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

}
