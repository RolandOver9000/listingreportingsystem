package mother.location;

import worldofbooks.listingreportingsystem.model.entity.Location;

import java.util.UUID;

public class LocationMother {

    public static Location getLocationWithFixUUIDAndWithoutListing() {
        Location location = new Location();
        location.setId(UUID.fromString("0fe479bb-fe39-4265-b59f-bb4ac5a060d4"));
        location.setAddressPrimary("18 Saint Paul Circle");
        location.setAddressSecondary("90151 Hovde Place");
        location.setCountry("Poland");
        location.setManagerName("Esther Elph");
        location.setPhone("473-926-8446");
        location.setPostalCode("83-401");
        location.setTown("Kościerzyna");

        return location;
    }
}
