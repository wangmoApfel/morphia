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
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.TestBase;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.DAYS;

public class Java8EntityTest extends TestBase {
    @Test
    public void queries() {
        Java8ConvertersHelper.addConverters(getMorphia());

        Duration duration = Duration.ofDays(42);
        Instant instant = Instant.ofEpochMilli(System.currentTimeMillis());
        LocalDate localDate = LocalDate.of(1995, 10, 15);
        LocalDateTime localDateTime = LocalDateTime.of(2016, 4, 10, 14, 15, 16, 123 * 1_000_000);
        LocalTime localTime = LocalTime.of(10, 29, 15, 848_000_000);
        Period period = Period.ofWeeks(4);
        Year year = Year.of(1492);
        YearMonth yearMonth = YearMonth.of(1234, 6);

        Java8Entity entity = createEntity(getDs(), duration, instant, localDate, localDateTime, localTime, period, year, yearMonth);

        compare(getDs(), entity, "localDate", localDate);
        compare(getDs(), entity, "localDateTime", localDateTime);
        compare(getDs(), entity, "localTime", localTime);
        compare(getDs(), entity, "period", period);
        compare(getDs(), entity, "year", year);
        compare(getDs(), entity, "yearMonth", yearMonth);
    }

    @Test
    public void localDateTimeToString() {
        Morphia morphia = new Morphia();
        Java8ConvertersHelper.addConverters(morphia, true);
        Datastore datastore = morphia.createDatastore(getMongoClient(), TEST_DB_NAME);

        Duration duration = Duration.ofDays(42);
        Instant instant = Instant.ofEpochMilli(System.currentTimeMillis());
        LocalDate localDate = LocalDate.of(1995, 10, 15);
        LocalDateTime localDateTime = LocalDateTime.of(2016, 4, 10, 14, 15, 16, 123456789);
        LocalTime localTime = LocalTime.of(10, 29, 15, 848_000_000);
        Period period = Period.ofWeeks(4);
        Year year = Year.of(1492);
        YearMonth yearMonth = YearMonth.of(1234, 6);

        Java8Entity entity = createEntity(datastore, duration, instant, localDate, localDateTime, localTime, period, year, yearMonth);

        compare(datastore, entity, "duration", duration);
        compare(datastore, entity, "instant", instant);
        compare(datastore, entity, "localDate", localDate);
        compare(datastore, entity, "localDateTime", localDateTime);
        compare(datastore, entity, "localTime", localTime);
        compare(datastore, entity, "period", period);
        compare(datastore, entity, "year", year);
        compare(datastore, entity, "yearMonth", yearMonth);
    }

    @Test
    public void rangeQueries() {
        Java8ConvertersHelper.addConverters(getMorphia());
        Duration duration = Duration.ofDays(42);
        Instant instant = Instant.ofEpochMilli(System.currentTimeMillis());
        LocalDate localDate = LocalDate.of(1995, 10, 15);
        LocalDateTime localDateTime = LocalDateTime.of(2016, 4, 10, 14, 15, 16, 123 * 1_000_000);
        LocalTime localTime = LocalTime.of(10, 29, 15, 848493);
        Period period = Period.ofWeeks(4);
        Year year = Year.of(1492);
        YearMonth yearMonth = YearMonth.of(1234, 6);

        for (int i = 0; i < 10; i++) {
            createEntity(getDs(), duration.plusDays(i),
                         instant.plus(i, DAYS),
                         localDate.plus(i, DAYS),
                         localDateTime.plus(i, DAYS),
                         localTime.plus(i, ChronoUnit.HOURS),
                         period.plusDays(i),
                         year.plusYears(i),
                         yearMonth.plusMonths(i));
        }
        Assert.assertEquals(5L, getDs().createQuery(Java8Entity.class).field("duration").greaterThan(duration.plusDays(4)).countAll());
        Assert.assertEquals(2L, getDs().createQuery(Java8Entity.class).field("instant").lessThanOrEq(instant.plus(1, DAYS)).countAll());
        Assert.assertEquals(1L, getDs().createQuery(Java8Entity.class).field("localDate").equal(localDate.plus(1, DAYS)).countAll());
        Assert.assertEquals(0L, getDs().createQuery(Java8Entity.class).field("localDate").equal(localDate.minus(1, DAYS)).countAll());
        Assert.assertEquals(9L, getDs().createQuery(Java8Entity.class).field("localDateTime")
                                       .notEqual(localDateTime.plus(6, DAYS)).countAll());
        Assert.assertEquals(4L, getDs().createQuery(Java8Entity.class).field("period").lessThanOrEq(period.plusDays(3)).countAll());
        Assert.assertEquals(10L, getDs().createQuery(Java8Entity.class).field("year").lessThanOrEq(year.plusYears(12)).countAll());
        Assert.assertEquals(10L, getDs().createQuery(Java8Entity.class).field("yearMonth")
                                        .lessThanOrEq(yearMonth.plusYears(12)).countAll());
        Assert.assertEquals(6L, getDs().createQuery(Java8Entity.class).field("yearMonth")
                                        .lessThanOrEq(yearMonth.plusMonths(5)).countAll());
    }

    private void compare(final Datastore datastore, final Java8Entity entity, final String field, final Object value) {
        Java8Entity actual = datastore.createQuery(Java8Entity.class).field(field).equal(value).get();
        Assert.assertEquals(entity, actual);
    }

    private Java8Entity createEntity(final Datastore ds, final Duration duration, final Instant instant, final LocalDate localDate,
                                     final LocalDateTime localDateTime, final LocalTime localTime, final Period period, final Year year,
                                     final YearMonth yearMonth) {
        Java8Entity entity = new Java8Entity();
        entity.setDuration(duration);
        entity.setInstant(instant);
        entity.setLocalDate(localDate);
        entity.setLocalDateTime(localDateTime);
        entity.setLocalTime(localTime);
        entity.setPeriod(period);
        entity.setYear(year);
        entity.setYearMonth(yearMonth);
        ds.save(entity);
        return entity;
    }

}
