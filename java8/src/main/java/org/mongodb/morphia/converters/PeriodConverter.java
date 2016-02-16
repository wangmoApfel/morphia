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

import java.time.Period;

/**
 * Provides a converter for a {@link java.time.Period} using the toString() format defined in the Period class.
 *
 */
public class PeriodConverter extends TypeConverter implements SimpleValueConverter {
    /**
     * Creates the Converter.
     */
    public PeriodConverter() {
        super(Period.class);
    }

    @Override
    public Object decode(final Class<?> targetClass, final Object val, final MappedField optionalExtraInfo) {
        if (val == null) {
            return null;
        }

        if (val instanceof Period) {
            return val;
        }

        if (val instanceof String) {
            return Period.parse(((String) val));
        }

        throw new IllegalArgumentException("Can't convert to Period from " + val);
    }

    @Override
    public Object encode(final Object value, final MappedField optionalExtraInfo) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }
}
