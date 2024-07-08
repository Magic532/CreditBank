package ru.mayorov.deal.сonverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import ru.mayorov.deal.model.Employment;

@Converter
@Slf4j
public class EmploymentAttributeConverter implements AttributeConverter<Employment, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Employment employment) {
        log.info("Начинается конвертация Employment в JSON");
            try {
                return objectMapper.writeValueAsString(employment);
            }catch (JsonProcessingException e){
                log.warn("Не может быть сконвертирован Employment в JSON");
                throw new RuntimeException(e);
            }
        }

    @Override
    public Employment convertToEntityAttribute(String value){
        log.info("Начинается конвертация JSON в Employment");
        try {
            return objectMapper.readValue(value, Employment.class);
        }catch (JsonProcessingException e){
            log.warn("Не может быть сконвертирован JSON в Employment");
            throw new RuntimeException(e);
        }
    }
}
