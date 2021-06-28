package worldofbooks.listingreportingsystem.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileHandler {

    public static void generateListingLogCsvFileFromString_thenSaveToDirectory(List<String> logs, String directoryPath) {
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy__HH_mm");
        String fileName =  "listing_validation_log_" + formatter.format(currentDate) + ".csv";
        File logFile = new File(directoryPath + fileName);

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

    public static String generateJsonFileFromString_thenSaveFileToDirectory(String givenString, String directoryPath) {
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy__HH_mm");
        String fileName = "listing_report_" + formatter.format(currentDate) + ".json";

        try (FileWriter file = new FileWriter(directoryPath + fileName)) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(givenString);
            file.flush();

            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
