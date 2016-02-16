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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Provides a converter for {@link LocalDate} converting the value in to the numeric form of yyyyMMdd.
 */
public class LocalDateConverter extends Java8DateTimeConverter {
    /**
     * Creates the Converter.
     */
    public LocalDateConverter() {
        super(LocalDate.class);
    }

    @Override
    public Object decode(final Class<?> targetClass, final Object val, final MappedField optionalExtraInfo) {
        if (val == null) {
            return null;
        }

        if (val instanceof LocalDate) {
            return val;
        }

        if (val instanceof Date) {
            final Date date = (Date) val;
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(date.getTime());
            return LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
        }

        if (val instanceof Number) {
            int[] values = extract(((Number) val).longValue(), 3);
            return LocalDate.of(values[0], values[1], values[2]);
        }

        if (val instanceof String) {
            return LocalDate.parse((String) val, DateTimeFormatter.ISO_LOCAL_DATE);
        }

        throw new IllegalArgumentException("Can't convert to LocalDate from " + val);
    }

    @Override
    public Object encode(final Object value, final MappedField optionalExtraInfo) {
        LocalDate date = (LocalDate) value;
        return expand(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }
}
