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
        log.info("Начинается конвертация Passport в JSON");
        try {
            return objectMapper.writeValueAsString(passport);
        } catch (JsonProcessingException e) {
            log.warn("Не может быть сконвертирован Passport в JSON");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Passport convertToEntityAttribute(String value) {
        log.info("Начинается конвертация JSON в Passport");
        try {
            return objectMapper.readValue(value, Passport.class);
        }catch (JsonProcessingException e){
            log.warn("Не может быть сконвертирован JSON в Passport");
            throw new RuntimeException(e);
        }
    }
}
