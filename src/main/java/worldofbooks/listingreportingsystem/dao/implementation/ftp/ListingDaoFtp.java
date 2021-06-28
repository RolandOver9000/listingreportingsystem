package worldofbooks.listingreportingsystem.dao.implementation.ftp;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import worldofbooks.listingreportingsystem.dao.repository.ftp.ListingFtpRepository;

import java.io.*;

public class ListingDaoFtp implements ListingFtpRepository {
    private static final String FTP_SERVER = System.getenv("FTP_SERVER_HOST");
    private static final int FTP_SERVER_PORT = Integer.parseInt(System.getenv("FTP_SERVER_PORT"));
    private static final String FTP_USER = System.getenv("FTP_SERVER_USERNAME");
    private static final String FTP_PASSWORD = System.getenv("FTP_SERVER_PASSWORD");
    private final FTPClient ftpClient;

    public ListingDaoFtp(FTPClient newFtpClient) {
        this.ftpClient = newFtpClient;
    }

    public void uploadFile(String localFileFullName, String fileName, String directory) {
        try(InputStream input = new FileInputStream(new File(localFileFullName))) {
            this.ftpClient.connect(FTP_SERVER, FTP_SERVER_PORT);
            this.ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
            this.ftpClient.login(FTP_USER, FTP_PASSWORD);

            boolean isDirectoryExist = ftpClient.changeWorkingDirectory(directory);

            if(!isDirectoryExist) {
                System.out.println("CREATE DIR: " + directory);
                this.ftpClient.makeDirectory(directory);
            }

            this.ftpClient.storeFile(directory + "/" + fileName, input);

            int reply = this.ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                this.closeFtpConnection();
                throw new IOException("Exception in connecting to FTP Server");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.closeFtpConnection();
        }
    }

    public void closeFtpConnection() {
        try {
            this.ftpClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
