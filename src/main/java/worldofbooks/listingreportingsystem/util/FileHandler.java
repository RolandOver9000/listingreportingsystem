package worldofbooks.listingreportingsystem.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileHandler {
    private static final String LISTING_VALIDATION_LOG_FILE_PATH = "src/main/logs/";
    private static final String LISTING_REPORT_FILE_PATH = "src/main/reports/";


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

    public static String generateListingReportFromString(String givenString) {
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy__HH_mm");
        String fileName = "listing_report_" + formatter.format(currentDate) + ".json";

        try (FileWriter file = new FileWriter(LISTING_REPORT_FILE_PATH + fileName)) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(givenString);
            file.flush();

            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getListingReportFilePath() {
        return LISTING_REPORT_FILE_PATH;
    }
}
