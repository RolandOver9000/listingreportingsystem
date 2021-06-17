package worldofbooks.listingreportingsystem.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileHandler {
    private static final String LISTING_VALIDATION_LOG_FILE_PATH = "src/main/logs/";

    public static void writeToListingValidationLogFile(List<String> logs) {
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy__HH_mm");
        String fileName =  "listing_validation_log_" + formatter.format(currentDate) + ".csv";
        File logFile = new File(LISTING_VALIDATION_LOG_FILE_PATH + fileName);

        try (FileWriter fileWriter = new FileWriter(logFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            for(String log : logs) {
                bufferedWriter.write(log);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
