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

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;

@Entity("java8")
public class Java8Entity {
    @Id
    private ObjectId id;
    private Duration duration;
    private Instant instant;
    private LocalDate localDate;
    private LocalDateTime localDateTime;
    private LocalTime localTime;
    private Period period;
    private Year year;
    private YearMonth yearMonth;

    public Java8Entity() {
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(final Duration duration) {
        this.duration = duration;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(final ObjectId id) {
        this.id = id;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(final Instant instant) {
        this.instant = instant;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(final LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(final LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(final LocalTime localTime) {
        this.localTime = localTime;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(final Period period) {
        this.period = period;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(final Year year) {
        this.year = year;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(final YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (instant != null ? instant.hashCode() : 0);
        result = 31 * result + (localDate != null ? localDate.hashCode() : 0);
        result = 31 * result + (localDateTime != null ? localDateTime.hashCode() : 0);
        result = 31 * result + (localTime != null ? localTime.hashCode() : 0);
        result = 31 * result + (period != null ? period.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (yearMonth != null ? yearMonth.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Java8Entity)) {
            return false;
        }

        final Java8Entity that = (Java8Entity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) {
            return false;
        }
        if (instant != null ? !instant.equals(that.instant) : that.instant != null) {
            return false;
        }
        if (localDate != null ? !localDate.equals(that.localDate) : that.localDate != null) {
            return false;
        }
        if (localDateTime != null ? !localDateTime.equals(that.localDateTime) : that.localDateTime != null) {
            return false;
        }
        if (localTime != null ? !localTime.equals(that.localTime) : that.localTime != null) {
            return false;
        }
        if (period != null ? !period.equals(that.period) : that.period != null) {
            return false;
        }
        if (year != null ? !year.equals(that.year) : that.year != null) {
            return false;
        }
        return yearMonth != null ? yearMonth.equals(that.yearMonth) : that.yearMonth == null;

    }

    @Override
    public String toString() {
        return String.format(
            "Java8Entity{duration=%s, id=%s, instant=%s, localDate=%s, localDateTime=%s, localTime=%s, period=%s, year=%s, yearMonth=%s}",
            duration, id, instant, localDate, localDateTime, localTime, period, year, yearMonth);
    }
}
