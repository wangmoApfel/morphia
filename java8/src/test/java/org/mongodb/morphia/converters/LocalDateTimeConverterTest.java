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
import org.junit.Test;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverterTest extends ConverterTest {
    public LocalDateTimeConverterTest() {
        super(new LocalDateTimeConverter());
    }

    @Test
    public void testConversion() throws ParseException {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime dateTime = LocalDateTime.of(2016, 5, 1, 12, 30, 45, 718000000);

        Assert.assertEquals(20160501123045718L, getConverter().encode(dateTime));
        compare(LocalDateTime.class, now);
        compare(LocalDateTime.class, now.format(DateTimeFormatter.ISO_DATE_TIME), now);
    }

}
