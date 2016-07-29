/*
 * Copyright (c) 2016 MongoDB, Inc.
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

import org.mongodb.morphia.Morphia;

/**
 * Helper class to add all the Java 8 converters in bulk.
 */
public final class Java8ConvertersHelper {
    private Java8ConvertersHelper() {
    }

    /**
     * Add all the converters in bulk using the numeric representation of LocalDateTime.
     *
     * @param morphia the Morphia instance to configure
     */
    public static void addConverters(final Morphia morphia) {
        Converters converters = morphia.getMapper().getConverters();
        converters.addConverter(LocalTimeConverter.class);
        converters.addConverter(LocalDateTimeConverter.class);
        converters.addConverter(LocalDateConverter.class);
        converters.addConverter(InstantConverter.class);
    }
}
