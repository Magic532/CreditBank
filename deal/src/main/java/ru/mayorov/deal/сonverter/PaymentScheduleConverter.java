package ru.mayorov.deal.сonverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import ru.mayorov.deal.dto.PaymentScheduleElementDto;

@Converter
@Slf4j
public class PaymentScheduleConverter implements AttributeConverter<PaymentScheduleElementDto, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(PaymentScheduleElementDto paymentScheduleElementDto) {
        log.info("Начинается конвертация PaymentScheduleElementDto в JSON");
        try {
            return objectMapper.writeValueAsString(paymentScheduleElementDto);
        } catch (JsonProcessingException e) {
            log.warn("Не может быть сконвертирован PaymentScheduleElementDto в JSON");
            throw new RuntimeException(e);
        }
    }

    @Override
    public PaymentScheduleElementDto convertToEntityAttribute(String value) {
        log.info("Начинается конвертация JSON в PaymentScheduleElementDto");
        try {
            return objectMapper.readValue(value, PaymentScheduleElementDto.class);
        }catch (JsonProcessingException e){
            log.warn("Не может быть сконвертирован JSON в PaymentScheduleElementDto");
            throw new RuntimeException(e);
        }
    }
}
