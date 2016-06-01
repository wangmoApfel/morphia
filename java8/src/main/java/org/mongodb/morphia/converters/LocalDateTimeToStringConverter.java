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

/**
 * Provides a converter for {@link LocalDateTime} converting to the toString() format defined in LocalDateTime.  This form is generally
 * intended only for cases where nanosecond level precision is required.  Most use cases should use {@link LocalDateTimeConverter} instead.
 */
public class LocalDateTimeToStringConverter extends LocalDateTimeConverter {
    @Override
    public Object encode(final Object value, final MappedField optionalExtraInfo) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }
}
