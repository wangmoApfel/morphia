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

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Provides a converter for {@link YearMonth} converting an instance to the numeric form of yyyyMM.
 */
public class YearMonthConverter extends TypeConverter implements SimpleValueConverter {
    private final NumberPadder padder = new NumberPadder(2);

    /**
     * Creates the Converter.
     */
    public YearMonthConverter() {
        super(YearMonth.class);
    }

    @Override
    public Object decode(final Class<?> targetClass, final Object val, final MappedField optionalExtraInfo) {
        if (val == null) {
            return null;
        }

        if (val instanceof YearMonth) {
            return val;
        }

        if (val instanceof Number) {
            long[] value = padder.extract(((Number) val).longValue());
            return YearMonth.of((int) value[0], (int) value[1]);
        }

        if (val instanceof Date) {
            final Date date = (Date) val;
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(date.getTime());
            return YearMonth.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
        }

        if (val instanceof String) {
            return YearMonth.parse((String) val, DateTimeFormatter.ofPattern("yyyy-MM"));
        }

        throw new IllegalArgumentException("Can't convert to LocalDateTime from " + val);
    }

    @Override
    public Object encode(final Object value, final MappedField optionalExtraInfo) {
        if (value == null) {
            return null;
        }
        YearMonth yearMonth = (YearMonth) value;
        return padder.pad(yearMonth.getYear(), yearMonth.getMonthValue());

    }

}
