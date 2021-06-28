package worldofbooks.listingreportingsystem.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

public class StringConverterUtil {

    public static <T> T convertJsonStringToGivenType(String givenString, TypeReference<T> targetClass) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(givenString, targetClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static LocalDate tryFormatDateFromString(String stringDate, DateTimeFormatter targetFormat) {
        try {
            return LocalDate.parse(stringDate, targetFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}