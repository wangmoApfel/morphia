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

import java.util.Iterator;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.mongodb.morphia.aggregation.Accumulator.sum;
import static org.mongodb.morphia.aggregation.AggregationField.field;
import static org.mongodb.morphia.aggregation.AggregationField.fields;
import static org.mongodb.morphia.aggregation.Dates.dayOfYear;
import static org.mongodb.morphia.aggregation.Dates.year;
import static org.mongodb.morphia.aggregation.Group.grouping;
import static org.mongodb.morphia.aggregation.Group.id;
import static org.mongodb.morphia.aggregation.Operation.multiply;
import static org.mongodb.morphia.aggregation.Projection.compute;

public class SumTest extends AggregationTestBase {
    @Test
    public void groupSum() throws Exception {
        salesData();

        Iterator<SalesAmounts> aggregate = getAds().createAggregation("sales")
                                                   .group(id(grouping("day", dayOfYear("date")),
                                                             grouping("year", year("date"))),
                                                          grouping("totalAmount", sum(multiply(fields("price", "quantity")))),
                                                          grouping("count", sum(1)))
                                                   .aggregate(SalesAmounts.class);
        Assert.assertTrue(aggregate.hasNext());
        assertEquals(new SalesAmounts(new DayYear(46, 2014), 150, 2), aggregate.next());
        assertEquals(new SalesAmounts(new DayYear(34, 2014), 45, 2), aggregate.next());
        assertEquals(new SalesAmounts(new DayYear(1, 2014), 20, 1), aggregate.next());
    }

    @Test
    public void projectSum() {
        quizData();

        Iterator<QuizScores> aggregate = getAds().createAggregation("students")
                                                 .project(
                                                     compute("quizTotal", sum(field("quizzes"))),
                                                     compute("labTotal", sum(field("labs"))),
                                                     compute("examTotal", sum(fields("final", "midterm"))))
                                                 .aggregate(QuizScores.class);
        Assert.assertTrue(aggregate.hasNext());
        assertEquals(new QuizScores(1, 23, 13, 155), aggregate.next());
        assertEquals(new QuizScores(2, 19, 16, 175), aggregate.next());
        assertEquals(new QuizScores(3, 14, 11, 148), aggregate.next());
    }

}