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

import java.time.Instant;

import static org.mongodb.morphia.converters.LocalTimeConverter.NANO_OFFSET;

/**
 * This converter will take a {@link Instant} and convert it to its numeric form of &lt;epoch seconds&gt;&lt;nanos&gt;.
 */
public class InstantConverter extends Java8DateTimeConverter {

    /**
     * Creates the Converter.
     */
    public InstantConverter() {
        super(Instant.class);
    }

    @Override
    public Object decode(final Class<?> targetClass, final Object val, final MappedField optionalExtraInfo) {
        if (val == null) {
            return null;
        }

        if (val instanceof Instant) {
            return val;
        }

        if (val instanceof Number) {
            long time = ((Number) val).longValue();
            long nano = time % NANO_OFFSET;
            time /= NANO_OFFSET;
            int[] values = extract(time, 1);
            return Instant.ofEpochSecond(values[0], (int) nano);
        }

        throw new IllegalArgumentException("Can't convert to Instant from " + val);
    }

    @Override
    public Object encode(final Object value, final MappedField optionalExtraInfo) {
        Instant instant = (Instant) value;
        return instant.getEpochSecond() * NANO_OFFSET + instant.getNano();
    }
}
