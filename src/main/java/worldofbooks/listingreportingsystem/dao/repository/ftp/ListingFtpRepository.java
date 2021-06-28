package worldofbooks.listingreportingsystem.dao.repository.ftp;

public interface ListingFtpRepository {
    void uploadFile(String localFileFullName, String fileName, String directory);
}
