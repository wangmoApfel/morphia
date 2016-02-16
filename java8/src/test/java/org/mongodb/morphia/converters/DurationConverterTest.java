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

import java.time.Duration;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class DurationConverterTest extends ConverterTest {
    public DurationConverterTest() {
        super(new DurationConverter());
    }

    @Test
    public void testConversion() {
        Duration duration = Duration.ofSeconds(42);

        Assert.assertEquals(42000000000L, getConverter().encode(duration));
        compare(Duration.class, duration);
//        compare(Duration.class, Duration.of(292L, ChronoUnit.YEARS));
    }
}
