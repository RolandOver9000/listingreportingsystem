package worldofbooks.listingreportingsystem.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringConverterUtil {

    public static <T> T convertJsonStringToGivenType(String givenString, TypeReference<T> targetClass) {
        System.out.println(givenString);
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(givenString, targetClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Date tryFormatDateFromString(String stringDate, SimpleDateFormat targetFormat) {
        try {
            return targetFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}