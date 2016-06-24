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

package org.mongodb.morphia.aggregation;

import org.bson.Document;
import org.mongodb.morphia.TestBase;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;

import static java.lang.String.format;

public class AggregationTestBase extends TestBase {
    private void parse(final String collection, final String jsonString) {
        getMongoClient().getDatabase(TEST_DB_NAME).getCollection(collection).insertOne(Document.parse(jsonString));
    }

    protected static class SalesAmounts {
        @Id
        private DayYear id;
        private int totalAmount;
        private int count;

        public SalesAmounts() {
        }

        public SalesAmounts(final DayYear id, final int totalAmount, final int count) {
            this.id = id;
            this.totalAmount = totalAmount;
            this.count = count;
        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + totalAmount;
            result = 31 * result + count;
            return result;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof SalesAmounts)) {
                return false;
            }

            final SalesAmounts that = (SalesAmounts) o;

            if (totalAmount != that.totalAmount) {
                return false;
            }
            if (count != that.count) {
                return false;
            }
            return id != null ? id.equals(that.id) : that.id == null;
        }

        @Override
        public String toString() {
            return String.format("SalesAmounts{id=%s, totalAmount=%d, count=%d}", id, totalAmount, count);
        }
    }

    @Embedded
    protected static class DayYear {
        private int day;
        private int year;

        public DayYear() {
        }

        public DayYear(final int day, final int year) {
            this.day = day;
            this.year = year;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof DayYear)) {
                return false;
            }

            final DayYear dayYear = (DayYear) o;

            if (day != dayYear.day) {
                return false;
            }
            return year == dayYear.year;

        }

        @Override
        public int hashCode() {
            int result = day;
            result = 31 * result + year;
            return result;
        }

        @Override
        public String toString() {
            return format("DayYear{day=%d, year=%d}", day, year);
        }
    }

    protected static class QuizScores {
        @Id
        private int id;
        private int quizTotal;
        private int labTotal;
        private int examTotal;

        public QuizScores() {
        }

        public QuizScores(final int id, final int quizTotal, final int labTotal, final int examTotal) {
            this.id = id;
            this.quizTotal = quizTotal;
            this.labTotal = labTotal;
            this.examTotal = examTotal;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof QuizScores)) {
                return false;
            }

            final QuizScores that = (QuizScores) o;

            if (id != that.id) {
                return false;
            }
            if (quizTotal != that.quizTotal) {
                return false;
            }
            if (labTotal != that.labTotal) {
                return false;
            }
            return examTotal == that.examTotal;

        }

        @Override
        public int hashCode() {
            int result = id;
            result = 31 * result + quizTotal;
            result = 31 * result + labTotal;
            result = 31 * result + examTotal;
            return result;
        }

        @Override
        public String toString() {
            return String.format("QuizScores{id=%d, quizTotal=%d, labs=%d, examTotal=%d}", id, quizTotal, labTotal, examTotal);
        }
    }

    void quizData() {
        parse("students", "{ \"_id\": 1, \"quizzes\": [ 10, 6, 7 ], \"labs\": [ 5, 8 ], \"final\": 80, \"midterm\": 75 }");
        parse("students", "{ \"_id\": 2, \"quizzes\": [ 9, 10 ], \"labs\": [ 8, 8 ], \"final\": 95, \"midterm\": 80 }");
        parse("students", "{ \"_id\": 3, \"quizzes\": [ 4, 5, 5 ], \"labs\": [ 6, 5 ], \"final\": 78, \"midterm\": 70 }");
    }

    void salesData() {
        parse("sales", "{\"_id\":1,\"item\":\"abc\",\"price\":10,\"quantity\":2, \"date\":ISODate(\"2014-01-01T08:00:00Z\")}");
        parse("sales", "{\"_id\":2,\"item\":\"jkl\",\"price\":20,\"quantity\":1, \"date\":ISODate(\"2014-02-03T09:00:00Z\")}");
        parse("sales", "{\"_id\":3,\"item\":\"xyz\",\"price\":5, \"quantity\":5, \"date\":ISODate(\"2014-02-03T09:05:00Z\")}");
        parse("sales", "{\"_id\":4,\"item\":\"abc\",\"price\":10,\"quantity\":10,\"date\":ISODate(\"2014-02-15T08:00:00Z\")}");
        parse("sales", "{\"_id\":5,\"item\":\"xyz\",\"price\":5, \"quantity\":10,\"date\":ISODate(\"2014-02-15T09:05:00Z\")}");
    }
}
