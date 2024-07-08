package ru.mayorov.deal.сonverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import ru.mayorov.deal.dto.LoanOfferDto;
import ru.mayorov.deal.model.Passport;

@Converter
@Slf4j
public class LoanOfferDtoConverter implements AttributeConverter<LoanOfferDto, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(LoanOfferDto loanOfferDto) {
        log.info("Начинается конвертация LoanOfferDto в JSON");
        try {
            return objectMapper.writeValueAsString(loanOfferDto);
        } catch (JsonProcessingException e) {
            log.warn("Не может быть сконвертирован LoanOfferDto в JSON");
            throw new RuntimeException(e);
        }
    }

    @Override
    public LoanOfferDto convertToEntityAttribute(String value) {
        log.info("Начинается конвертация JSON в LoanOfferDto");
        try {
            return objectMapper.readValue(value, LoanOfferDto.class);
        }catch (JsonProcessingException e){
            log.warn("Не может быть сконвертирован JSON в LoanOfferDto");
            throw new RuntimeException(e);
        }
    }
}
