package ru.mayorov.deal.сonverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import ru.mayorov.deal.model.Passport;

@Converter
@Slf4j
public class PassportAttributeConverter implements AttributeConverter<Passport, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public String convertToDatabaseColumn(Passport passport) {
        try {
            return objectMapper.writeValueAsString(passport);
        } catch (JsonProcessingException jpe) {
            log.warn("Cannot convert Passport into JSON");
            return null;
        }
    }

    @Override
    public Passport convertToEntityAttribute(String value) {
        try {
            return objectMapper.readValue(value, Passport.class);
        }catch (JsonProcessingException e){
            log.warn("Cannot convert JSON into Passport");
            return null;
        }
    }
}
