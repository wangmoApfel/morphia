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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LocalDateConverterTest extends ConverterTest {
    public LocalDateConverterTest() {
        super(new LocalDateConverter());
    }

    @Test
    public void testConversion() throws ParseException {
        LocalDate fixed = LocalDate.of(2016, 5, 1);

        Assert.assertEquals(20160501L, getConverter().encode(fixed));
        compare(LocalDate.class, LocalDate.now());
        final LocalDate now = LocalDate.now();
        compare(LocalDate.class, now.format(DateTimeFormatter.ISO_LOCAL_DATE), now);

        Date date = new SimpleDateFormat("yyyy/MMM/dd").parse("2016/Jan/20");
        LocalDate ldt = LocalDate.parse("2016/Jan/20", DateTimeFormatter.ofPattern("yyyy/MMM/dd"));
        compare(LocalDate.class, date, ldt);
    }

}
