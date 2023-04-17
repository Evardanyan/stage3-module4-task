//package com.mjc.school.repository.utils;
//
//import java.time.Instant;
//import java.util.Date;
//import javax.persistence.AttributeConverter;
//import javax.persistence.Converter;
//
//@Converter(autoApply = true)
//public class DateConverter implements AttributeConverter<Date, String> {
//
//    @Override
//    public String convertToDatabaseColumn(Date date) {
//        return date != null ? date.toInstant().toString() : null;
//    }
//
//    @Override
//    public Date convertToEntityAttribute(String dateString) {
//        return dateString != null ? Date.from(Instant.parse(dateString)) : null;
//    }
//}
