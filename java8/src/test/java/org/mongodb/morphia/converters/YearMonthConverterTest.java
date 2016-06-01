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

import org.junit.Test;

import java.text.ParseException;
import java.time.YearMonth;

public class YearMonthConverterTest extends ConverterTest<YearMonth, Long> {
    public YearMonthConverterTest() {
        super(new YearMonthConverter());
    }

    @Test
    public void testConversion() throws ParseException {
        YearMonth date = YearMonth.of(2016, 3);
        final YearMonth now = YearMonth.now();

        assertFormat(date, 201603L);
        compare(YearMonth.class, now);
        compare(YearMonth.class, YearMonth.of(123553, 10));
    }
}
