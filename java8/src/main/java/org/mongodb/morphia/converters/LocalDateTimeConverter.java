/*
 * Copyright (c) 2008-2016 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mongodb.morphia.converters;

import org.mongodb.morphia.mapping.MappedField;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * Provides a converter for {@link java.time.LocalDateTime}.  This converter will output the value as a numeric value in the form of
 * yyyyMMddHHmmSSLLL.  This means that while millisecond values will be preserved, nanosecond level precision will be lost.  This format
 * is more efficient storage than what would be necessary to store the full nanosecond precision.  {@link LocalDateTimeToStringConverter}
 * provides a more precise, if less efficient, storage option.
 */
public class LocalDateTimeConverter extends Java8DateTimeConverter {
    /**
     * Creates the Converter.
     */
    public LocalDateTimeConverter() {
        super(LocalDateTime.class);
    }

    @Override
    public Object decode(final Class<?> targetClass, final Object val, final MappedField optionalExtraInfo) {
        if (val == null) {
            return null;
        }

        if (val instanceof LocalDateTime) {
            return val;
        }

        if (val instanceof Date) {
            final Date date = (Date) val;
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(date.getTime());
            return LocalDateTime.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
                                    cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY),
                                    cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
        }

        if (val instanceof Number) {
            long time = ((Number) val).longValue();
            long nano = time % 1000 * 1_000_000;
            time /= 1000;
            int[] values = extract(time, 6);
            return LocalDateTime.of(values[0], values[1], values[2], values[3], values[4], values[5], (int) nano);
        }

        if (val instanceof String) {
            return LocalDateTime.parse((String) val);
        }

        throw new IllegalArgumentException("Can't convert to LocalDateTime from " + val);
    }

    @Override
    public Object encode(final Object value, final MappedField optionalExtraInfo) {
        LocalDateTime dateTime = (LocalDateTime) value;
        return expand(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(), dateTime.getHour(), dateTime.getMinute(),
                      dateTime.getSecond()) * 1000 + dateTime.getNano() / 1_000_000;

    }
}
