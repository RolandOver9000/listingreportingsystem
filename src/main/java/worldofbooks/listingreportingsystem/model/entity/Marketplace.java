package worldofbooks.listingreportingsystem.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Marketplace {

    @Id
    private int id;

    private String marketplaceName;

    @OneToOne(mappedBy="marketplace")
    private Listing listing;
}
