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

/**
 * This define a base class for converting Java 8 Date/Time values.
 */
public abstract class Java8DateTimeConverter extends TypeConverter implements SimpleValueConverter {

    protected Java8DateTimeConverter(final Class... types) {
        super(types);
    }

    @Override
    public abstract Object encode(final Object value, final MappedField optionalExtraInfo);

    protected long expand(final int... values) {
        long value = 0;
        for (int stage : values) {
            value = value * 100 + stage;
        }

        return value;
    }

    protected int[] extract(final long value, final int count) {
        final int[] results = new int[count];
        long temp = value;
        for (int i = results.length - 1; i > 0; i--) {
            results[i] = (int) (temp % 100);
            temp /= 100;
        }
        results[0] = (int) temp;
        return results;
    }
}
