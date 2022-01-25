package au.com.geekseat.theta.helper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

@Converter
public class LocalDateConverter implements AttributeConverter<LocalDate, String> {
    @Override
    public String convertToDatabaseColumn(LocalDate localDate) {
        return localDate.format(ISO_LOCAL_DATE);
    }

    @Override
    public LocalDate convertToEntityAttribute(String s) {
        return LocalDate.parse(s, ISO_LOCAL_DATE);
    }
}
