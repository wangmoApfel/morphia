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

import org.junit.Assert;

import static java.lang.String.format;

class ConverterTest {
    private TypeConverter converter;

    ConverterTest(final TypeConverter converter) {
        this.converter = converter;
    }

    TypeConverter getConverter() {
        return converter;
    }

    void compare(final Class<?> targetClass, final Object value) {
        compare(targetClass, converter.encode(value), value);
    }

    void compare(final Class<?> targetClass, final Object encoded, final Object value) {
        final Object decoded = converter.decode(targetClass, encoded);
        Assert.assertEquals(format("%s didn't survive the round trip: decoded = %s encoded=%s", value, decoded, encoded),
                            value, decoded);
    }
}
