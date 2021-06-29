package worldofbooks.listingreportingsystem.service;

import worldofbooks.listingreportingsystem.dao.repository.ftp.ListingFtpRepository;

public class FtpService {
    private ListingFtpRepository listingFtpRepository;

    public FtpService(ListingFtpRepository newListingFtpRepository) {
        this.listingFtpRepository = newListingFtpRepository;
    }

    public void uploadFile(String localFileFullName, String fileName, String hostDir) {
        this.listingFtpRepository.uploadFile(localFileFullName, fileName, hostDir);
    }
}
