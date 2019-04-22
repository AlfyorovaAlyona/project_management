package com.projectmanagement.config.hibernate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Converter(autoApply = true)
public class ZonedDateTimeAttributeConverter implements AttributeConverter<ZonedDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) {
            return null;
        }

        LocalDateTime withoutTimezone = zonedDateTime.toLocalDateTime();
        return Timestamp.valueOf(withoutTimezone);
    }

    @Override
    public ZonedDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
        if (sqlTimestamp == null) {
            return null;
        }

        LocalDateTime withoutTimezone = sqlTimestamp.toLocalDateTime();
        ZonedDateTime withTimezone = withoutTimezone.atZone(ZoneId.of("UTC"));
        return withTimezone;
    }
}