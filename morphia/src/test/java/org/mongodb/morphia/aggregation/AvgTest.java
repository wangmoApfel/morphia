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

import org.junit.Assert;
import org.junit.Test;
import org.mongodb.morphia.annotations.Id;

import java.util.Iterator;

import static java.lang.String.format;
import static org.mongodb.morphia.aggregation.Accumulator.avg;
import static org.mongodb.morphia.aggregation.AggregationField.field;
import static org.mongodb.morphia.aggregation.AggregationField.fields;
import static org.mongodb.morphia.aggregation.Group.grouping;
import static org.mongodb.morphia.aggregation.Operation.multiply;
import static org.mongodb.morphia.aggregation.Projection.compute;

public class AvgTest extends AggregationTestBase {
    @Test
    public void groupAvg() {
        salesData();
        Iterator<SalesAverages> aggregate = getAds()
            .createAggregation("sales")
            .group("item",
                   grouping("avgAmount", avg(multiply(fields("price", "quantity")))),
                   grouping("avgQuantity", avg(field("quantity"))))
            .aggregate(SalesAverages.class);

        Assert.assertTrue(aggregate.hasNext());
        Assert.assertEquals(new SalesAverages("xyz", 37.5, 7.5), aggregate.next());
        Assert.assertEquals(new SalesAverages("jkl", 20, 1), aggregate.next());
        Assert.assertEquals(new SalesAverages("abc", 60, 6), aggregate.next());
    }

    @Test
    public void projectAvg() {
        quizData();

        Iterator<QuizAverages> aggregate = getAds()
            .createAggregation("students")
            .project(
                compute("quizAvg", avg(field("quizzes"))),
                compute("labAvg", avg(field("labs"))),
                compute("examAvg", avg(fields("final", "midterm"))))
            .aggregate(QuizAverages.class);
        Assert.assertTrue(aggregate.hasNext());
        Assert.assertEquals(new QuizAverages(1, 7.666666666666667, 6.5, 77.5), aggregate.next());
        Assert.assertEquals(new QuizAverages(2, 9.5, 8, 87.5), aggregate.next());
        Assert.assertEquals(new QuizAverages(3, 4.666666666666667, 5.5, 74), aggregate.next());
    }

    private static class SalesAverages {
        @Id
        private String item;
        private double avgAmount;
        private double avgQuantity;

        public SalesAverages() {
        }

        public SalesAverages(final String item, final double avgAmount, final double avgQuantity) {
            this.item = item;
            this.avgAmount = avgAmount;
            this.avgQuantity = avgQuantity;
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = item != null ? item.hashCode() : 0;
            temp = Double.doubleToLongBits(avgAmount);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(avgQuantity);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof SalesAverages)) {
                return false;
            }

            final SalesAverages that = (SalesAverages) o;

            if (Double.compare(that.avgAmount, avgAmount) != 0) {
                return false;
            }
            if (Double.compare(that.avgQuantity, avgQuantity) != 0) {
                return false;
            }
            return item != null ? item.equals(that.item) : that.item == null;

        }

        @Override
        public String toString() {
            return format("SalesAverages{avgAmount=%s, avgQuantity=%s, item='%s'}", avgAmount, avgQuantity, item);
        }
    }

    private static class QuizAverages {
        @Id
        private int id;
        private double quizAvg;
        private double labAvg;
        private double examAvg;

        public QuizAverages() {
        }

        public QuizAverages(final int id, final double quizAvg, final double labAvg, final double examAvg) {
            this.id = id;
            this.quizAvg = quizAvg;
            this.labAvg = labAvg;
            this.examAvg = examAvg;
        }

        @Override
        public String toString() {
            return String.format("QuizAverages{examAvg=%s, id=%d, labAvg=%s, quizAvg=%s}", examAvg, id, labAvg, quizAvg);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof QuizAverages)) {
                return false;
            }

            final QuizAverages that = (QuizAverages) o;

            if (id != that.id) {
                return false;
            }
            if (Double.compare(that.quizAvg, quizAvg) != 0) {
                return false;
            }
            if (Double.compare(that.labAvg, labAvg) != 0) {
                return false;
            }
            return Double.compare(that.examAvg, examAvg) == 0;

        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = id;
            temp = Double.doubleToLongBits(quizAvg);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(labAvg);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(examAvg);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }
    }
}
