package ru.mayorov.deal.сonverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import ru.mayorov.deal.dto.StatementStatusHistoryDto;

import java.util.Arrays;
import java.util.List;

@Converter
@Slf4j
public class StatementStatusHistoryDtoListConverter implements AttributeConverter<List<StatementStatusHistoryDto>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public String convertToDatabaseColumn(List<StatementStatusHistoryDto> statementStatusHistoryDtos) {
        log.info("Начинается конвертация List<StatementStatusHistoryDto> в JSON");
        try {
            return objectMapper.writeValueAsString(statementStatusHistoryDtos);
        } catch (JsonProcessingException e) {
            log.warn("Не может быть сконвертирован List<StatementStatusHistoryDto> в JSON");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<StatementStatusHistoryDto> convertToEntityAttribute(String values) {
        log.info("Начинается конвертация JSON в List<StatementStatusHistoryDto>");
        try {
            StatementStatusHistoryDto[] array = objectMapper.readValue(values, StatementStatusHistoryDto[].class);
            return Arrays.asList(array);
        }catch (JsonProcessingException e){
            log.warn("Не может быть сконвертирован JSON в List<StatementStatusHistoryDto>");
            throw new RuntimeException(e);
        }
    }
}
