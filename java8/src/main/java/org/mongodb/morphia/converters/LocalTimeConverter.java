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

import java.time.LocalTime;

/**
 * Provides a converter for {@link LocalTime} and convert it to its numeric form of &lt;hour&gt;&lt;minute&gt;&lt;seconds&gt;&lt;nanos&gt;.
 */
public class LocalTimeConverter extends TypeConverter implements SimpleValueConverter {

    public static final int NANO_OFFSET = 1_000_000_000;
    private final NumberPadder padder = new NumberPadder(2, 2, 3);

    /**
     * Creates the Converter.
     */
    public LocalTimeConverter() {
        this(LocalTime.class);
    }

    private LocalTimeConverter(final Class clazz) {
        super(clazz);
    }

    @Override
    public Object decode(final Class<?> targetClass, final Object val, final MappedField optionalExtraInfo) {
        if (val == null) {
            return null;
        }

        if (val instanceof LocalTime) {
            return val;
        }

        if (val instanceof Number) {
            long time = ((Number) val).longValue();
            long[] values = padder.extract(time);
            return LocalTime.of((int) values[0], (int) values[1], (int) values[2], (int) values[3] * 1_000_000);
        }

        throw new IllegalArgumentException("Can't convert to LocalTime from " + val);
    }

    @Override
    public Object encode(final Object value, final MappedField optionalExtraInfo) {
        if (value == null) {
            return null;
        }
        LocalTime time = (LocalTime) value;
        return padder.pad(time.getHour(), time.getMinute(), time.getSecond(), time.getNano() / 1_000_000);
    }
}
