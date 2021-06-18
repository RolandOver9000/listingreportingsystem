package worldofbooks.listingreportingsystem.dao.repository.externalapi;

import worldofbooks.listingreportingsystem.model.dto.ListingIncomingDTO;

import java.util.List;

public interface ListingApiRepository {
    List<ListingIncomingDTO> getAllListings();
}
